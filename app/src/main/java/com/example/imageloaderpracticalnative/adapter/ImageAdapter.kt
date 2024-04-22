package com.example.imageloaderpracticalnative.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloaderpracticalnative.databinding.ItemImageBinding
import com.example.imageloaderpracticalnative.model.ImageModel
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener

class ImageAdapter : PagingDataAdapter<ImageModel, ImageAdapter.ViewHolder>(DIFF_CALLBACK) {
    private val options: DisplayImageOptions = DisplayImageOptions.Builder()
        .showImageOnLoading(com.example.imageloaderpracticalnative.R.drawable.ic_launcher_foreground)
        .showImageForEmptyUri(com.example.imageloaderpracticalnative.R.drawable.ic_launcher_foreground)
        .showImageOnFail(com.example.imageloaderpracticalnative.R.drawable.ic_launcher_foreground)
        .resetViewBeforeLoading(true)
        .cacheOnDisk(true)
        .cacheInMemory(true)
        .imageScaleType(ImageScaleType.EXACTLY)
        .displayer(FadeInBitmapDisplayer(300))
        .build()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = getItem(position)
        if (image != null) {
            ImageLoader.getInstance()
                .displayImage("${image.thumbnail.domain}/${image.thumbnail.basePath}/0/${image.thumbnail.key}",
                    holder.binding.ivImage,
                    options,
                    object : SimpleImageLoadingListener() {
                        override fun onLoadingStarted(imageUri: String, view: View) {
                            holder.binding.ivImage.visibility = View.GONE
                            holder.binding.progressLoader.visibility = View.VISIBLE
                        }

                        override fun onLoadingFailed(
                            imageUri: String,
                            view: View,
                            failReason: FailReason
                        ) {
                            holder.binding.ivImage.visibility = View.VISIBLE
                            holder.binding.progressLoader.visibility = View.GONE
                        }

                        override fun onLoadingComplete(
                            imageUri: String,
                            view: View,
                            loadedImage: Bitmap
                        ) {
                            holder.binding.ivImage.visibility = View.VISIBLE
                            holder.binding.progressLoader.visibility = View.GONE
                        }
                    }
                )
        }
    }

    inner class ViewHolder(var binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageModel>() {
            override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
