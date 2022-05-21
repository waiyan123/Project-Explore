//package com.itachi.explore.mvp.presenters
//
//import android.content.Context
//import android.content.Intent
//import com.facebook.CallbackManager
//import com.facebook.FacebookCallback
//import com.facebook.FacebookException
//import com.facebook.login.LoginManager
//import com.facebook.login.LoginResult
//import com.itachi.explore.activities.LoginActivity
//import com.itachi.explore.mvvm.model.LanguageModelImpl
//import com.itachi.explore.mvp.views.LoginView
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//import org.koin.core.KoinComponent
//import org.koin.core.inject
//
//class LoginPresenter : BasePresenter<LoginView>(), KoinComponent {
//
//    private lateinit var callbackManager: CallbackManager
//    private val authModel : AuthenticationModelImpl by inject()
//    private val languageModel : LanguageModelImpl by inject()
//
//    override fun initPresenter(view: LoginView) {
//        super.initPresenter(view)
//        callbackManager = CallbackManager.Factory.create()
//
//        if(authModel.isAlreadyLogin()) mView.isAlreadyLogin()
//    }
//
//    fun fbButtonClicked(mContext : Context) {
//        LoginManager.getInstance().logInWithReadPermissions(mContext as LoginActivity , listOf("public_profile","email"))
//        LoginManager.getInstance().registerCallback(callbackManager,object : FacebookCallback<LoginResult>{
//            override fun onSuccess(result: LoginResult?) {
//                authModel.firebaseAuthWithFacebook(result!!.accessToken,
//                    {
//                        observable ->
//                        observable.subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe {
//                                mView.loginSuccess()
//                            }
//                    },
//                    {
//                        mView.loginFailed(it)
//                    })
//
//            }
//
//            override fun onCancel() {
//                mView.loginFailed("Cancel login")
//            }
//
//            override fun onError(error: FacebookException?) {
//                mView.loginFailed(error!!.localizedMessage)
//            }
//        })
//
//    }
//
//    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
//        mView.displayLoading()
//        callbackManager.onActivityResult(requestCode,resultCode,data)
//
//    }
//
//    fun checkLanguage() {
//        languageModel.getLanguage {
//            mView.checkLanguage(it)
//        }
//    }
//
//}