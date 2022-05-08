package com.itachi.explore.mvvm.viewmodel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.itachi.explore.activities.LoginActivity
import com.itachi.explore.framework.Interactors
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.persistence.entities.PhotoEntity
import com.itachi.explore.persistence.entities.UserEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject


class LoginViewModel(interactors: Interactors) : AppViewmodel(interactors), KoinComponent {

    private val firebaseAuthRef = FirebaseAuth.getInstance()
    private val database : MyDatabase by inject()
    val loginSuccess = MutableLiveData<Boolean>()

    private val REQ_ONE_TAP = 1

    init {

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            REQ_ONE_TAP -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            Log.d("test---", "Signed in successfully")
            loginToFirebase(account)

        } catch (e: ApiException) {
            Log.d("test---", "signInResult:failed code=" + e.localizedMessage)
        }
    }

    fun signInWithGoogle(activity: LoginActivity) {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
//        val account = GoogleSignIn.getLastSignedInAccount(activity)
        activity.startActivityForResult(mGoogleSignInClient.signInIntent, REQ_ONE_TAP)

    }

    private fun loginToFirebase(account : GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuthRef.signInWithCredential(credential)
            .addOnCompleteListener {
                Log.d("test---","Firebase login successful")
                database.userDao().insertUser(UserEntity("","$account.id","",
                "",account.email,account.displayName, PhotoEntity("",account.photoUrl.toString(),"")
                ))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        loginSuccess.postValue(true)

                    }
            }
    }

}
