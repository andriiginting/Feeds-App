package com.example.andriginting.feeds.view.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.andriginting.feeds.R
import com.example.andriginting.feeds.repo.remote.hackernews.HackerNewsResponse
import com.example.andriginting.feeds.repo.remote.news.NewsArticleData
import com.example.andriginting.feeds.viewmodel.FeedsViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {


    private val viewModel by viewModel<FeedsViewModel>()

    private lateinit var newsAdapter: MainAdapter
    private lateinit var hackerAdapter: HackerNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)

        viewModel.fetchAllRepo("us", "technology")

        newsAdapter = MainAdapter(viewModel, this)
        hackerAdapter = HackerNewsAdapter(viewModel, this)
        setupNewsRecycler(newsAdapter)
        setupHackerRecycler(hackerAdapter)
    }

    private fun setupNewsRecycler(adapter: MainAdapter) {
        news_recyclerview.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        news_recyclerview.adapter = adapter
        observeNewsData()
    }

    private fun setupHackerRecycler(adapter: HackerNewsAdapter) {
        hacker_recyclerview.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
        hacker_recyclerview.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        hacker_recyclerview.adapter = adapter
        observeNewsData()
    }


    private fun observeNewsData() {
        viewModel.getAllNews().observe(this, Observer<List<NewsArticleData>> {
            if (it?.isEmpty()!!) {
                news_recyclerview.visibility = View.GONE
            }
        })

        viewModel.getLoadingData().observe(this, Observer {
            shimmer_view_container_news.visibility = if (it!!) View.VISIBLE else View.GONE
        })

    }

    private fun observeHackerNewsData() {
        viewModel.getHackerNews().observe(this, Observer<List<HackerNewsResponse>> {
            //set shimmer gone
        })
    }

}
