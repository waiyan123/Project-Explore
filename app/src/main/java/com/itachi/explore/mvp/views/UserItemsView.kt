package com.itachi.explore.mvp.views

import com.itachi.core.domain.ItemVO


interface UserItemsView : BaseView{

    fun showUserItems(items : List<ItemVO>)

    fun showError(str : String)

    fun navigateToFormActivity(itemVO: ItemVO)

    fun navigateToDetailsActivity(itemVO: ItemVO)
}