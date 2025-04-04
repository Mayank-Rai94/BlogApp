package com.example.blog_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.blog_app.Model.BlogItemModel
import com.example.blog_app.databinding.BlogItemBinding

class blogAdapter(private val items: List<BlogItemModel>) : RecyclerView.Adapter<blogAdapter.BlogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        TODO("Not yet implemented")
        val inflater = LayoutInflater.from(parent.context)
        val binding = BlogItemBinding.inflate(inflater,parent,false)
        return BlogViewHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return items.size
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        TODO("Not yet implemented")
        holder.bind(items[position])
    }
    inner class BlogViewHolder(private val binding: BlogItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(blogItemModel: BlogItemModel) {
            binding.heading.text = blogItemModel.heading
            binding.userName.text = blogItemModel.userName
            binding.date.text = blogItemModel.date
            binding.post.text = blogItemModel.post
            binding.likeCount.text  = blogItemModel.likeCount.toString()

        }

    }
}