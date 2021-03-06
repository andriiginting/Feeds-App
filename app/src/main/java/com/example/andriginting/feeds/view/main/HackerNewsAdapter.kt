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
import com.example.andriginting.feeds.repo.remote.hackernews.HackerNewsResponse
import com.example.andriginting.feeds.viewmodel.FeedsViewModel

class HackerNewsAdapter(listViewModel: FeedsViewModel,
                        lifecycleOwner: LifecycleOwner) :
        RecyclerView.Adapter<HackerNewsAdapter.HackerNewsHolder>() {

    private var listHackerNews: ArrayList<HackerNewsResponse> = ArrayList()

    init {

        listViewModel.getHackerNews().observe(lifecycleOwner, Observer<List<HackerNewsResponse>> { repos ->
            listHackerNews.clear()
            if (repos != null) {
                listHackerNews.addAll(repos)
                notifyDataSetChanged()
            }
        }) //hacker news
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HackerNewsHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hacker_feeds_content_item, parent, false)
        return HackerNewsHolder(view)
    }

    override fun getItemCount(): Int {
        return listHackerNews.size
    }

    override fun onBindViewHolder(holder: HackerNewsHolder, position: Int) {
        holder.bindHackerItem(listHackerNews[position])
    }

    class HackerNewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.title_hacker_article)
        private var storyType: TextView = itemView.findViewById(R.id.type_hacker_article)
        private var hackerNewsImage: ImageView = itemView.findViewById(R.id.hacker_image_article)


        @SuppressLint("SetTextI18n")
        fun bindHackerItem(data: HackerNewsResponse) {
            title.text = data.storiesTitle?.capitalize()
            storyType.text = data.newsType?.capitalize()
            bindImageToHolder("https://lelogama.go-jek.com/banner/goride_mobile.jpeg",
                    itemView.context, hackerNewsImage)

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
