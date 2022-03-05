@file:Suppress("DEPRECATION")

package com.moon.map_application.utils.extension


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.DrawableRes


object ContextExtension {
    private var sLock = Any()
    private var sTempValue: TypedValue? = null

    /**
     * ResourceType Enum
     */
    enum class ResourceType(val resource: String) {
        DRAWABLE("drawable"),
        STRING("string"),
        DIMEN("dimen");

        companion object {
            fun fromString(type: String): ResourceType? {
                return values().firstOrNull { it.resource == type }
            }
        }
    }

    /**
     * Get Image from Drwabale by Image Name
     */
    fun Context.getImageFromDrawable(imageName: String): Int {
        return this.resources.getIdentifier(imageName, "drawable", this.packageName)
    }

    /**
     * Get ResourceId by Name and type
     */
    fun Context.getResourceId(
        pVariableName: String,
        pResourceType: ResourceType
    ): Int {
        val resourceName = pResourceType.resource
        return try {
            this.resources.getIdentifier(pVariableName, resourceName, this.packageName)
        } catch (e: Exception) {
            -1
        }
    }

    /**
     * showLoadingDialog
     */
    fun Context.showLoadingDialog(): ProgressBar {
        val progressDialog = ProgressBar(this)
        progressDialog.visibility = View.VISIBLE
        progressDialog.background = ColorDrawable(Color.TRANSPARENT)
        progressDialog.isIndeterminate = true
        return progressDialog
    }

    /**
     * getDrawableSafely By Drawable name
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun Context.getDrawableSafely(drawableResourceName: String): Drawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.resources.getDrawable(
                this.getImageFromDrawable(drawableResourceName), null
            )
        } else {
            this.resources.getDrawable(
                this.getImageFromDrawable(drawableResourceName)
            )
        }
    }

    /**
     * getDrawableSafely By DrawableRes Id
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun Context.getDrawableSafely(@DrawableRes id: Int): Drawable? {
        return when {
            true -> {
                this.getDrawable(id)
            }
            true -> {
                this.resources.getDrawable(id)
            }
            else -> {
                var resolvedId: Int = 0
                synchronized(sLock) {
                    if (sTempValue == null) {
                        sTempValue = TypedValue()
                    }
                    this.resources.getValue(id, sTempValue, true)
                    sTempValue?.let {
                        resolvedId = it.resourceId
                    }

                }
                this.resources.getDrawable(resolvedId)
            }
        }
    }

}