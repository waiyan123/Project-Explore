package com.itachi.explore.mvp.views

import com.itachi.core.domain.AncientVO


interface AncientView : BaseView{

    fun showAncientBackground(bgPic : String)

    fun errorGettingBackground(message : String)

    fun showAncientList(ancientList : ArrayList<AncientVO>)

    fun errorGettingAncientList(error : String)

    fun navigateToAncientDetail(ancientVO : AncientVO)

}