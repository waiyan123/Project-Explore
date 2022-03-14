//package com.itachi.explore.mvp.presenters
//
//import androidx.lifecycle.Observer
//import com.itachi.core.domain.PagodaVO
//import com.itachi.explore.activities.PagodasActivity
//import com.itachi.explore.mvvm.model.LanguageModelImpl
//import com.itachi.explore.mvvm.model.PagodaModelImpl
//import com.itachi.explore.mvp.views.PagodaView
//import org.koin.core.KoinComponent
//import org.koin.core.inject
//
//class PagodaPresenter : BasePresenter<PagodaView>(),KoinComponent {
//
//    private val pagodaModel : PagodaModelImpl by inject()
//    private val languageModel : LanguageModelImpl by inject()
//    private val photoList : ArrayList<String> = ArrayList()
//    private val pagodaList : ArrayList<PagodaVO> = ArrayList()
//
//    init {
//        getBannerPhotos()
//    }
//
//    private fun getBannerPhotos() {
//        pagodaModel.getPagodaBanner {
//            if(it.size>2) {
//                photoList.clear()
//                photoList.addAll(it)
//                mView.showBannerPhotos(it)
//            }
//            else mView.errorGettingBanner("Banner photos are less than 2")
//        }
//    }
//
//    fun getPagodaList(activity : PagodasActivity) {
//        pagodaModel.getPagodasList {
//            mView.errorGettingPagodaList(it)
//        }
//            .observe(activity,
//                Observer {
//                    mView.showPagodaList(it as ArrayList<PagodaVO>)
//                    pagodaList.clear()
//                    pagodaList.addAll(it)
//                })
//
//    }
//
//    fun clickPagodaItem(index : Int) {
//        mView.navigateToPagodaDetail(pagodaList[index])
//    }
//
//    fun checkLanguage() {
//        languageModel.getLanguage {
//            mView.checkLanguage(it)
//        }
//    }
//
//}