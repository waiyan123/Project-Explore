package com.itachi.explore.mvp.views

import com.itachi.core.domain.UserVO

interface UserProfileView : BaseView {

    fun showUserProfile(userVO : UserVO)

    fun showUserProfilePic(url : String)

    fun showUserBackgroundPic(url : String)

    fun showProfileDialog(image : String,btnText : String)

    fun goToNormalMode()

    fun dismissDialog()

    fun showUploadSuccessFul(str : String)

    fun showError(error : String)

    fun goToEditMode(userVO : UserVO)

    fun displayLoading()
}