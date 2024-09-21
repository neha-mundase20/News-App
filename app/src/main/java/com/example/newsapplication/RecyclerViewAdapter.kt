package com.example.newsapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter(private val articles: ApiResponse?) : RecyclerView.Adapter<RecyclerViewAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsTitle: TextView = itemView.findViewById(R.id.NewsTitle)
        val newsImage: ImageView = itemView.findViewById(R.id.NewsImage)
        val description: TextView = itemView.findViewById(R.id.NewsDescription)
        val source: TextView = itemView.findViewById(R.id.NewsSource)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return NewsViewHolder(viewLayout)
    }

    override fun getItemCount(): Int {
        return articles?.articles?.size ?: 0 // Safely handle null
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = articles?.articles?.get(position) // Safely handle null

        currentItem?.let { item ->
            holder.newsTitle.text = item.title
            holder.source.text = item.source.name // Display the source name
            holder.description.text = item.description
            val imageURL = item.urlToImage

            // Load image using Glide
            Glide.with(holder.itemView.context)
                .load(imageURL)
                .into(holder.newsImage)
        }
    }
}