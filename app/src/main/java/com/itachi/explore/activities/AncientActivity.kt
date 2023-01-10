package com.itachi.explore.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.itachi.explore.adapters.recycler.AncientRecyclerAdapter
import kotlinx.android.synthetic.main.activity_ancient.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.itachi.core.domain.AncientVO
import com.itachi.explore.R
import com.itachi.explore.mvvm.viewmodel.AncientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AncientActivity : BaseActivity(), View.OnClickListener {

    private val mViewModel: AncientViewModel by viewModels()
    private lateinit var rvAdapter: AncientRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ancient)

        img_back.setOnClickListener(this)

        mViewModel.checkLanguage().observe(this, Observer { lang ->
            when (lang) {
                "en" -> {
                    tv_ancient_title.text = getString(R.string.ancients_en)
                }

                "mm_unicode" -> {
                    tv_ancient_title.text = getString(R.string.ancients_mm_unicode)
                }

                "mm_zawgyi" -> {
                    tv_ancient_title.text = getString(R.string.ancients_mm_zawgyi)
                }
            } })
        mViewModel.errorStr.observe(this, Observer { errorMessage->
            showToast(errorMessage)
        })

        setUpPagodaRecyclerView()


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.img_back -> {
                finish()
            }
        }
    }


    private fun navigateToAncientDetail(ancientVO: AncientVO) {
        val intent = ActivityDetail.newIntent(this)
        intent.putExtra(ActivityDetail.EXTRA_EVENT_ID_ANCIENT, ancientVO)
        startActivity(intent)
    }

    companion object {
        //        const val EXTRA_EVENT_ID = "Extra_to_extra"
        fun newIntent(context: Context): Intent {
            return Intent(context, AncientActivity::class.java).apply {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getAncients().observe(this, Observer {ancientList->
            rvAdapter.setNewData(ancientList)
        })
        mViewModel.getAncientBg().observe(this, Observer {bgPic->
            Glide.with(this)
                .asBitmap()
                .load(bgPic)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val dr = BitmapDrawable(resource)
                        layout_ancient.background = dr
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // this is called when imageView is cleared on lifecycle call or for
                        // some other reason.
                        // if you are referencing the bitmap somewhere else too other than this imageView
                        // clear it here as you can no longer have the bitmap
                    }
                })
        })
    }

    private fun setUpPagodaRecyclerView() {
        rvAdapter = AncientRecyclerAdapter {ancientVO->
            navigateToAncientDetail(ancientVO)
        }

        with(rv_ancient) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                applicationContext,
                LinearLayoutManager.VERTICAL,
                false
            )
            (layoutManager as LinearLayoutManager).scrollToPosition(0) // this line solved the problem that recyclerview auto scroll up
            adapter = rvAdapter

        }
    }
}