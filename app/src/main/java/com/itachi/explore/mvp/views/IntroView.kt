package com.itachi.explore.mvp.views

interface IntroView : BaseView{

    fun showIntro(intro : String)

    fun showError(error : String)

    fun navigateToMainActivity()

}