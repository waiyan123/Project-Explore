package com.itachi.explore.mvvm.model

interface YoutubeModel{

    fun getAncientList(
        onSuccess : (ArrayList<String>) -> Unit,
        onFailure : (String) -> Unit
    )
}