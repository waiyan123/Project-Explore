package com.itachi.explore.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.R
import com.itachi.explore.mvp.presenters.DetailPresenter
import com.itachi.explore.mvp.views.DetailView
import com.itachi.explore.third_parties.PagodaSliderAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.dialog_post_detail.*
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit

class ActivityDetail : BaseActivity(),DetailView,View.OnClickListener{

    override fun checkLanguage(lang: String) {
        when(lang) {
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

    override fun toEditPagoda(pagodaVO: PagodaVO?) {
        val intent = FormActivity.newIntent(this)
        intent.putExtra(FormActivity.EXTRA_EVENT_ID_PAGODA,pagodaVO)
        startActivity(intent)
        finish()
    }

    override fun toEditView(viewVO: ViewVO?) {
        val intent = FormActivity.newIntent(this)
        intent.putExtra(FormActivity.EXTRA_EVENT_ID_VIEW,viewVO)
        startActivity(intent)
        finish()
    }

    override fun toEditAncient(ancientVO: AncientVO?) {
        val intent = FormActivity.newIntent(this)
        intent.putExtra(FormActivity.EXTRA_EVENT_ID_ANCIENT,ancientVO)
        startActivity(intent)
        finish()
    }

    @SuppressLint("SetTextI18n")
    override fun showAncientDetailViews(detail: AncientVO) {
        var name = detail.title
        var createdDate = detail.created_date
        var festivalDate = detail.festival_date
        var about = detail.about
        if(!MDetect.isUnicode()) {
            name = Rabbit.uni2zg(name)
            createdDate = Rabbit.uni2zg(createdDate)
            festivalDate = Rabbit.uni2zg(festivalDate)
            about = Rabbit.uni2zg(about)
        }
        tv_name.text = name
        tv_value_created_date.text = createdDate
        tv_value_festival_date.text = festivalDate
        tv_value_about.text = "\t\t\t" +about
    }

    override fun successfullyDeletedItem(message: String) {
        showToast(message)
        finish()
    }

    override fun showSettingIcon(show: Boolean) {
        if(show) {
            img_setting.visibility = View.VISIBLE
        }
        else {
            img_setting.visibility = View.GONE
        }
    }

    override fun showError(error: String) {
        showError(error)
    }

    @SuppressLint("SetTextI18n")
    override fun showPagodaDetailViews(detail: PagodaVO) {
        var name = detail.title
        var createdDate = detail.created_date
        var festivalDate = detail.festival_date
        var about = detail.about
        if(!MDetect.isUnicode()) {
            name = Rabbit.uni2zg(name)
            createdDate = Rabbit.uni2zg(createdDate)
            festivalDate = Rabbit.uni2zg(festivalDate)
            about = Rabbit.uni2zg(about)
        }
        tv_name.text = name
        tv_value_created_date.text = createdDate
        tv_value_festival_date.text = festivalDate
        tv_value_about.text = "\t\t\t" +about

    }

    @SuppressLint("SetTextI18n")
    override fun showViewDetailViews(detail: ViewVO) {
        var name = detail.title
        var createdDate = detail.created_date
        var festivalDate = detail.festival_date
        var about = detail.about
        if(!MDetect.isUnicode()) {
            name = Rabbit.uni2zg(name)
            createdDate = Rabbit.uni2zg(createdDate)
            festivalDate = Rabbit.uni2zg(festivalDate)
            about = Rabbit.uni2zg(about)
        }
        tv_name.text = name
        tv_value_created_date.text = createdDate
        tv_value_festival_date.text = festivalDate
        tv_value_about.text = "\t\t\t" +about
    }

    private lateinit var sliderAdapter : PagodaSliderAdapter

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

    lateinit var mPresenter : DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mPresenter = ViewModelProviders.of(this).get(DetailPresenter::class.java)
        mPresenter.initPresenter(this)
        mPresenter.checkLanguage()

        val pagodaItem = intent.getSerializableExtra(EXTRA_EVENT_ID_PAGODA) as PagodaVO?
        val viewItem = intent.getSerializableExtra(EXTRA_EVENT_ID_VIEW) as ViewVO?
        val ancientItem = intent.getSerializableExtra(EXTRA_EVENT_ID_ANCIENT) as AncientVO?

        when {
            pagodaItem!=null -> {
                pagodaItem.photos?.let {
                    setUpSlider(ArrayList(it))
                }
                mPresenter.showPagodaDetails(pagodaItem)
            }
            viewItem!=null -> {
                viewItem.photos?.let {
                    setUpSlider(ArrayList(it))
                }
                mPresenter.showViewDetails(viewItem)
            }
            ancientItem!=null -> {
                ancientItem.photos?.let {
                    setUpSlider(ArrayList(it))
                }
                mPresenter.showAncientDetails(ancientItem)

            }
        }


        img_setting.setOnClickListener(this)
        img_back.setOnClickListener(this)
    }


    private fun setUpSlider(photoList : ArrayList<PhotoVO>) {
        banner_slider.visibility = View.VISIBLE
        sliderAdapter = PagodaSliderAdapter(photoList)
        banner_slider.setAdapter(sliderAdapter)
        banner_slider.setInterval(2000)
        banner_slider.setLoopSlides(true)
        banner_slider.setAnimateIndicators(true)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {

            R.id.img_back -> {
                finish()
            }

            R.id.img_setting -> {
                showSettingDialog()
            }

            R.id.tv_edit -> {
                mPresenter.clickedEdit()
            }

            R.id.tv_delete -> {
                alertDialog!!.dismiss()
                showLoading()
                val lp = WindowManager.LayoutParams()

                lp.copyFrom(alertDialog!!.window!!.attributes)
                lp.width = 350
                lp.height = 300
                alertDialog!!.window!!.attributes = lp
                mPresenter.clickedDelete()
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun showSettingDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_post_detail,null)
        dialogBuilder.setView(view)
        alertDialog = dialogBuilder.create()
        alertDialog!!.window!!.attributes.windowAnimations = R.style.DialogChosing
        alertDialog!!.show()
        alertDialog!!.window!!.setLayout(500, 400)

        alertDialog!!.tv_edit.setOnClickListener(this)
        alertDialog!!.tv_delete.setOnClickListener (this)
    }

}