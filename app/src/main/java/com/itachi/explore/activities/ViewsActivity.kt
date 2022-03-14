package com.itachi.explore.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.explore.R
import com.itachi.explore.adapters.recycler.ViewsRecyclerAdapter
import com.itachi.explore.mvp.presenters.ViewsPresenter
import com.itachi.explore.mvp.views.ViewsView
import kotlinx.android.synthetic.main.activity_views.*
import kotlinx.android.synthetic.main.activity_views.img_back

class ViewsActivity : BaseActivity(),ViewsView,View.OnClickListener{

    override fun checkLanguage(lang: String) {
        when(lang) {
            "en" -> {
                tv_views_title.text = getString(R.string.views_en)
            }

            "mm_unicode" -> {
                tv_views_title.text = getString(R.string.views_mm_unicode)
            }

            "mm_zawgyi" -> {
                tv_views_title.text = getString(R.string.views_mm_zawgyi)
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.img_back -> {
                finish()
            }
        }
    }

    override fun navigateViewPhoto(uploadedPhotoVO: UploadedPhotoVO) {
        val intent = PreviewActivity.newIntent(this)
        intent.putExtra(PreviewActivity.EXTRA_EVENT_ID,uploadedPhotoVO)
        startActivity(intent)
    }

    override fun showPhotoViews(uploadedPhotoList: ArrayList<UploadedPhotoVO>) {

        mAdapter.setNewData(uploadedPhotoList)
    }

    override fun showError(error: String) {
        showToast(error)
        mAdapter.clearAllData()
    }

    companion object {
//        const val EXTRA_EVENT_ID = "Extra_to_extra"
        fun newIntent(context: Context): Intent {
            return Intent(context, ViewsActivity::class.java).apply {
            }
        }
    }

    lateinit var mPresenter : ViewsPresenter
    private lateinit var mAdapter : ViewsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_views)

        mPresenter = ViewModelProviders.of(this).get(ViewsPresenter::class.java)
        mPresenter.initPresenter(this)
        mPresenter.checkLanguage()

        setUpRecyclerView()

        img_back.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.showPhotoList()
    }

    private fun setUpRecyclerView() {

        rv_views.recycledViewPool.setMaxRecycledViews(0,0)
        mAdapter = ViewsRecyclerAdapter {
            mPresenter.clickViewItem(it)
        }
        rv_views.adapter = mAdapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.scrollToPositionWithOffset(0,0)
        layoutManager.reverseLayout = false
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        rv_views.layoutManager = layoutManager


    }
}