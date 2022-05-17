package com.itachi.explore.mvvm.viewmodel

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.itachi.explore.framework.Interactors
import com.itachi.explore.utils.PERMISSION_STORAGE

open class AppViewmodel(protected val interactors: Interactors) : ViewModel(){

    suspend fun checkPermission(context: Context, requestCode : Int, onSuccess : (Context)->Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                "android.permission.READ_EXTERNAL_STORAGE"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                PERMISSION_STORAGE,
                requestCode
            )
        } else {
            onSuccess(context)
        }
    }

}