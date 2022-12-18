package com.itachi.explore.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
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
import com.itachi.explore.mvvm.viewmodel.UserProfileViewModel
import com.itachi.explore.utils.REQUEST_BACKGROUND_PIC
import com.itachi.explore.utils.REQUEST_PROFILE_PIC
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.dialog_change_user_image.*
import kotlinx.android.synthetic.main.dialog_saveable.*


@AndroidEntryPoint
class UserProfileActivity : BaseActivity(), ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private fun showUserBackgroundPic(url: String) {
        hideLoading()
        Glide.with(applicationContext)
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(img_user_background)
    }

    private fun showUserProfilePic(url: String) {
        hideLoading()
        Glide.with(applicationContext)
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(img_user_profile)
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
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
                onBackPressed()
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

    private fun showProfileReadingMode(userVO: UserVO) {

        if (userVO.get_is_uploader) {
            img_check_user_profile.visibility = View.VISIBLE
        }
        Glide.with(applicationContext)
            .load(userVO.background_pic.url)
            .placeholder(R.drawable.ic_placeholder)
            .into(img_user_background)
        Glide.with(applicationContext)
            .load(userVO.profile_pic.url)
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
                vp_profile.currentItem = 0
            }
            R.id.bot_nav_items -> {
                vp_profile.currentItem = 1
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
    private var mRequestCode = 0
    private val getIntent = chooseImage()


    private val mViewModel: UserProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val userVoItem = intent.getSerializableExtra(EXTRA_EVENT_ID_USER_PROFILE) as UserVO?

        mViewModel.getUser(userVoItem)

        mViewModel.userVOLiveData.observe(this){
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
        mViewModel.profilePicLiveData.observe(this) {
            showUserProfilePic(it.url)
        }
        mViewModel.backgroundPicLiveData.observe(this) {
            showUserBackgroundPic(it.url)
        }
        mViewModel.progressLoadingLiveData.observe(this) {
            if(it) {
                showLoading()
            }else hideLoading()
        }
        mViewModel.responseMessageLiveData.observe(this) {
            showToast(it)
        }
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
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        mViewModel.onActivityResult(requestCode,resultCode,data)
//    }

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
        alertDialog?.let {dialog->
            dialog.window!!.attributes.windowAnimations = R.style.DialogChosing
            dialog.show()

            dialog.btn_change.text = uploadProfileModel.clickAction
            dialog.tv_dialog_user_image_title.text = uploadProfileModel.title
            dialog.tv_dialog_user_image_title.visibility = View.VISIBLE

            Glide.with(applicationContext)
                .load(uploadProfileModel.imagePath)
                .placeholder(R.drawable.ic_placeholder)
                .into(dialog.img_dialog_user_profile)

            dialog.btn_change.setOnClickListener {
                if (uploadProfileModel.clickAction == "Pick up") {
                    dialog.dismiss()
                    if(uploadProfileModel.title == "Profile") {
                        mRequestCode = REQUEST_PROFILE_PIC
                        getIntent.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI))
                    }
                    else {
                        mRequestCode = REQUEST_BACKGROUND_PIC
                        getIntent.launch(Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI))
                    }

                } else if (uploadProfileModel.clickAction == "Change") {
                    dialog.dismiss()
                    mViewModel.onClickedChangeButton(uploadProfileModel)
                }
            }
            dialog.tv_cancel.setOnClickListener {
                alertDialog?.dismiss()
            }
        }
    }

    override fun onBackPressed() {
        if(mViewModel.onEditStatus) {
            showSavableDialog()
            alertDialog?.let {dialog ->
                dialog.btn_save.setOnClickListener {
                    dialog.dismiss()
                    mViewModel.onClickedDoneButton()
                }
            }
        } else {
            super.onBackPressed()
        }

    }

    private fun chooseImage()  = registerForActivityResult(
            StartActivityForResult(),
            ActivityResultCallback { result ->
                result.data?.let {
                    mViewModel.onActivityResult(mRequestCode,result.resultCode,it)
                }
            })
}