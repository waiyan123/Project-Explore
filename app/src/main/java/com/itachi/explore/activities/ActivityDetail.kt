package com.itachi.explore.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import com.itachi.core.domain.models.*
import com.itachi.explore.R
import com.itachi.explore.mvvm.viewmodel.DetailsViewModel
import com.itachi.explore.third_parties.PagodaSliderAdapter
import com.itachi.explore.utils.ANCIENT_TYPE
import com.itachi.explore.utils.PAGODA_TYPE
import com.itachi.explore.utils.VIEW_TYPE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit


@AndroidEntryPoint
class ActivityDetail : BaseActivity(), View.OnClickListener {

    private fun changeLanguage(lang: String) {
        when (lang) {
            "en" -> {
                tv_key_created_date.text = getString(R.string.created_date_en)
                tv_key_festival_date.text = getString(R.string.festival_date_en)
            }

            "mm_unicode" -> {
                tv_key_created_date.text = getString(R.string.created_date_mm_unicode)
                tv_key_festival_date.text = getString(R.string.festival_date_mm_unicode)
            }

            "mm_zawgyi" -> {
                tv_key_created_date.text = getString(R.string.created_date_mm_zawgyi)
                tv_key_festival_date.text = getString(R.string.festival_date_mm_zawgyi)
            }
        }
    }

    private fun editPagoda(pagodaVO: PagodaVO?) {
        val intent = FormActivity.newIntent(this)
        intent.putExtra(FormActivity.EXTRA_EVENT_ID_PAGODA, pagodaVO)
        startActivity(intent)
    }

    private fun editView(viewVO: ViewVO?) {
        val intent = FormActivity.newIntent(this)
        intent.putExtra(FormActivity.EXTRA_EVENT_ID_VIEW, viewVO)
        startActivity(intent)
    }

    private fun editAncient(ancientVO: AncientVO?) {
        val intent = FormActivity.newIntent(this)
        intent.putExtra(FormActivity.EXTRA_EVENT_ID_ANCIENT, ancientVO)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun showAncientDetailViews(detail: AncientVO) {
        var name = detail.title
        var createdDate = detail.created_date
        var festivalDate = detail.festival_date
        var about = detail.about
        if (!MDetect.isUnicode()) {
            name = Rabbit.uni2zg(name)
            createdDate = Rabbit.uni2zg(createdDate)
            festivalDate = Rabbit.uni2zg(festivalDate)
            about = Rabbit.uni2zg(about)
        }
        tv_name.text = name
        tv_value_created_date.text = createdDate
        tv_value_festival_date.text = festivalDate
        tv_value_about.text = "\t\t\t" + about
    }

    private fun successfullyDeletedItem(message: String) {
        showToast(message)
        finish()
    }

    private fun showSettingIcon(show: Boolean) {
        if (show) {
            img_setting.visibility = View.VISIBLE
        } else {
            img_setting.visibility = View.GONE
        }
    }

    private fun showError(error: String) {
        showError(error)
    }

    @SuppressLint("SetTextI18n")
    private fun showPagodaDetailViews(detail: PagodaVO) {
        var name = detail.title
        var createdDate = detail.created_date
        var festivalDate = detail.festival_date
        var about = detail.about
        if (!MDetect.isUnicode()) {
            name = Rabbit.uni2zg(name)
            createdDate = Rabbit.uni2zg(createdDate)
            festivalDate = Rabbit.uni2zg(festivalDate)
            about = Rabbit.uni2zg(about)
        }
        tv_name.text = name
        tv_value_created_date.text = createdDate
        tv_value_festival_date.text = festivalDate
        tv_value_about.text = "\t\t\t" + about

    }

    @SuppressLint("SetTextI18n")
    private fun showViewDetailViews(detail: ViewVO) {
        var name = detail.title
        var createdDate = detail.created_date
        var festivalDate = detail.festival_date
        var about = detail.about
        if (!MDetect.isUnicode()) {
            name = Rabbit.uni2zg(name)
            createdDate = Rabbit.uni2zg(createdDate)
            festivalDate = Rabbit.uni2zg(festivalDate)
            about = Rabbit.uni2zg(about)
        }
        tv_name.text = name
        tv_value_created_date.text = createdDate
        tv_value_festival_date.text = festivalDate
        tv_value_about.text = "\t\t\t" + about
    }

    private lateinit var sliderAdapter: PagodaSliderAdapter

    companion object {
        const val EXTRA_EVENT_ID_PAGODA = "pagoda"
        const val EXTRA_EVENT_ID_VIEW = "view"
        const val EXTRA_EVENT_ID_ANCIENT = "ancient"
//        const val EXTRA_EVENT_ID_FOOD = "food"
//        const val EXTRA_EVENT_ID_ACCESSORIES = "accessories"
//        const val EXTRA_EVENT_ID_SUPPORTS = "supports"

        fun newIntent(context: Context): Intent {
            return Intent(context, ActivityDetail::class.java).apply {
            }
        }
    }

    private val mViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val pagodaItem = intent.getSerializableExtra(EXTRA_EVENT_ID_PAGODA) as PagodaVO?
        val viewItem = intent.getSerializableExtra(EXTRA_EVENT_ID_VIEW) as ViewVO?
        val ancientItem = intent.getSerializableExtra(EXTRA_EVENT_ID_ANCIENT) as AncientVO?

        val item = when {
            pagodaItem != null -> pagodaItem
            viewItem != null -> viewItem
            ancientItem != null -> ancientItem
            else -> return
        }
        setUpSlider(item.photos)
        mViewModel.setupItemVO(item)

        img_setting.setOnClickListener(this)
        img_back.setOnClickListener(this)

        mViewModel.mItemVO.observe(this) { itemVO ->
            when (itemVO.item_type) {
                PAGODA_TYPE -> showPagodaDetailViews(itemVO.toPagodaVO())
                VIEW_TYPE -> showViewDetailViews(itemVO.toViewVO())
                ANCIENT_TYPE -> showAncientDetailViews(itemVO.toAncientVO())
            }
        }
        mViewModel.isUploader.observe(this) {
            showSettingIcon(it)
        }
        mViewModel.successMsg.observe(this) {
            successfullyDeletedItem(it)
        }
        mViewModel.errorMsg.observe(this) {
            showError(it)
        }
        mViewModel.language.observe(this) {
            changeLanguage(it)
        }

    }

    override fun onResume() {
        super.onResume()
        mViewModel.refreshDetails()
    }


    private fun setUpSlider(photoList: List<PhotoVO>) {
        banner_slider.visibility = View.VISIBLE
        sliderAdapter = PagodaSliderAdapter(photoList)
        banner_slider.setAdapter(sliderAdapter)
        banner_slider.setInterval(2000)
        banner_slider.setLoopSlides(true)
        banner_slider.setAnimateIndicators(true)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.img_back -> {
                finish()
            }

            R.id.img_setting -> {
                showPopUpMenu()
            }
        }
    }

    private fun showPopUpMenu() {
        val popup = PopupMenu(this, img_setting)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.setting_menu, popup.menu)
        popup.setOnMenuItemClickListener {menu->
            when (menu.itemId) {
                R.id.menu_edit -> {
                    Log.d("test---", "menu edit")
                    mViewModel.itemVO?.let {
                        when (it.item_type) {
                            PAGODA_TYPE -> {
                                editPagoda(it.toPagodaVO())
                            }
                            VIEW_TYPE -> {
                                editView(it.toViewVO())
                            }
                            ANCIENT_TYPE -> {
                                editAncient(it.toAncientVO())
                            }
                        }
                    }
                    true
                }
                R.id.menu_delete -> {
                    Log.d("test---", "menu delete")
                    showLoading()
                    mViewModel.deleteItem()
                    true
                }
                else -> {
                    true
                }
            }
        }
        popup.show()
    }

}