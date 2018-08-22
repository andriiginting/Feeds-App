package com.example.andriginting.feeds.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.example.andriginting.feeds.repo.remote.hackernews.HackerNewsResponse
import com.example.andriginting.feeds.repo.remote.news.NewsArticleData
import com.example.andriginting.feeds.network.NetworkClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException



class FeedsViewModel(val context: Context) : ViewModel() {

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
        fetchHN()

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

    private fun fetchHN() {
        NetworkClient().getHackerNewsServiceRequest()
                .getHackerNewsListId()
                .flattenAsObservable { it.body() }
                .flatMap { response ->
                    NetworkClient().getHackerNewsServiceRequest()
                            .getHackerNewsItems(response)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.body()?.let { it1 -> list.add(it1) }
                    hackerNewsRepo.value = list
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