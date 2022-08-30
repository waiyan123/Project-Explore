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
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UserVO
import com.itachi.explore.R
import com.itachi.explore.activities.LoginActivity
import com.itachi.explore.framework.Interactors
import com.itachi.explore.mvvm.model.LanguageModelImpl
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.persistence.entities.PhotoEntity
import com.itachi.explore.persistence.entities.UserEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject


class LoginViewModel(interactors: Interactors) : AppViewmodel(interactors), KoinComponent {

    private val firebaseAuthRef = FirebaseAuth.getInstance()
    private val languageModel : LanguageModelImpl by inject()
    val loginSuccess = MutableLiveData<Boolean>()
    val isAlreadyLogin = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val displayLoading = MutableLiveData<Boolean>()
    val language = MutableLiveData<String>()

    private val REQ_ONE_TAP = 1

    init {
        isAlreadyLogin.postValue(firebaseAuthRef.currentUser!=null)
        languageModel.getLanguage {
            language.postValue(it)
        }
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
        displayLoading.postValue(true)
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            Log.d("test---", "Signed in successfully ${account.id}")
            Log.d("test---","id token "+account.idToken)
            loginToFirebase(account)

        } catch (e: ApiException) {
            Log.d("test---", "signInResult:failed code=" + e.localizedMessage)
        }
    }

    fun signInWithGoogle(activity: LoginActivity) {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.firebase_client_id))
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
        activity.startActivityForResult(mGoogleSignInClient.signInIntent, REQ_ONE_TAP)

    }

    private fun loginToFirebase(account : GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuthRef.signInWithCredential(credential)
            .addOnCompleteListener {
                val userId = firebaseAuthRef.currentUser!!.uid
                GlobalScope.launch {
                    interactors.addUser.toFirebase(UserVO("",""+userId,"",
                        "",account.email!!,account.displayName!!, PhotoVO("",account.photoUrl.toString(),""),
                        PhotoVO("","",""),"",false),
                        {
                            Log.d("test---","add user to firebase")
                            GlobalScope.launch {
                                interactors.addUser.toRoom(it,
                                    {
                                        Log.d("test---","add user to room")
                                        loginSuccess.postValue(true)
                                    },
                                    {errorMsgFromRoom->
                                        errorMessage.postValue(errorMsgFromRoom)
                                    })
                            }
                        },
                        {errorMsgFromFirebase->
                            errorMessage.postValue(errorMsgFromFirebase)
                        })
            }
            }
            .addOnFailureListener { exception->
                errorMessage.postValue(exception.localizedMessage)
            }
    }

}
