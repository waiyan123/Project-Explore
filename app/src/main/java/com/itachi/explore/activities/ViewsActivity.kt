package com.itachi.explore.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.itachi.core.domain.models.UploadedPhotoVO
import com.itachi.explore.R
import com.itachi.explore.adapters.recycler.ViewsRecyclerAdapter
import com.itachi.explore.mvvm.viewmodel.ViewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_views.*
import kotlinx.android.synthetic.main.activity_views.img_back

@AndroidEntryPoint
class ViewsActivity : BaseActivity(),View.OnClickListener{

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.img_back -> {
                finish()
            }
        }
    }

    private fun navigateViewPhoto(uploadedPhotoVO: UploadedPhotoVO) {
        val intent = PreviewActivity.newIntent(this)
        intent.putExtra(PreviewActivity.EXTRA_EVENT_ID,uploadedPhotoVO)
        startActivity(intent)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ViewsActivity::class.java).apply {
            }
        }
    }


    private val mViewModel : ViewsViewModel by viewModels()
        private lateinit var mAdapter : ViewsRecyclerAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_views)

            setUpRecyclerView()

            img_back.setOnClickListener(this)
        }

        override fun onResume() {
            super.onResume()
            mViewModel.showPhotoList().observe(this, Observer { viewPhotoList->
                mAdapter.setNewData(viewPhotoList)
            })
            mViewModel.checkLanguage().observe(this, Observer { lang->
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
            })
            mViewModel.getErrorMessage().observe(this, Observer {
                showToast(it)
                mAdapter.clearAllData()
            })

        }

        private fun setUpRecyclerView() {

            rv_views.recycledViewPool.setMaxRecycledViews(0,0)
            mAdapter = ViewsRecyclerAdapter {photoVO->
                navigateViewPhoto(photoVO)
            }
            rv_views.adapter = mAdapter
            val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            layoutManager.scrollToPositionWithOffset(0,0)
            layoutManager.reverseLayout = false
            layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            rv_views.layoutManager = layoutManager

        }
    }
