package com.itachi.explore.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.custom_toast.*
import kotlinx.android.synthetic.main.custom_toast.view.*
import kotlinx.android.synthetic.main.dialog_force_update.*
import kotlinx.android.synthetic.main.dialog_loading.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.itachi.explore.R
import com.itachi.explore.utils.PERMISSION_STORAGE
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

        val lp = WindowManager.LayoutParams()

        lp.copyFrom(alertDialog!!.window!!.attributes)
        lp.width = 250
        lp.height = 200
        alertDialog!!.window!!.attributes = lp
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
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e : NoSuchAlgorithmException) {

        }
    }

    fun showSavableDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_saveable,null)
        dialogBuilder.setView(view)
        alertDialog = dialogBuilder.create()
        alertDialog?.let {
            it.window?.attributes?.windowAnimations = R.style.DialogChosing
            it.show()
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

    fun checkPermission(context: Context, requestCode : Int, onSuccess : (Context)->Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                "android.permission.READ_EXTERNAL_STORAGE"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                PERMISSION_STORAGE,
                requestCode
            )
        } else {
            onSuccess(context)
        }
    }

}