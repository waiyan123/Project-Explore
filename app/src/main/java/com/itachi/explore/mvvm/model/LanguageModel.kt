package com.itachi.explore.mvvm.model

import androidx.lifecycle.LiveData

interface LanguageModel {

    fun getLanguage(
        lang : (String) -> Unit
    )

    fun setLanguage(lang : String) : LiveData<String>
}