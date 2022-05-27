package com.itachi.explore.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.itachi.explore.mvp.presenters.FormPresenter
import com.itachi.explore.mvp.views.FormView
import com.itachi.explore.utils.*
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.dialog_choose_type.*
import kotlin.collections.ArrayList
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.R
import com.itachi.explore.mvvm.viewmodel.FormViewModel
import com.itachi.explore.mvvm.viewmodel.LoginViewModel
import com.itachi.explore.mvvm.viewmodel.MyViewModelProviderFactory
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit


class FormActivity : BaseActivity(), View.OnClickListener {

    private fun changeLanguage(lang: String) {
        when (lang) {
            "en" -> {
                ll_name.hint = getString(R.string.name_en)
                tv_choose_type.text = getString(R.string.choose_type_en)
                ll_created_date.hint = getString(R.string.created_date_en)
                ll_festival_date.hint = getString(R.string.festival_date_en)
                ll_about.hint = getString(R.string.about_en)
                btn_add.text = getString(R.string.add_en)
            }

            "mm_unicode" -> {
                ll_name.hint = getString(R.string.name_mm_unicode)
                tv_choose_type.text = getString(R.string.choose_type_mm_unicode)
                ll_created_date.hint = getString(R.string.created_date_mm_unicode)
                ll_festival_date.hint = getString(R.string.festival_date_mm_unicode)
                ll_about.hint = getString(R.string.about_mm_unicode)
                btn_add.text = getString(R.string.add_mm_unicode)
            }

            "mm_zawgyi" -> {
                ll_name.hint = getString(R.string.name_mm_zawgyi)
                tv_choose_type.text = getString(R.string.choose_type_mm_zawgyi)
                ll_created_date.hint = getString(R.string.created_date_mm_zawgyi)
                ll_festival_date.hint = getString(R.string.festival_date_mm_zawgyi)
                ll_about.hint = getString(R.string.about_mm_zawgyi)
                btn_add.text = getString(R.string.add_mm_zawgyi)
            }
        }
    }

    private fun showEditDetails(
        name: String,
        created: String,
        festival: String,
        about: String,
        type: String
    ) {
        var mName = name
        var mCreated = created
        var mFestival = festival
        var mAbout = about
        if (!MDetect.isUnicode()) {
            mName = Rabbit.uni2zg(mName)
            mCreated = Rabbit.uni2zg(mCreated)
            mFestival = Rabbit.uni2zg(mFestival)
            mAbout = Rabbit.uni2zg(mAbout)
        }
        et_name.setText(mName)
        et_created_date.setText(mCreated)
        et_festival_date.setText(mFestival)
        et_about.setText(mAbout)
        tv_choose_type.text = type
        tv_choose_type.setTextColor(Color.BLUE)
        rl_choose_type.isEnabled = false
        img_choose_type.isEnabled = false
        tv_choose_type.isEnabled = false
        mViewModel.language.observe(this) {
            when(it){
                "en" -> {
                    btn_add.text = getString(R.string.update_en)
                }

                "mm_unicode" -> {
                    btn_add.text = getString(R.string.update_mm_unicode)
                }

                "mm_zawgyi" -> {
                    btn_add.text = getString(R.string.update_mm_zawgyi)
                }
            }

        }
    }

    private fun showProgressLoading() {
        showLoading()
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(alertDialog!!.window!!.attributes)
        lp.width = 350
        lp.height = 300
        alertDialog!!.window!!.attributes = lp
    }

    private fun showPhotoError(error: String) {
        showToast(error)
        hideLoading()
    }

    private fun successAddingItem(name: String) {
        showToast(name)
        hideLoading()
        finish()
    }

    private fun showPickUpImages(list: ArrayList<Uri>) {
        img_photo_item1.setImageResource(R.drawable.ic_tab_unselected_black_24dp)
        img_photo_item2.setImageResource(R.drawable.ic_tab_unselected_black_24dp)
        img_photo_item3.setImageResource(R.drawable.ic_tab_unselected_black_24dp)
        when (list.size) {
            1 -> {
                Glide.with(this)
                    .load(list[0])
                    .into(img_photo_item1)
            }
            2 -> {
                Glide.with(this)
                    .load(list[0])
                    .into(img_photo_item1)
                Glide.with(this)
                    .load(list[1])
                    .into(img_photo_item2)
            }

            3 -> {
                Glide.with(this)
                    .load(list[0])
                    .into(img_photo_item1)
                Glide.with(this)
                    .load(list[1])
                    .into(img_photo_item2)
                Glide.with(this)
                    .load(list[2])
                    .into(img_photo_item3)
            }
        }

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.img_add_photos -> {
                checkPermission(
                    this, REQUEST_EXTERNAL_STORAGE
                ) {
                    mViewModel.chooseMultiplePhotos(it)
                }
            }

            R.id.btn_add -> {
                when (mViewModel.formType) {
                    "Add" -> {
                        if (checkEditTextValidation()) {
                            mViewModel.addItem(
                                et_name.text.toString(),
                                et_created_date.text.toString(),
                                et_festival_date.text.toString(),
                                et_about.text.toString(),
                                tv_choose_type.text.toString(),
                                this
                            )
                        }
                    }
                    "Update" -> {
                        mViewModel.updateItem(
                            et_name.text.toString(),
                            et_created_date.text.toString(),
                            et_festival_date.text.toString(),
                            et_about.text.toString(),
                            tv_choose_type.text.toString()
                        )
                    }
                }
            }

            R.id.tv_choose_type -> {
                showChooseTypeDialog()
            }

            R.id.img_choose_type -> {
                showChooseTypeDialog()
            }

            R.id.tv_pagoda_type -> {
                tv_choose_type.text = PAGODA_TYPE
                tv_choose_type.setTextColor(Color.BLUE)
                tv_choose_type.error = null
                alertDialog!!.dismiss()
            }

            R.id.tv_view_type -> {
                tv_choose_type.text = VIEW_TYPE
                tv_choose_type.setTextColor(Color.BLUE)
                tv_choose_type.error = null
                alertDialog!!.dismiss()
            }

            R.id.tv_ancient_type -> {
                tv_choose_type.text = ANCIENT_TYPE
                tv_choose_type.setTextColor(Color.BLUE)
                tv_choose_type.error = null
                alertDialog!!.dismiss()
            }

            R.id.tv_food_type -> {
                showToast("Coming soon")
//                tv_choose_type.text = FOOD_TYPE
//                tv_choose_type.setTextColor(Color.BLUE)
//                tv_choose_type.error = null
//                alertDialog.dismiss()
            }

            R.id.tv_accessories_type -> {
                showToast("Coming soon")
//                tv_choose_type.text = ACCESSORY_TYPE
//                tv_choose_type.setTextColor(Color.BLUE)
//                tv_choose_type.error = null
//                alertDialog.dismiss()
            }

            R.id.tv_support_type -> {
                showToast("Coming soon")
//                tv_choose_type.text = SUPPORT_TYPE
//                tv_choose_type.setTextColor(Color.BLUE)
//                tv_choose_type.error = null
//                alertDialog.dismiss()
            }

        }
    }

    companion object {
        const val EXTRA_EVENT_ID_PAGODA = "pagoda"
        const val EXTRA_EVENT_ID_VIEW = "view"
        const val EXTRA_EVENT_ID_ANCIENT = "ancient"

        //        const val EXTRA_EVENT_ID_FOOD = "food"
//        const val EXTRA_EVENT_ID_ACCESSORIES = "accessories"
//        const val EXTRA_EVENT_ID_SUPPORTS = "supports"
        fun newIntent(context: Context): Intent {
            return Intent(context, FormActivity::class.java).apply {
            }
        }
    }

    lateinit var mViewModel: FormViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        nestedScrollView.isNestedScrollingEnabled = true

        et_about.setOnTouchListener(OnTouchListener { v, event ->
            if (et_about.hasFocus()) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_SCROLL -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                        return@OnTouchListener true
                    }
                }
            }
            false
        })

        mViewModel =
            ViewModelProvider(this, MyViewModelProviderFactory).get(FormViewModel::class.java)

        img_add_photos.setOnClickListener(this)
        btn_add.setOnClickListener(this)
        tv_choose_type.setOnClickListener(this)
        img_choose_type.setOnClickListener(this)

        val pagodaItem = intent.getSerializableExtra(EXTRA_EVENT_ID_PAGODA) as PagodaVO?
        val viewItem = intent.getSerializableExtra(EXTRA_EVENT_ID_VIEW) as ViewVO?
        val ancientItem = intent.getSerializableExtra(EXTRA_EVENT_ID_ANCIENT) as AncientVO?

        when {
//            pagodaItem!=null -> mPresenter.onEditPagodaDetails(pagodaItem)
//            viewItem!=null -> mPresenter.onEditViewDetails(viewItem)
//            ancientItem!=null -> mPresenter.onEditAncientDetails(ancientItem)
        }
        mViewModel.images.observe(this) {
            showPickUpImages(it)
        }

    }

    override fun onResume() {
        super.onResume()
        mViewModel.progressLoading.observe(this){
            showProgressLoading()
        }
        mViewModel.successMsg.observe(this){
            successAddingItem(it)
        }
        mViewModel.errorMsg.observe(this){
            showToast(it)
        }
        mViewModel.pickupImageError.observe(this){pickupError->
            if(pickupError) {
                tv_pick_up_error.visibility = View.VISIBLE
            } else tv_pick_up_error.visibility = View.GONE

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mViewModel.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            mViewModel.chooseMultiplePhotos(this)
        }
    }

    private fun checkEditTextValidation(): Boolean {
        var valid = false
        ll_name.error = null
        ll_created_date.error = null
        ll_festival_date.error = null
        ll_about.error = null

        if (!mViewModel.checkValidate(et_name)) {
            ll_name.error = "Invalid text"
            ll_name.requestFocus()
        } else if (tv_choose_type.text.toString() == "Choose Type") {
            tv_choose_type.error = "Type required !"
        } else if (!mViewModel.checkValidate(et_created_date)) {
            ll_created_date.error = "Invalid text"
            ll_created_date.requestFocus()
        } else if (!mViewModel.checkValidate(et_festival_date)) {
            ll_festival_date.error = "Invalid text"
            ll_festival_date.requestFocus()
        } else if (!mViewModel.checkValidate(et_about)) {
            ll_about.error = "Invalid text"
            ll_about.requestFocus()
        } else {
            valid = true
        }

        return valid
    }

    @SuppressLint("InflateParams")
    private fun showChooseTypeDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_choose_type, null)
        dialogBuilder.setView(view)
        alertDialog = dialogBuilder.create()
        alertDialog!!.window!!.attributes.windowAnimations = R.style.DialogChosing
        alertDialog!!.show()

        alertDialog!!.tv_pagoda_type.setOnClickListener(this)
        alertDialog!!.tv_view_type.setOnClickListener(this)
        alertDialog!!.tv_ancient_type.setOnClickListener(this)
        alertDialog!!.tv_food_type.setOnClickListener(this)
        alertDialog!!.tv_accessories_type.setOnClickListener(this)
        alertDialog!!.tv_support_type.setOnClickListener(this)
    }


}