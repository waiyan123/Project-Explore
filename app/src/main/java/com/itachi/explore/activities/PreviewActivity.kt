package com.itachi.explore.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.itachi.core.domain.models.*
import com.itachi.explore.R
import com.itachi.explore.adapters.recycler.RelatedViewsRecyclerAdapter
import com.itachi.explore.mvvm.viewmodel.PreviewViewModel
import com.itachi.explore.utils.ANCIENT_TYPE
import com.itachi.explore.utils.PAGODA_TYPE
import com.itachi.explore.utils.VIEW_TYPE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_preview.*
import kotlinx.android.synthetic.main.activity_preview.img_back
import kotlinx.android.synthetic.main.bottom_sheet_views.*
import kotlinx.android.synthetic.main.dialog_zoomage_view.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit

@AndroidEntryPoint
class PreviewActivity : BaseActivity() {

    private fun showZoomageDialog(url: String) {
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

    private fun navigateToGoogleMap(geoPoints: String) {
        val gmmIntentUri = Uri.parse("google.navigation:q=$geoPoints")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.resolveActivity(packageManager)?.let {
            startActivity(mapIntent)
        }
    }

    private fun geoPointsAvailable(geoPoints: String) {
        if(geoPoints != "" && geoPoints != "0.0,0.0") {
            tv_google_map.visibility = View.VISIBLE
            tv_google_map.setOnClickListener {
                navigateToGoogleMap(geoPoints)
            }
        }
        else tv_google_map.visibility = View.GONE
    }

    private fun checkLanguage(lang: String) {
        when (lang) {
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

    private fun showUploader(userVO: UserVO) {

        var name = buildString {
            append(tv_uploader.text)
            append(" ")
            append(userVO.name)
        }
        if (!MDetect.isUnicode()) {
            name = Rabbit.uni2zg(name)
        }

        tv_uploader.text = name
        tv_uploader.setOnClickListener {
            val intent = UserProfileActivity.newIntent(this)
            intent.putExtra(UserProfileActivity.EXTRA_EVENT_ID_USER_PROFILE, userVO)
            startActivity(intent)
        }
    }

    private fun showFullInfo(itemVO: ItemVO) {
        var mTitle = itemVO.title

        if (!MDetect.isUnicode()) {
            mTitle = Rabbit.uni2zg(mTitle)
        }
        tv_title.text = mTitle
        itemVO.photos.let {
            relatedRecyclerAdapter.setNewData(it)
        }
        btn_details.setOnClickListener {
            when (itemVO.item_type) {
                PAGODA_TYPE -> {
                    onClickedDetailButtonWithPagodaVO(itemVO.toPagodaVO())
                }
                VIEW_TYPE -> {
                    onClickedDetailButtonWithViewVO(itemVO.toViewVO())
                }
                ANCIENT_TYPE -> {
                    onClickedDetailButtonWithAncientVO(itemVO.toAncientVO())
                }
            }
        }
    }

    private fun onClickedDetailButtonWithPagodaVO(pagodaVO: PagodaVO) {
        val intent = ActivityDetail.newIntent(this)
        intent.putExtra(ActivityDetail.EXTRA_EVENT_ID_PAGODA, pagodaVO)
        startActivity(intent)
        finish()
    }

    private fun onClickedDetailButtonWithViewVO(viewVO: ViewVO) {
        val intent = ActivityDetail.newIntent(this)
        intent.putExtra(ActivityDetail.EXTRA_EVENT_ID_VIEW, viewVO)
        startActivity(intent)
        finish()
    }

    private fun onClickedDetailButtonWithAncientVO(ancientVO: AncientVO) {
        val intent = ActivityDetail.newIntent(this)
        intent.putExtra(ActivityDetail.EXTRA_EVENT_ID_ANCIENT, ancientVO)
        startActivity(intent)
        finish()
    }

    private fun showPreview(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(img_image_preview)
        img_image_preview.setOnClickListener {
            showZoomageDialog(url)
        }
    }

    companion object {
        const val EXTRA_EVENT_ID = "Extra_to_extra"
        fun newIntent(context: Context): Intent {
            return Intent(context, PreviewActivity::class.java).apply {
            }
        }
    }

    private val mViewModel: PreviewViewModel by viewModels()
    private lateinit var relatedRecyclerAdapter: RelatedViewsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        val uploadedPhotoVO = intent.getSerializableExtra(EXTRA_EVENT_ID) as UploadedPhotoVO?
        uploadedPhotoVO?.let {
            mViewModel.handleIntent(uploadedPhotoVO)
        }
        mViewModel.language.observe(this) {
            checkLanguage(it)
        }
        mViewModel.mItemVO.observe(this) {
            showFullInfo(it)
        }
        mViewModel.uploadedPhotoVOLiveData.observe(this) {
            showPreview(it.url)
            geoPointsAvailable(it.geo_points)
        }
        mViewModel.uploaderVO.observe(this) {
            showUploader(it)
        }
        mViewModel.errorMessageLiveData.observe(this){
            showToast(it)
        }

        img_back.setOnClickListener {
            finish()
        }

        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {

        rv_related_views.recycledViewPool.setMaxRecycledViews(0, 0)
        relatedRecyclerAdapter = RelatedViewsRecyclerAdapter {
            showPreview(it.url)
            geoPointsAvailable(it.geo_points)
        }
        rv_related_views.adapter = relatedRecyclerAdapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_related_views.layoutManager = layoutManager

    }

}