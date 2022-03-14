package com.itachi.explore.mvp.views

interface LoginView : BaseView{

    fun loginSuccess()

    fun loginFailed(msg : String)

    fun isAlreadyLogin()

    fun displayLoading()
}