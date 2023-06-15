package com.mukesh.template.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.mukesh.template.BuildConfig
import com.mukesh.template.R
import com.mukesh.template.controller.Controller
import java.util.*


/**
 * Set Animation on any view
 */
fun View.loadAnimation(duration: Long, animationType: Int) {
    try {
        val animation = AnimationUtils.loadAnimation(context, animationType)
        animation.duration = duration
        startAnimation(animation)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


/** -------- HIDE KEYBOARD -------- */
@SuppressLint("ServiceCast")
fun Activity.hideSoftKeyBoard() {
    try {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


/**
 * Show Snack Bar
 * */
fun Activity.showSnackBar(string: String) = try {
    var message = string
    if (message.contains("Unable to resolve host"))
        message = getString(R.string.internet_not_connected)
    Snackbar.make(
        findViewById(android.R.id.content),
        message,
        Snackbar.LENGTH_LONG
    ).apply {
        setBackgroundTint(ContextCompat.getColor(this@showSnackBar, R.color.white))
        animationMode = Snackbar.ANIMATION_MODE_SLIDE
        setTextColor(ContextCompat.getColor(this@showSnackBar, R.color.white))
        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 5
        show()
    }
} catch (e: Exception) {
    e.printStackTrace()
}


/**
 * First Letter Capitalize
 * */
fun String.firstLetterCapitalize() =
    this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }


/**
 * Convert into format
 */
fun String?.convertFormat(format : String = "%.2f"): String{
    return if (this.isNullOrEmpty()) "0.0"
    else String.format(format, this.toDoubleOrNull() ?: 0.0)
}


/**
 * Load Image
 * */
@SuppressLint("CheckResult")
fun AppCompatImageView.loadImage(
    url: String,
    errorPlaceHolder: Int = R.drawable.dummy_image
) = try {
    val circularProgressDrawable = CircularProgressDrawable(this.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }

    val requestOptions = RequestOptions().apply {
        placeholder(circularProgressDrawable)
        error(errorPlaceHolder)
        skipMemoryCache(false)
        sizeMultiplier(1f)
        centerCrop()
    }

    Glide.with(this)
        .load(url)
        .apply(requestOptions)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(errorPlaceHolder)
        .into(this)
} catch (e: Exception) {
    e.printStackTrace()
}


fun showSnackBar(string: String, dialogView: View? = null) = try {
    Controller.context?.get()?.let { context ->
        val message = when {
            string.contains("Failed to connect") ->
                context.getString(STRING_ALIAS.no_internet_connection)

            string.contains("Unable to resolve host") ->
                context.getString(STRING_ALIAS.no_internet_connection)

            else -> string
        }
        Snackbar.make(
            dialogView ?: (context as Activity).findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        ).apply {
            dialogView?.let { anchorView = it }
            setBackgroundTint(ContextCompat.getColor(context, COLOR_ALIAS.teal_700))
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            setTextColor(ContextCompat.getColor(context, COLOR_ALIAS.white))
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 5
            show()
        }
    }
} catch (e: Exception) {
    e.printStackTrace()
}
