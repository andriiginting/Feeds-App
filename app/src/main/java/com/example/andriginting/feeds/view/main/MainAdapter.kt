package com.example.andriginting.feeds.view.main

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.andriginting.feeds.R
import com.example.andriginting.feeds.model.news.NewsModel
import com.example.andriginting.feeds.model.news.NewsResponse
import com.example.andriginting.feeds.viewmodel.FeedsViewModel

class MainAdapter(listViewModel: FeedsViewModel,
                  lifecycleOwner: LifecycleOwner):
        RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var listNews: ArrayList<NewsResponse> = ArrayList()

    init {
        listViewModel.getAllNews().observe(lifecycleOwner, Observer<List<NewsResponse>>{ repos ->
            listNews.clear()
            if (repos != null){
                listNews.addAll(repos)
                notifyDataSetChanged()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.feeds_content_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listNews[position],position)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.title_news_article)
        private var newsImage: ImageView = itemView.findViewById(R.id.image_article)
        private var loadingProgresBar : ProgressBar = itemView.findViewById(R.id.progbar_item_article)

        fun bindItem(data: NewsResponse,position: Int){
            title.text = data.articleData?.get(position)?.articletitle
            data.articleData?.get(position)?.articleImageUrl?.let { bindImageToHolder(it,itemView.context) }

        }

        private fun bindImageToHolder(image: String,context: Context){
            Glide.with(context)
                    .load(image)
                    .listener(object :RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            loadingProgresBar.visibility = View.GONE
                            return false
                        }

                    })
                    .into(newsImage)
        }
    }
}