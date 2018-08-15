package com.example.andriginting.feeds.view.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.andriginting.feeds.R
import com.example.andriginting.feeds.repo.remote.news.NewsArticleData
import com.example.andriginting.feeds.viewmodel.FeedsViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: FeedsViewModel

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)

        viewModel = ViewModelProviders.of(this).get(FeedsViewModel::class.java)
        viewModel.fetchAllRepo("us","technology")

        adapter = MainAdapter( viewModel,this)
        setupRecycler(adapter)
    }

    private fun setupRecycler(adapter: MainAdapter){
        feeds_recyclerview.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false)
        feeds_recyclerview.adapter = adapter
        observeNewsData()
    }

    private fun observeNewsData(){
        viewModel.getAllNews().observe(this, Observer<List<NewsArticleData>> {
            //set shimmer gone
        })
    }

}
