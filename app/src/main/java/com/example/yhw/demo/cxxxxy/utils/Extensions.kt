package com.example.yhw.demo.cxxxxy.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

val Int.px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun NavController.push(@IdRes id: Int, animated: Boolean = true, args: Bundle? = null) {
    var navOptions: NavOptions? = null
    if (animated) {
        navOptions = NavOptions.Builder()
            .setEnterAnim(android.R.anim.slide_in_left)
            .setExitAnim(android.R.anim.slide_out_right)
            .setPopEnterAnim(android.R.anim.slide_in_left)
            .setPopExitAnim(android.R.anim.slide_out_right)
//            .setPopUpTo(android.R.anim.slide_out_right)
            .build()
    }
    navigate(id, args, navOptions)
}