package com.itachi.explore.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itachi.core.domain.UserVO
import com.itachi.explore.R
import com.itachi.explore.adapters.pager.UserProfilePagerAdapter
import com.itachi.explore.fragments.UserItemsFragment
import com.itachi.explore.fragments.UserTimelineFragment
import com.itachi.explore.mvvm.model.UserProfileUploadDialogModel
import com.itachi.explore.utils.REQUEST_BACKGROUND_PIC
import com.itachi.explore.utils.REQUEST_PROFILE_PIC
import com.itachi.explore.mvvm.viewmodel.UserProfileViewModel
import com.itachi.explore.utils.REQUEST_EXTERNAL_STORAGE
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.MimeType
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import com.sangcomz.fishbun.define.Define
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.dialog_change_user_image.*

@AndroidEntryPoint
class UserProfileActivity : BaseActivity(), ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    fun displayLoading() {
        showLoading()
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(alertDialog!!.window!!.attributes)
        lp.width = 350
        lp.height = 300
        alertDialog!!.window!!.attributes = lp
    }

    fun showUserBackgroundPic(url: String) {
        hideLoading()
        Glide.with(applicationContext)
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(img_user_background)
    }

    fun showUserProfilePic(url: String) {
        hideLoading()
        Glide.with(applicationContext)
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(img_user_profile)
    }

    fun showUploadSuccessFul(str: String) {
        hideLoading()
        showToast(str)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_edit_user_profile -> {
//                mPresenter.onClickedEditButton()
            }
            R.id.tv_done_user_profile -> {
//                mPresenter.onClickedDone(this, et_user_name.text.toString())
            }
            R.id.img_user_background -> {
                checkPermission(this, REQUEST_BACKGROUND_PIC) {

                    mViewModel.onClickedUserBackgroundPic()
                }
            }

            R.id.img_user_profile -> {
                checkPermission(this, REQUEST_PROFILE_PIC) {
                    mViewModel.onClickedUserProfilePic()
                }
            }

            R.id.img_back -> {
                finish()
            }
        }
    }

    private fun onEditProfile() {
//        mPresenter.setUpMode("edit")
        tv_user_name.visibility = View.GONE
        img_edit_user_profile.visibility = View.GONE

        tv_done_user_profile.visibility = View.VISIBLE
        et_user_name.visibility = View.VISIBLE
        et_user_name.setText(tv_user_name.text)

        img_user_background.isEnabled = true
        img_user_profile.isEnabled = true
        img_user_background.setOnClickListener(this)
        img_user_profile.setOnClickListener(this)
        tv_done_user_profile.setOnClickListener {
            mViewModel.onClickedDoneButton()
        }
    }

    private fun showProfileEditableMode() {
//        mPresenter.setUpMode("normal")
        tv_user_name.visibility = View.VISIBLE
        et_user_name.visibility = View.GONE
        img_edit_user_profile.visibility = View.VISIBLE
        img_user_profile.isEnabled = false
        img_user_background.isEnabled = false
        tv_done_user_profile.visibility = View.GONE
        img_edit_user_profile.setOnClickListener {
            mViewModel.onClickedEditButton()
        }
    }

    fun dismissDialog() {
        if (alertDialog!!.isShowing) alertDialog!!.dismiss()
    }

    private fun showProfileReadingMode(userVO: UserVO) {

        if (userVO.get_is_uploader == true) {
            img_check_user_profile.visibility = View.VISIBLE
        }
        Glide.with(applicationContext)
            .load(userVO.background_pic!!.url)
            .placeholder(R.drawable.ic_placeholder)
            .into(img_user_background)
        Glide.with(applicationContext)
            .load(userVO.profile_pic!!.url)
            .placeholder(R.drawable.ic_placeholder)
            .into(img_user_profile)
        tv_user_name.text = userVO.name

        mViewModel.addItem(userVO)
    }


    fun showError(error: String) {
        showToast(error)
        hideLoading()
    }

    fun checkLanguage(lang: String) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        bnv_profile.menu.getItem(position).isChecked = true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bot_nav_timeline -> {
                vp_profile.setCurrentItem(0)
            }
            R.id.bot_nav_items -> {
                vp_profile.setCurrentItem(1)
            }

        }
        return true
    }

    companion object {
        const val EXTRA_EVENT_ID_USER_PROFILE = "Extra_event_id_user_profile"
        fun newIntent(context: Context): Intent {
            return Intent(context, UserProfileActivity::class.java).apply {
            }
        }
    }

    private lateinit var pagerAdapter: UserProfilePagerAdapter

    private val mViewModel: UserProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val userVoItem = intent.getSerializableExtra(EXTRA_EVENT_ID_USER_PROFILE) as UserVO?

        mViewModel.getUser(userVoItem).observe(this){
            showProfileReadingMode(it)
        }

        setUpViewPager()

        img_edit_user_profile.setOnClickListener(this)
        tv_done_user_profile.setOnClickListener(this)
        img_back.setOnClickListener(this)

        mViewModel.editable.observe(this) { editable ->
            if (editable) {
                showProfileEditableMode()
            }
        }

        mViewModel.onEdit.observe(this) { onEdit ->
            if (onEdit) {
                onEditProfile()
            } else {
                showProfileEditableMode()
            }
        }

        mViewModel.mutableUploadProfileModel.observe(this){
            showPickupImageDialog(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setUpViewPager() {
        pagerAdapter = UserProfilePagerAdapter(supportFragmentManager)
        val userTimelineFragment = UserTimelineFragment()
        val userItemsfragment = UserItemsFragment()
        pagerAdapter.addFragment(userTimelineFragment)
        pagerAdapter.addFragment(userItemsfragment)
        vp_profile.adapter = pagerAdapter

        bnv_profile.setOnNavigationItemSelectedListener(this)
        vp_profile.setOnPageChangeListener(this)
        vp_profile.currentItem = 1
    }

    //incoming with pickup image urls
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mViewModel.onActivityResult(requestCode,resultCode,data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PROFILE_PIC && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {

            mViewModel.onClickedUserProfilePic()
        }
        else if (requestCode == REQUEST_BACKGROUND_PIC && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {

            mViewModel.onClickedUserBackgroundPic()
        }
    }

    private fun showPickupImageDialog(uploadProfileModel : UserProfileUploadDialogModel
                                      = UserProfileUploadDialogModel()
    ) {
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_change_user_image, null)

        dialogBuilder.setView(view)
        alertDialog = dialogBuilder.create()
        alertDialog!!.window!!.attributes.windowAnimations = R.style.DialogChosing
        alertDialog!!.show()

        alertDialog!!.btn_change.text = uploadProfileModel.clickAction
        alertDialog!!.tv_dialog_user_image_title.text = uploadProfileModel.title
        alertDialog!!.tv_dialog_user_image_title.visibility = View.VISIBLE

        Glide.with(applicationContext)
            .load(uploadProfileModel.imagePath)
            .placeholder(R.drawable.ic_placeholder)
            .into(alertDialog!!.img_dialog_user_profile)

        alertDialog!!.btn_change.setOnClickListener {
            if (uploadProfileModel.clickAction == "Pick up") {
                alertDialog!!.dismiss()
                if(uploadProfileModel.title == "Profile") {
                    startActivityForResult(Intent(Intent.ACTION_PICK), REQUEST_PROFILE_PIC)
                }
                else {
                    startActivityForResult(Intent(Intent.ACTION_PICK), REQUEST_BACKGROUND_PIC)
                }

            } else if (uploadProfileModel.clickAction == "Change") {
                alertDialog!!.dismiss()
                mViewModel.onClickedChangeButton(uploadProfileModel)
            }
        }
        alertDialog!!.tv_cancel.setOnClickListener {
            alertDialog?.dismiss()
        }
    }

}