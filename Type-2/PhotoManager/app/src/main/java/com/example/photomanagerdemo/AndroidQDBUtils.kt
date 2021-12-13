package com.example.photomanagerdemo

import android.content.Context
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi

object AndroidQDBUtils {

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getExif(context: Context, id: String) {
        try {
            val uri:Uri
            uri = Uri.EMPTY
            MediaStore.setRequireOriginal(uri)

        } catch (e: Exception) {
        }
    }
}