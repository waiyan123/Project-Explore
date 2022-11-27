package com.itachi.explore.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.itachi.explore.R
import com.itachi.explore.adapters.recycler.PagodasRecyclerAdapter
import com.itachi.explore.mvvm.viewmodel.MyViewModelProviderFactory
import com.itachi.explore.mvvm.viewmodel.PagodaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_pagodas.*
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class PagodasActivity : BaseActivity(),View.OnClickListener{

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.img_back -> {
                finish()
            }
        }
    }

    companion object {
//        const val EXTRA_EVENT_ID = "Extra_to_extra"
        fun newIntent(context: Context): Intent {
            return Intent(context, PagodasActivity::class.java).apply {
            }
        }
    }
    private var timer : Timer? = null
    private lateinit var rvAdapter : PagodasRecyclerAdapter

    private val mViewModel : PagodaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagodas)

        showAnim(shimmer_banner,true)
        showAnim(shimmer_showall,true)

        mViewModel.checkLanguage().observe(this, androidx.lifecycle.Observer { lang->
            when(lang) {
                "en" -> {
                    tv_pagoda_title.text = getString(R.string.pagoda_en)
                }

                "mm_unicode" -> {
                    tv_pagoda_title.text = getString(R.string.pagoda_mm_unicode)
                }

                "mm_zawgyi" -> {
                    tv_pagoda_title.text = getString(R.string.pagoda_mm_zawgyi)
                }
            }
        })

        setSupportActionBar(toolbar)

        setUpPagodaRecyclerView()

        img_back.setOnClickListener(this)

        mViewModel.bannerPhotos.observe(this){photoList->
            showAnim(shimmer_banner,false)
            app_bar_layout.visibility = View.VISIBLE
            setUpBanner(photoList)
        }

        mViewModel.errorGettingBanner.observe(this) {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }

        mViewModel.pagodaListLiveData.observe(this, androidx.lifecycle.Observer {
            tv_no_items.visibility = View.GONE
            rv_pagodas.visibility = View.VISIBLE

            showAnim(shimmer_showall,false)

            rvAdapter.setNewData(it)
        })

        mViewModel.errorGettingPagodaList.observe(this, androidx.lifecycle.Observer {
            tv_no_items.visibility = View.VISIBLE
            tv_no_items.text = it
            rv_pagodas.visibility = View.GONE

            showAnim(shimmer_banner,false)
            showAnim(shimmer_showall,false)
        })
        mViewModel.pagodaItemOb.observe(this){pagodaVO->
            val intent = ActivityDetail.newIntent(this)
            intent.putExtra(ActivityDetail.EXTRA_EVENT_ID_PAGODA,pagodaVO)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getPagodaList()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }


    private fun setUpBanner(photoList: ArrayList<String>) {

        timer = Timer()

        img_switch_banner.setFactory {
            val imageView = ImageView(applicationContext)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView
        }

        val fadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        val fadeOut = AnimationUtils.loadAnimation(this,R.anim.fade_out)
        img_switch_banner.inAnimation = fadeIn
        img_switch_banner.outAnimation = fadeOut

        startTimer(photoList)
    }

    private fun setUpPagodaRecyclerView () {
        rvAdapter = PagodasRecyclerAdapter{
            mViewModel.clickPagodaItem(it)
        }

        with(rv_pagodas) {
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

    /* this is a testing commit */

    private fun startTimer(photoList : ArrayList<String>) {
        var position = 0

        timer?.scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                runOnUiThread {
                    Glide.with(applicationContext)
                        .load(photoList[position])
                        .into(img_switch_banner.currentView as ImageView)
                    position++
                    if(position==photoList.size) position = 0
                }
            }

        },0, 2500)
    }

    private fun showAnim(shimmer: ShimmerFrameLayout, anim: Boolean) {
        if (anim) {
            shimmer.visibility = View.VISIBLE
            shimmer.startShimmerAnimation()
        } else {
            shimmer.visibility = View.GONE
            shimmer.stopShimmerAnimation()
        }
    }
}