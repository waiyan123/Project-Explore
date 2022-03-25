package com.itachi.explore.activities

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.custom_toast.*
import kotlinx.android.synthetic.main.custom_toast.view.*
import kotlinx.android.synthetic.main.dialog_force_update.*
import kotlinx.android.synthetic.main.dialog_loading.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Base64
import android.util.Log
import com.itachi.explore.R
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


abstract class BaseActivity : AppCompatActivity(){

    var alertDialog : AlertDialog? = null

    init {

    }

    fun showToast(msg : String) {
        val layout = layoutInflater.inflate(R.layout.custom_toast,linearLayout)
        val myToast = Toast(this)
        myToast.duration = Toast.LENGTH_SHORT
        myToast.setGravity(Gravity.CENTER,0,0)
        myToast.view = layout
        layout.custom_toast_message.text = msg
        myToast.show()
    }

    @SuppressLint("InflateParams")
    protected fun showLoading() {
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_loading,null)
        dialogBuilder.setView(view)
        alertDialog = dialogBuilder.create()
        alertDialog!!.window!!.attributes.windowAnimations = R.style.DialogLoading
        alertDialog!!.setCancelable(false)
        alertDialog!!.show()
        alertDialog!!.lottie_view.setAnimation("circles_animation.json")
        alertDialog!!.lottie_view.playAnimation()
        alertDialog!!.lottie_view.loop(true)

    }

    protected fun hideLoading() {
        if(alertDialog!=null && alertDialog!!.isShowing) alertDialog!!.dismiss()
    }

    protected fun getKeyHashForFacebook() {
        try {
            val info = packageManager.getPackageInfo(
                "com.itachi.explore",
                PackageManager.GET_SIGNATURES
            )
            for(signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("test---", Base64.encodeToString(md.digest(),
                    Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e : NoSuchAlgorithmException) {

        }
    }

    fun setUpForceUpdateDialog() {
        val dialogBuilder = MaterialAlertDialogBuilder(this,R.style.MyRounded_MaterialComponents_MaterialAlertDialog)
        val view = layoutInflater.inflate(R.layout.dialog_force_update, null)
        dialogBuilder.setView(view)
        alertDialog = dialogBuilder.create()
        alertDialog!!.window!!.attributes.windowAnimations = R.style.DialogChosing
        alertDialog!!.setCancelable(false)
    }

    fun showForceUpdateDialog() {

        if(!alertDialog!!.isShowing){
            alertDialog!!.show()
        }

        val lp = WindowManager.LayoutParams()

        lp.copyFrom(alertDialog!!.window!!.attributes)
        lp.width = 900
        lp.height = 800
        alertDialog!!.window!!.attributes = lp
        alertDialog!!.tv_update.setOnClickListener {
            alertDialog!!.dismiss()
            navigateToPlayStore()
        }
    }

    fun navigateToPlayStore(){
        val appPackageName = packageName // getPackageName() from Context or Activity object
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }

    }

}