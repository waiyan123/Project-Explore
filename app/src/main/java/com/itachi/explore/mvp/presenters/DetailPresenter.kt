package com.itachi.explore.mvp.presenters

import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.mvvm.model.*
import com.itachi.explore.mvp.views.DetailView
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetailPresenter : BasePresenter<DetailView>(),KoinComponent{

    private val userModel : UserModelImpl by inject()
    private val languageModel : LanguageModelImpl by inject()

//    private val pagodaModel : PagodaModelImpl by inject()
    private val viewsModel : ViewsModelImpl by inject()
//    private val ancientModel : AncientModelImpl by inject()

    private var pagodaVO : PagodaVO? = null
    private var viewVO : ViewVO? = null
    private var ancientVO : AncientVO? = null
    private var uploader_id : String = ""

    init {
        checkUploader()
    }

    fun showPagodaDetails(detail : PagodaVO) {
        mView.showPagodaDetailViews(detail)
        pagodaVO = detail
        uploader_id = pagodaVO!!.uploader_id!!
    }

    fun showViewDetails(detail : ViewVO) {
        mView.showViewDetailViews(detail)
        viewVO = detail
        uploader_id = viewVO!!.uploader_id!!
    }

    fun showAncientDetails(detail : AncientVO) {
        mView.showAncientDetailViews(detail)
        ancientVO = detail
        uploader_id = ancientVO!!.uploader_id!!
    }
    private fun checkUploader() {
        userModel.getUserProfile(
            {
                if(uploader_id==it.facebook_id) mView.showSettingIcon(it.get_is_uploader!!)
            },
            {
                mView.showError(it!!)
            }
        )
    }

    fun clickedDelete() {
        when {
//            pagodaVO!=null -> pagodaModel.deletePagodaById(pagodaVO!!,
//                {
//                    mView.successfullyDeletedItem(it)
//                },
//                {
//                    mView.showError(it)
//                })
            viewVO!=null -> viewsModel.deleteViewById(viewVO!!,
                {
                    mView.successfullyDeletedItem(it)
                },
                {
                    mView.showError(it)
                })
//            ancientVO!=null -> ancientModel.deleteAncientById(ancientVO!!,
//                {
//                    mView.successfullyDeletedItem(it)
//                },
//                {
//                    mView.showError(it)
//                })
        }

    }

    fun clickedEdit() {
        when {
            pagodaVO!=null -> mView.toEditPagoda(pagodaVO)
            viewVO!=null -> mView.toEditView(viewVO)
            ancientVO!=null -> mView.toEditAncient(ancientVO)
        }
    }

    fun checkLanguage() {
        languageModel.getLanguage {
            mView.checkLanguage(it)
        }
    }
}