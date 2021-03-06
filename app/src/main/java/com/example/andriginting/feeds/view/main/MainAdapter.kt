package com.example.andriginting.feeds.view.main

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.andriginting.feeds.R
import com.example.andriginting.feeds.di.GlideApp
import com.example.andriginting.feeds.repo.remote.news.NewsArticleData
import com.example.andriginting.feeds.viewmodel.FeedsViewModel

class MainAdapter(listViewModel: FeedsViewModel,
                  lifecycleOwner: LifecycleOwner) :
        RecyclerView.Adapter<MainAdapter.NewsViewHolder>() {

    private var listNews: ArrayList<NewsArticleData> = ArrayList()

    init {
        listViewModel.getAllNews().observe(lifecycleOwner, Observer<List<NewsArticleData>> { repos ->
            listNews.clear()
            if (repos != null) {
                listNews.addAll(repos)
                notifyDataSetChanged()
            }
        }) //for news api
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_feeds_content_item, parent, false)
        return NewsViewHolder(view)


    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    override fun onBindViewHolder(holder: MainAdapter.NewsViewHolder, position: Int) {
        holder.bindItem(listNews[position])

    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.title_news_article)
        private var newsImage: ImageView = itemView.findViewById(R.id.image_article)

        fun bindItem(data: NewsArticleData) {
            title.text = data.articletitle
            data.articleImageUrl?.let { bindImageToHolder(it, itemView.context, newsImage) }

        }

        @SuppressLint("PrivateResource")
        private fun bindImageToHolder(image: String, context: Context, target: ImageView) {
            GlideApp.with(context)
                    .load(image)
                    .fitCenter()
                    .placeholder(R.color.material_grey_600)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            //loadingProgressBar.visibility = View.GONE
                            return false
                        }

                    })
                    .into(target)
        }
    }


}

