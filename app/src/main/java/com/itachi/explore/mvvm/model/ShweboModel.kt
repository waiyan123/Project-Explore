package com.itachi.explore.mvvm.model

interface ShweboModel {

    fun getIntro(
        onSuccess : (String) -> Unit,
        onFailure : (String) -> Unit
    )
}