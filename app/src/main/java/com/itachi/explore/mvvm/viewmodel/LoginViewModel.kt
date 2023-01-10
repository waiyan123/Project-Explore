package com.itachi.explore.mvvm.viewmodel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UserVO
import com.itachi.core.interactors.AddUserUseCase
import com.itachi.core.interactors.GetLanguageUseCase
import com.itachi.explore.R
import com.itachi.explore.activities.LoginActivity
import com.itachi.core.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val auth: FirebaseAuth,
    private val getLanguageUseCase: GetLanguageUseCase
) : ViewModel(), KoinComponent {

    val loginSuccess = MutableLiveData<Boolean>()
    val isAlreadyLogin = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val displayLoading = MutableLiveData<Boolean>()
    val language = MutableLiveData<String>()

    private val REQ_ONE_TAP = 1

    init {
        isAlreadyLogin.postValue(auth.currentUser != null)
        viewModelScope.launch {
            getLanguageUseCase()
                .onEach{
                language.postValue(it)
            }
                .collect()
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

            loginToFirebase(account)

        } catch (e: ApiException) {

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

    private fun loginToFirebase(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                Log.d("test---","addOnComplete")
                val userId = auth.currentUser!!.uid
                viewModelScope.launch {
                    addUserUseCase(
                        UserVO(
                            "",
                            "" + userId,
                            "",
                            "",
                            account.email!!,
                            account.displayName!!,
                            PhotoVO("0", account.photoUrl.toString(), ""),
                            PhotoVO("0", "", ""),
                            "",
                            false
                        )
                    )
                        .onEach{
                            when (it) {
                                is Resource.Success -> {

                                    it.data?.let {
                                        loginSuccess.postValue(true)
                                        displayLoading.postValue(false)
                                    }
                                }
                                is Resource.Error -> {
                                    it.message?.let { errorMsg ->
                                        errorMessage.postValue(errorMsg)
                                        displayLoading.postValue(false)
                                    }
                                }
                                is Resource.Loading -> {
                                    displayLoading.postValue(true)
                                }
                            }
                        }
                        .collect()
                }
            }
    }
}
