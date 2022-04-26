package com.itachi.explore.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.itachi.explore.R
import com.itachi.explore.mvp.presenters.LoginPresenter
import com.itachi.explore.mvp.views.LoginView
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(),LoginView{

    override fun checkLanguage(lang: String) {
        when(lang) {
            "en" -> {
                btn_login_with_facebook.text = getString(R.string.btn_facebook_en)
            }

            "mm_unicode" -> {
                btn_login_with_facebook.text = getString(R.string.btn_facebook_mm_unicode)
            }

            "mm_zawgyi" -> {
                btn_login_with_facebook.text = getString(R.string.btn_facebook_mm_zawgyi)
            }
        }
    }

    override fun displayLoading() {
        showLoading()
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(alertDialog!!.window!!.attributes)
        lp.width = 350
        lp.height = 300
        alertDialog!!.window!!.attributes = lp
    }


    override fun loginSuccess() {
        val intent = IntroActivity.newIntent(this)
        hideLoading()
        startActivity(intent)
        finish()
    }

    override fun loginFailed(message : String) {
        showToast(message)
        hideLoading()
    }

    override fun isAlreadyLogin() {
        val intent = IntroActivity.newIntent(this)
        startActivity(intent)
        finish()
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
//        const val EXTRA_EVENT_ID = "Extra_to_extra"
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java).apply {

            }
        }
    }

    lateinit var mPresenter : LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mPresenter = ViewModelProviders.of(this).get(LoginPresenter::class.java)
        mPresenter.initPresenter(this)
        mPresenter.checkLanguage()

        btn_login_with_facebook.setOnClickListener {
            mPresenter.fbButtonClicked(this)
        }

        Glide.with(this)
            .load(getDrawable(R.drawable.login_background))
            .into(img_login_background)

//        getKeyHashForFacebook()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.onActivityResult(requestCode,resultCode,data)
    }
}