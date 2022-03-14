package com.itachi.explore.mvp.presenters

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.itachi.explore.mvp.views.BaseView
import com.itachi.explore.utils.PERMISSION_STORAGE
import com.itachi.explore.utils.REQUEST_EXTERNAL_STORAGE

abstract class BasePresenter<V : BaseView> : ViewModel() {

    protected lateinit var mView: V

    open fun initPresenter(view : V) {
        mView = view
    }

    open fun checkPermission(context: Context,requestCode : Int,onSuccess : (Context)->Unit) {
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