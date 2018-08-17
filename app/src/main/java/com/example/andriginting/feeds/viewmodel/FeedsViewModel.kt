package com.example.andriginting.feeds.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.andriginting.feeds.repo.remote.hackernews.HackerNewsResponse
import com.example.andriginting.feeds.repo.remote.news.NewsArticleData
import com.example.andriginting.feeds.network.NetworkClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class FeedsViewModel : ViewModel() {

    private val TAG = "feedsViewModel"

    private val list: ArrayList<HackerNewsResponse> = ArrayList()
    private val newsRepo: MutableLiveData<List<NewsArticleData>> = MutableLiveData()
    private val hackerNewsRepo: MutableLiveData<List<HackerNewsResponse>> = MutableLiveData()

    private val repoLoadsError: MutableLiveData<Boolean> = MutableLiveData()
    private val repoLoadsLoading: MutableLiveData<Boolean> = MutableLiveData()


    fun getAllNews(): LiveData<List<NewsArticleData>> {
        return newsRepo
    }


    fun getHackerNews(): LiveData<List<HackerNewsResponse>> {
        return hackerNewsRepo
    }


    fun fetchAllRepo(country: String, category: String) {
        getListOfNewsArticle(country, category)
        getHackerNewsArticle()

    }

    private fun getListOfNewsArticle(country: String, category: String) {
        repoLoadsLoading.value = true
        NetworkClient().getNewsServiceRequest()
                .getListArticle(country, category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    repoLoadsLoading.value = false
                    repoLoadsError.value = false
                    when {
                        response.isSuccessful -> {
                            newsRepo.value = response.body()?.articleData
                            Log.d(TAG, response.body().toString())
                        }
                        response.code() == 401 -> Log.d(TAG, response.message().toString())
                        response.code() == 400 -> Log.d(TAG, response.message().toString())
                    }
                }, { error ->
                    repoLoadsError.value = true
                    repoLoadsLoading.value = false
                    try {
                        Log.e(TAG, error.fillInStackTrace().message.toString())
                    } catch (e: IOException) {
                        Log.e(TAG, e.stackTrace.toString())
                    }
                })
    }

    private fun getHackerNewsArticle() {
        NetworkClient().getHackerNewsServiceRequest()
                .getHackerNewsListId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    repoLoadsLoading.value = false
                    repoLoadsError.value = false
                    when {
                        response.isSuccessful -> {
                            for (result in 0..20) {
                                NetworkClient().getHackerNewsServiceRequest()
                                        .getHackerNewsItems(response.body()!![result])
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe {
                                            it.body()?.let { it1 -> list.add(it1) }
                                            hackerNewsRepo.value = list

                                            Log.d("listhackerapp", it.body().toString())
                                        }
                            }
                            Log.d("listhacker", response.body().toString())
                            Log.d("listhackersize", response.body().toString())

                        }
                        response.code() == 401 -> Log.d("listhacker", response.message().toString())
                        response.code() == 400 -> Log.d("listhacker", response.message().toString())
                    }
                }, { error ->
                    repoLoadsError.value = true
                    repoLoadsLoading.value = false
                    try {
                        Log.e(TAG, error.fillInStackTrace().message.toString())
                    } catch (e: IOException) {
                        Log.e(TAG, e.stackTrace.toString())
                    }
                })

    }

}