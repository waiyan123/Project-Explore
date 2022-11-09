package com.itachi.explore.mvvm.model

import android.graphics.drawable.Drawable

data class UserProfileUploadDialogModel(
    var title : String = "",
    var imagePath : String = "",
    var cancel : String = "Cancel",
    var clickAction : String = ""
)
