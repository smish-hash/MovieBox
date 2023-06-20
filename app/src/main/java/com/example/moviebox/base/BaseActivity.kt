package com.example.moviebox.base

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity: ComponentActivity() {
    fun showSmallToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun debugLog(tag: String, message: String) {
        Log.d(tag, message)
    }
}