package com.itachi.explore.mvp.views

import com.itachi.core.domain.UserVO

interface MainView : BaseView{

    fun showUserInfo(userVO : UserVO)

    fun failedUserInfo(msg : String?)

    fun isUserLogout()

    fun isUploader(uploader : Boolean)

    fun forceUpdate()

    fun dismissUpdate()
}