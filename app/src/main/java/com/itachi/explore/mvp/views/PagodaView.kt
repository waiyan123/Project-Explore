package com.itachi.explore.mvp.views

import com.itachi.core.domain.PagodaVO

interface PagodaView : BaseView {

    fun showBannerPhotos(photoList : ArrayList<String>)

    fun errorGettingBanner(message : String)

    fun navigateToFormActivity()

    fun showPagodaList(pagodaList : ArrayList<PagodaVO>)

    fun errorGettingPagodaList(error : String)

    fun navigateToPagodaDetail(pagodaVO : PagodaVO)

}