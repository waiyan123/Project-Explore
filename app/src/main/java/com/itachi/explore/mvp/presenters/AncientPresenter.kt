//package com.itachi.explore.mvp.presenters
//
//import com.itachi.core.domain.AncientVO
//import com.itachi.explore.mvvm.model.LanguageModelImpl
//import com.itachi.explore.mvp.views.AncientView
//import org.koin.core.KoinComponent
//import org.koin.core.inject
//
//class AncientPresenter : BasePresenter<AncientView>(), KoinComponent {
//
////    private val ancientModel: AncientModelImpl by inject()
//    private val languageModel : LanguageModelImpl by inject()
//    private val ancientList: ArrayList<AncientVO> = ArrayList()
//
//
//    fun getAncientBackground() {
//        ancientModel.getAncientBackground(
//            {
//                mView.showAncientBackground(it)
//            },
//            {
//                mView.errorGettingBackground(it)
//            }
//        )
//    }
//
//    fun getAncientList() {
//        ancientModel.getAncientList(
//            {
//                mView.showAncientList(it)
//                ancientList.clear()
//                ancientList.addAll(it)
//            }
//            ,
//            {
//                mView.errorGettingAncientList(it)
//            }
//        )
//    }
//
//    fun clickAncientItem(index: Int) {
//        mView.navigateToAncientDetail(ancientList[index])
//    }
//
//    fun checkLanguage() {
//        languageModel.getLanguage {
//            mView.checkLanguage(it)
//        }
//    }
//
//}