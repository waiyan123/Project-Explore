//package com.itachi.explore.mvp.presenters
//
//import com.itachi.core.domain.UploadedPhotoVO
//import com.itachi.explore.mvvm.model.LanguageModelImpl
//import com.itachi.explore.mvvm.model.ViewsModelImpl
//import com.itachi.explore.mvp.views.ViewsView
//import org.koin.core.KoinComponent
//import org.koin.core.inject
//
//class ViewsPresenter : BasePresenter<ViewsView>(),KoinComponent{
//
//    private val viewsModelImpl : ViewsModelImpl by inject()
//    private val languageModel : LanguageModelImpl by inject()
//    private val uploadedPhotoVOList : ArrayList<UploadedPhotoVO> = ArrayList()
//
//    init {
//        showPhotoList()
//    }
//
//    fun showPhotoList() {
//        viewsModelImpl.getPhotoViews(
//            {
//                mView.showPhotoViews(it)
//                uploadedPhotoVOList.clear()
//                uploadedPhotoVOList.addAll(it)
//            },
//            {
//                mView.showError(it)
//            }
//        )
//    }
//
//    fun clickViewItem(index : Int) {
//        mView.navigateViewPhoto(uploadedPhotoVOList[index])
//    }
//
//    fun checkLanguage() {
//        languageModel.getLanguage {
//            mView.checkLanguage(it)
//        }
//    }
//}