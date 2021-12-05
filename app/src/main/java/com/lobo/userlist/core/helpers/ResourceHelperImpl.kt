package com.lobo.userlist.core.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.lobo.repogit.core.helpers.ResourceHelper

class ResourceHelperImpl(
    private val context: Context
) : ResourceHelper {

    override fun getString(resId: Int): String = context.getString(resId)

    override fun getString(resId: Int, vararg formatArgs: Any): String = context.getString(resId, formatArgs)

    override fun getDrawable(resId: Int): Drawable? = ContextCompat.getDrawable(context, resId)

    override fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(context, resId)

    override fun getContext(): Context {
        return context
    }
}