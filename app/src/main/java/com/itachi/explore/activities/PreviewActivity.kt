package com.itachi.explore.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.itachi.core.domain.*
import com.itachi.explore.R
import com.itachi.explore.adapters.recycler.RelatedViewsRecyclerAdapter
import com.itachi.explore.mvp.presenters.PreviewPresenter
import com.itachi.explore.mvp.views.PreviewView
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.activity_preview.img_back
import kotlinx.android.synthetic.main.bottom_sheet_views.*
import kotlinx.android.synthetic.main.dialog_zoomage_view.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit

class PreviewActivity : BaseActivity(),PreviewView{

    override fun showZoomageDialog(url: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_zoomage_view, null)
        dialogBuilder.setView(view)
        alertDialog = dialogBuilder.create()
        alertDialog!!.show()
        Glide.with(applicationContext)
            .load(url)
            .into(alertDialog!!.img_zoomage)
        alertDialog!!.img_cancel.setOnClickListener {
            alertDialog!!.dismiss()
        }
    }

    override fun navigateToGoogleMap(geoPoints: String) {
        val gmmIntentUri = Uri.parse("google.navigation:q=$geoPoints")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.resolveActivity(packageManager)?.let {
            startActivity(mapIntent)
        }
    }

    override fun isGeoPointsValid(valid: Boolean) {
        when(valid) {
            true -> {
                tv_google_map.visibility = View.VISIBLE
                tv_google_map.setOnClickListener {
                    mPresenter.onClickedNavigateToGoogleMap()
                }
            }
            false -> {
                tv_google_map.visibility = View.GONE
            }
        }
    }

    override fun checkLanguage(lang: String) {
        when(lang) {
            "en" -> {
                tv_uploader.text = getString(R.string.uploader_en)
                btn_details.text = getString(R.string.details_en)
                tv_related_views.text = getString(R.string.related_views_en)
            }

            "mm_unicode" -> {
                tv_uploader.text = getString(R.string.uploader_mm_unicode)
                btn_details.text = getString(R.string.details_mm_unicode)
                tv_related_views.text = getString(R.string.related_views_mm_unicode)
            }

            "mm_zawgyi" -> {
                tv_uploader.text = getString(R.string.uploader_mm_zawgyi)
                btn_details.text = getString(R.string.details_mm_zawgyi)
                tv_related_views.text = getString(R.string.related_views_mm_zawgyi)
            }
        }
    }

    override fun showUploader(userVO: UserVO) {
        var name = buildString {
            append(tv_uploader.text )
            append(" ")
            append(userVO.name)
        }
        if(!MDetect.isUnicode()) {
            name = Rabbit.uni2zg(name)
        }

        tv_uploader.text = name
        tv_uploader.setOnClickListener {
            val intent = UserProfileActivity.newIntent(this)
            intent.putExtra(UserProfileActivity.EXTRA_EVENT_ID_USER_PROFILE,userVO)
            startActivity(intent)
        }
    }

    override fun onClickedImageItem(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(img_image_preview)
    }

    override fun showFullInfo(title : String ,photos : ArrayList<PhotoVO>) {
        var mTitle = title

        if(!MDetect.isUnicode()) {
            mTitle = Rabbit.uni2zg(title)
        }
        tv_title.text = mTitle
        photos.let {
            val list = ArrayList<String>()
            it.forEach {photoVO->
                list.add(photoVO.url!!)
            }
            relatedRecyclerAdapter.setNewData(list)
        }
    }

    override fun showError(error: String) {
        showToast(error)
    }

    override fun onClickedBackButton() {
        finish()
    }

    override fun onClickedDetailButtonWithPagodaVO(pagodaVO: PagodaVO) {
        val intent = ActivityDetail.newIntent(this)
        intent.putExtra(ActivityDetail.EXTRA_EVENT_ID_PAGODA,pagodaVO)
        startActivity(intent)
        finish()
    }

    override fun onClickedDetailButtonWithViewVO(viewVO: ViewVO) {
        val intent = ActivityDetail.newIntent(this)
        intent.putExtra(ActivityDetail.EXTRA_EVENT_ID_VIEW,viewVO)
        startActivity(intent)
        finish()
    }

    override fun onClickedDetailButtonWithAncientVO(ancientVO: AncientVO) {
        val intent = ActivityDetail.newIntent(this)
        intent.putExtra(ActivityDetail.EXTRA_EVENT_ID_ANCIENT,ancientVO)
        startActivity(intent)
        finish()
    }

    override fun showPreview(uploadedPhotoVO: UploadedPhotoVO) {
        Glide.with(applicationContext)
            .load(uploadedPhotoVO.url)
            .into(img_image_preview)

        mPresenter.getFullInfoItem(uploadedPhotoVO.item_id!!)
        mPresenter.getUploadedUser(uploadedPhotoVO.uploader_id!!)

    }

    companion object {
        const val EXTRA_EVENT_ID = "Extra_to_extra"
        fun newIntent(context: Context): Intent {
            return Intent(context, PreviewActivity::class.java).apply {
            }
        }
    }

    lateinit var mPresenter : PreviewPresenter
    private lateinit var relatedRecyclerAdapter : RelatedViewsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        mPresenter = ViewModelProviders.of(this).get(PreviewPresenter::class.java)
        mPresenter.initPresenter(this)
        mPresenter.onCheckedLanguage()

        mPresenter.handleIntent(intent)
        img_back.setOnClickListener{
            mPresenter.onClickedBackButton()
        }

        btn_details.setOnClickListener {
            mPresenter.onClickedDetailsButton()
        }
        setUpRecyclerView()

        img_image_preview.setOnClickListener {
            mPresenter.onClickedImageView()
        }

    }

    private fun setUpRecyclerView() {

        rv_related_views.recycledViewPool.setMaxRecycledViews(0,0)
        relatedRecyclerAdapter = RelatedViewsRecyclerAdapter {
            mPresenter.onClickedImageItem(it)
        }
        rv_related_views.adapter = relatedRecyclerAdapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_related_views.layoutManager = layoutManager


    }

}