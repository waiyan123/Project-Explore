package com.itachi.explore.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProviders
import com.itachi.explore.R
import com.itachi.explore.mvp.presenters.IntroPresenter
import com.itachi.explore.mvp.views.IntroView
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.dialog_intro.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit


class IntroActivity : BaseActivity(),IntroView,RadioGroup.OnCheckedChangeListener {

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1) {
            R.id.toggle_eng -> {
                mPresenter.setLanguage("en",this)
            }
            R.id.toggle_mm -> {
                mPresenter.setLanguage("mm",this)
            }
        }
    }

    override fun navigateToMainActivity() {
        startActivity(MainActivity.newIntent(this))
        finish()
    }

    override fun showIntro(intro: String) {
                                                                // changed to hard coded string
        var mIntro = intro
        if(!MDetect.isUnicode()) {
            mIntro = Rabbit.uni2zg(mIntro)
        }

        tv_intro_body.visibility = View.VISIBLE
        tv_intro_body.text = mIntro
    }

    override fun showError(error: String) {
        showToast(error)
    }

    override fun checkLanguage(lang: String) {
        when(lang) {
            "en" -> {
                tv_continue.text = getString(R.string.continue_en)
                alertDialog!!.tv_welcome.text = getString(R.string.welcome_text_en)
                alertDialog!!.tv_got_it.text = getString(R.string.got_it_en)
                tv_dont_show.text = getString(R.string.dont_show_en)

                tv_intro_title.text = getString(R.string.intro_title_en)
                tv_intro_body.text = getString(R.string.intro_body_en)

                toggle_eng.isChecked = true
            }

            "mm_unicode" -> {
                tv_continue.text = getString(R.string.continue_mm_unicode)
                alertDialog!!.tv_welcome.text = getString(R.string.welcome_text_mm_unicode)
                alertDialog!!.tv_got_it.text = getString(R.string.got_it_mm_unicode)
                tv_dont_show.text = getString(R.string.dont_show_mm_unicode)

                tv_intro_title.text = getString(R.string.intro_title_mm_unicode)
                tv_intro_body.text = getString(R.string.intro_body_mm_unicode)

                toggle_mm.isChecked = true
            }

            "mm_zawgyi" -> {
                tv_continue.text = getString(R.string.continue_mm_zawgyi)
                alertDialog!!.tv_welcome.text = getString(R.string.welcome_text_mm_zawgyi)
                alertDialog!!.tv_got_it.text = getString(R.string.got_it_mm_zawgyi)
                tv_dont_show.text = getString(R.string.dont_show_mm_zawgyi)

                tv_intro_title.text = getString(R.string.intro_title_mm_zawgyi)
                tv_intro_body.text = getString(R.string.intro_body_mm_zawgyi)

                toggle_mm.isChecked = true
            }
        }
    }

    companion object {
        //        const val EXTRA_EVENT_ID = "Extra_to_extra"
        fun newIntent(context: Context): Intent {
            return Intent(context, IntroActivity::class.java).apply {
            }
        }
    }

    private fun showIntroDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_intro, null)
        dialogBuilder.setView(view)
        alertDialog = dialogBuilder.create()
        val window: Window = alertDialog!!.window!!
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        alertDialog!!.window!!.attributes.windowAnimations = R.style.DialogChosing
        alertDialog!!.setCancelable(true)
        alertDialog!!.show()

        alertDialog!!.tv_got_it.setOnClickListener {
            alertDialog!!.dismiss()
        }
    }

    private lateinit var mPresenter : IntroPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        showIntroDialog()

        mPresenter = ViewModelProviders.of(this).get(IntroPresenter::class.java)
        mPresenter.initPresenter(this)
        mPresenter.checkLanguage()

        tv_continue.setOnClickListener {
            mPresenter.onClickedContinue(cb_dont_show.isChecked)
        }

        toggle_language.setOnCheckedChangeListener(this)

    }
}