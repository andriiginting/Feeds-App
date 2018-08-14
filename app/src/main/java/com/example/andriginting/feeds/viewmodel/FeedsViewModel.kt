package com.example.andriginting.feeds.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.widget.Toast
import com.example.andriginting.feeds.model.hackernews.HackerNewsResponse
import com.example.andriginting.feeds.model.news.NewsModel
import com.example.andriginting.feeds.model.news.NewsResponse
import com.example.andriginting.feeds.network.NetworkClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class FeedsViewModel : ViewModel() {
    private val TAG = "feedsViewModel"

    val composite: CompositeDisposable? = CompositeDisposable()

    private val newsRepo: MutableLiveData<List<NewsResponse>> = MutableLiveData()
    private val hackerNewsRepo: MutableLiveData<List<HackerNewsResponse>> = MutableLiveData()

    private val repoLoadsError: MutableLiveData<Boolean> = MutableLiveData()
    private val repoLoadsLoading: MutableLiveData<Boolean> = MutableLiveData()


    fun getAllNews(): LiveData<List<NewsResponse>> {
        return newsRepo
    }

    fun fetchAllRepo(country: String, category: String) {
        getListOfNewsArticle(country,category)
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
                            newsRepo.value = response.body()
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


}