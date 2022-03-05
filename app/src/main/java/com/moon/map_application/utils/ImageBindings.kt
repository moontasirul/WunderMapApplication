package com.moon.map_application.utils

import android.webkit.URLUtil
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.moon.map_application.utils.extension.ContextExtension.getDrawableSafely


/**
 * Custom binding for Image
 */
class ImageBindings {
    companion object {
        val DEFAULT_IMAGE = "default_image"


        /**
         * Custom binding for Image
         */
        @JvmStatic
        @BindingAdapter("imageUrl", "imageData", "defaultImage")
        fun ImageView.setImageUrl(
            url: String?, imageData: ByteArray?, defaultImage: String
        ) {
            val context = this.context
            val defaultImg = if (defaultImage.isEmpty()) DEFAULT_IMAGE else defaultImage

            try {
                val defaultDrawable = context.getDrawableSafely(defaultImg)
                if (imageData != null) {
                    Glide.with(context).load(imageData)
                        .placeholder(defaultDrawable)
                        .error(defaultDrawable).into(this)
                } else {
                    if (!url.isNullOrEmpty()) {
                        if (URLUtil.isValidUrl(url)) {
                            Glide.with(context).load(url)
                                .placeholder(defaultDrawable)
                                .error(defaultDrawable)
                                .diskCacheStrategy(DiskCacheStrategy.ALL).into(this)
                        } else {
                            Glide.with(context)
                                .load(context.getDrawableSafely(url))
                                .placeholder(defaultDrawable)
                                .error(defaultDrawable).into(this)
                        }
                    } else {
                        Glide.with(context).load(defaultDrawable)
                            .into(this)
                    }
                }
            } catch (ex: Exception) {
                Glide.with(context).load(context.getDrawableSafely(DEFAULT_IMAGE)).into(this)
            }
        }
    }
}