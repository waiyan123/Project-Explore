package com.itachi.explore.framework

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.itachi.core.data.network.AuthFirebaseDataSource
import com.itachi.explore.R
import com.itachi.explore.activities.LoginActivity
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.persistence.entities.PhotoEntity
import com.itachi.explore.persistence.entities.UserEntity
import com.itachi.explore.utils.REQ_GOOGLE_SIGN_IN
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class AuthFirebaseDataSourceImpl(val firebaseAuthRef : FirebaseAuth,val database : MyDatabase) : AuthFirebaseDataSource,KoinComponent{

    val context : Context by inject()

    override suspend fun isAlreadyLogin() : Boolean{
        return firebaseAuthRef.currentUser!=null
    }

    override suspend fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.firebase_client_id))
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
        val activity = context as Activity
        activity.startActivityForResult(mGoogleSignInClient.signInIntent, REQ_GOOGLE_SIGN_IN)

    }

    override suspend fun signOut() {
        firebaseAuthRef.signOut()
    }

    private fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_GOOGLE_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            Log.d("test---", "Signed in successfully ${account.id}")
            Log.d("test---","id token "+account.idToken)
            loginToFirebase(account)

        } catch (e: ApiException) {
            Log.d("test---", "signInResult:failed code=" + e.localizedMessage)
        }
    }

    private fun loginToFirebase(account : GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuthRef.signInWithCredential(credential)
            .addOnCompleteListener {
                Log.d("test---","Firebase login successful")
                database.userDao().insertUser(
                    UserEntity("","$account.id","",
                    "",account.email,account.displayName, PhotoEntity("",account.photoUrl.toString(),"")
                )
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
//                        loginSuccess.postValue(true)

                    }
            }
    }
}