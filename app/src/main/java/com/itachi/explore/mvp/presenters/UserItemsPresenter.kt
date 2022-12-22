//package com.itachi.explore.mvp.presenters
//
//import com.itachi.core.domain.ItemVO
//import com.itachi.core.domain.UserVO
//import com.itachi.explore.mvvm.model.*
//import com.itachi.explore.mvp.views.UserItemsView
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//import org.koin.core.KoinComponent
//import org.koin.core.inject
//
//class UserItemsPresenter : BasePresenter<UserItemsView>(), KoinComponent {
//
//    private val languageModel: LanguageModelImpl by inject()
////    private val pagodaModel: PagodaModelImpl by inject()
//    private val viewsModel: ViewsModelImpl by inject()
////    private val ancientModel: AncientModelImpl by inject()
//
//    private val userModel: UserModelImpl by inject()
//
//    private lateinit var itemsList: ArrayList<ItemVO>
//
//    private var mEditable: Boolean = false
//
//    private lateinit var mUserVO: UserVO
//
//    fun onUiReady(userVO: UserVO) {
//        userModel.getUserFromDb(
//            {
//                mEditable = userVO.facebook_id == it.facebook_id
//                mUserVO = userVO
//            },
//            {
//
//            }
//        )
//        getUserItems(userVO.facebook_id)
//    }
//
//    private fun getUserItems(userId: String) {
//        itemsList = ArrayList<ItemVO>()
//        itemsList.clear()
////        pagodaModel.getPagodaListByUserId(userId,
////            { observable ->
////                observable.subscribeOn(Schedulers.io())
////                    .observeOn(AndroidSchedulers.mainThread())
////                    .subscribe {
////                        it.forEach { vo ->
////                            vo.editable = mEditable
////                            itemsList.add(vo)
////                        }
//////                                itemsList.addAll(it)
////                        mView.showUserItems(itemsList)
////
////                    }
////            },
////            {
////                mView.showError(it)
////            })
//
//        viewsModel.getViewsListByUserId(userId,
//            { observable ->
//                observable.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        it.forEach { vo ->
//                            vo.editable = mEditable
//                            itemsList.add(vo)
//                        }
////                                itemsList.addAll(it)
//                        mView.showUserItems(itemsList)
//                    }
//            },
//            {
//                mView.showError(it)
//            })
//
////        ancientModel.getAncientsListByUserId(userId,
////            { observable ->
////                observable.subscribeOn(Schedulers.io())
////                    .observeOn(AndroidSchedulers.mainThread())
////                    .subscribe {
////                        it.forEach { vo ->
////                            vo.editable = mEditable
////                            itemsList.add(vo)
////                        }
//////                                itemsList.addAll(it)
////                        mView.showUserItems(itemsList)
////                    }
////            },
////            {
////                mView.showError(it)
////            })
//    }
//
//    fun onClickedEditBtn(position: Int) {
//        mView.navigateToFormActivity(itemsList[position])
//    }
//
//    fun onClickedViewBtn(position: Int) {
//        mView.navigateToDetailsActivity(itemsList[position])
//    }
//
//}