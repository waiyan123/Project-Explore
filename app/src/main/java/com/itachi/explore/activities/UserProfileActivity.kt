package com.itachi.explore.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itachi.core.domain.UserVO
import com.itachi.explore.R
import com.itachi.explore.adapters.pager.UserProfilePagerAdapter
import com.itachi.explore.fragments.UserItemsFragment
import com.itachi.explore.fragments.UserTimelineFragment
import com.itachi.explore.mvp.presenters.UserProfilePresenter
import com.itachi.explore.mvp.views.UserProfileView
import com.itachi.explore.utils.REQUEST_BACKGROUND_PIC
import com.itachi.explore.utils.REQUEST_PROFILE_PIC
import com.itachi.explore.view_model.UserProfileViewModel
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.dialog_change_user_image.*


class UserProfileActivity : BaseActivity(), UserProfileView, ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    override fun displayLoading() {
        showLoading()
        val lp = WindowManager.LayoutParams()

        lp.copyFrom(alertDialog!!.window!!.attributes)
        lp.width = 350
        lp.height = 300
        alertDialog!!.window!!.attributes = lp
    }

    override fun showUserBackgroundPic(url: String) {
        hideLoading()
        Glide.with(applicationContext)
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(img_user_background)
    }

    override fun showUserProfilePic(url: String) {
        hideLoading()
        Glide.with(applicationContext)
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(img_user_profile)
    }

    override fun showProfileDialog(image: String, btnText: String) {
        showDialogChangePic(image, btnText)
    }

    override fun showUploadSuccessFul(str: String) {
        hideLoading()
        showToast(str)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_edit_user_profile -> {
                mPresenter.onClickedEditButton()
            }
            R.id.tv_done_user_profile -> {
                mPresenter.onClickedDone(this, et_user_name.text.toString())
            }
            R.id.img_user_background -> {
                mPresenter.onClickedBackgroundPic(this)
            }

            R.id.img_user_profile -> {
                mPresenter.onClickedProfilePic(this)
            }

            R.id.img_back -> {
                finish()
            }
        }
    }

    override fun goToEditMode(userVO: UserVO) {
        mPresenter.setUpMode("edit")
        Log.d("test---","edit mode")
        tv_user_name.visibility = View.GONE
        img_edit_user_profile.visibility = View.GONE

        tv_done_user_profile.visibility = View.VISIBLE
        et_user_name.visibility = View.VISIBLE
        et_user_name.setText(userVO.name)

        img_user_background.isEnabled = true
        img_user_profile.isEnabled = true
        img_user_background.setOnClickListener(this)
        img_user_profile.setOnClickListener(this)
        tv_done_user_profile.setOnClickListener(this)
    }

    override fun goToNormalMode() {
        mPresenter.setUpMode("normal")
        Log.d("test---","normal mode")
        tv_user_name.visibility = View.VISIBLE
        et_user_name.visibility = View.GONE
        img_edit_user_profile.visibility = View.VISIBLE
        img_user_profile.isEnabled = false
        img_user_background.isEnabled = false
        tv_done_user_profile.visibility = View.GONE
    }

    override fun dismissDialog() {
        if (alertDialog!!.isShowing) alertDialog!!.dismiss()
    }

    override fun showUserProfile(userVO: UserVO) {

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
        Log.d("test---","show user profile")

        userProfileViewModel.addItem(userVO)
    }


    override fun showError(error: String) {
        showToast(error)
        hideLoading()
    }

    override fun checkLanguage(lang: String) {

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
    private lateinit var mPresenter: UserProfilePresenter

    private val userProfileViewModel: UserProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        setUpViewPager()

        val userVoItem = intent.getSerializableExtra(EXTRA_EVENT_ID_USER_PROFILE) as UserVO?

        mPresenter = ViewModelProviders.of(this).get(UserProfilePresenter::class.java)
        mPresenter.initPresenter(this)
        mPresenter.onUiReady(userVoItem)

        img_edit_user_profile.setOnClickListener(this)
        tv_done_user_profile.setOnClickListener(this)
        img_back.setOnClickListener(this)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PROFILE_PIC) {
            mPresenter.onClickedProfilePic(this)
        } else if (requestCode == REQUEST_BACKGROUND_PIC) {
            mPresenter.onClickedBackgroundPic(this)
        }
    }

    private fun showDialogChangePic(image: String, btnText: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_change_user_image, null)
        dialogBuilder.setView(view)
        alertDialog = dialogBuilder.create()
        alertDialog!!.window!!.attributes.windowAnimations = R.style.DialogChosing
        alertDialog!!.show()

        alertDialog!!.btn_change.text = btnText

        Glide.with(applicationContext)
            .load(image)
            .placeholder(R.drawable.ic_placeholder)
            .into(alertDialog!!.img_dialog_user_profile)

        alertDialog!!.btn_change.setOnClickListener {
            if (btnText == "Pick up") {
                alertDialog!!.dismiss()
                mPresenter.chooseSinglePhoto(this)
            } else if (btnText == "Change") {
                alertDialog!!.dismiss()
                mPresenter.onClickedChangeBtn(this)
            }
        }
        alertDialog!!.tv_cancel.setOnClickListener {
            mPresenter.onClickedCancel()
        }
    }
}