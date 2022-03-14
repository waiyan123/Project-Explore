package com.itachi.explore.mvp.views

import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.ViewVO

interface DetailView : BaseView{

    fun showPagodaDetailViews(detail : PagodaVO)

    fun showViewDetailViews(detail : ViewVO)

    fun showAncientDetailViews(detail : AncientVO)

    fun showSettingIcon(show : Boolean)

    fun showError(error : String)

    fun successfullyDeletedItem(message : String)

    fun toEditPagoda(pagodaVO: PagodaVO?)

    fun toEditView(viewVO: ViewVO?)

    fun toEditAncient(ancientVO: AncientVO?)

}