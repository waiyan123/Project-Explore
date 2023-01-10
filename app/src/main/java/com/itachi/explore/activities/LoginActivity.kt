package com.itachi.explore.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.itachi.explore.R
import com.itachi.explore.mvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private fun changeLanguage(lang: String) {
        when (lang) {
            "en" -> {
                btn_login_with_google.text = getString(R.string.btn_google_en)
            }

            "mm_unicode" -> {
                btn_login_with_google.text = getString(R.string.btn_google_mm_unicode)
            }

            "mm_zawgyi" -> {
                btn_login_with_google.text = getString(R.string.btn_google_mm_zawgyi)
            }
        }
    }

    private fun displayLoading() {
        showLoading()

    }

    private fun loginFailed(message: String) {
        showToast(message)
        hideLoading()
    }

    private fun navigateToIntroActivity() {
        val intent = IntroActivity.newIntent(this)
        hideLoading()
        startActivity(intent)
        finish()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java).apply {

            }
        }
    }

    private val mViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btn_login_with_google.setOnClickListener {
            mViewModel.signInWithGoogle(this)
        }

        Glide.with(this)
            .load(getDrawable(R.drawable.login_background))
            .into(img_login_background)

        mViewModel.isAlreadyLogin.observe(this) { success ->
            if (success) {
                navigateToIntroActivity()
            }
        }
        mViewModel.errorMessage.observe(this) {
            loginFailed(it)
        }
        mViewModel.loginSuccess.observe(this) { success ->
            if (success) {
                showToast("Login successful")
                navigateToIntroActivity()
            }
        }
        mViewModel.displayLoading.observe(this) { loading ->
            if (loading) {
                displayLoading()
            }
        }
        mViewModel.language.observe(this) { language->
            changeLanguage(language)
        }

//        getKeyHashForFacebook()
    }

    override fun onResume() {
        super.onResume()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mViewModel.onActivityResult(requestCode, resultCode, data)
    }
}