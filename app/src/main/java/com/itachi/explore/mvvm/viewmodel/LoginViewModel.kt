package com.itachi.explore.mvvm.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.itachi.explore.framework.Interactors


class LoginViewModel(interactors: Interactors) : AppViewmodel(interactors){

    fun loginWithGoogle(context : Context) {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

        val account = GoogleSignIn.getLastSignedInAccount(context)

        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){

    }
}