package com.itachi.explore.mvvm.viewmodel

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.itachi.explore.activities.LoginActivity
import com.itachi.explore.framework.Interactors
import org.koin.core.KoinComponent
import org.koin.core.inject


class LoginViewModel(interactors: Interactors) : AppViewmodel(interactors),KoinComponent {

    private val oneTapClient : SignInClient by inject()
    private val REQ_ONE_TAP = 1

    init {

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
//            REQ_ONE_TAP -> {
//                try {
//                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
//                    val idToken = credential.googleIdToken
//                    val username = credential.id
//                    val password = credential.password
//                    when {
//                        idToken != null -> {
//                            // Got an ID token from Google. Use it to authenticate
//                            // with your backend.
//                            Log.d("test---", "Got ID token.")
//                        }
//                        password != null -> {
//                            // Got a saved username and password. Use them to authenticate
//                            // with your backend.
//                            Log.d("test---", "Got password.")
//                        }
//                        else -> {
//                            // Shouldn't happen.
//                            Log.d("test---", "No ID token or password!")
//                        }
//                    }
//                } catch (e: ApiException) {
//                    Log.d("test---", "one tap error "+e.localizedMessage)
//                }
//            }

            REQ_ONE_TAP -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }

        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            Log.d("test---","Signed in successfully")

            // Signed in successfully, show authenticated UI.
//            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("test---", "signInResult:failed code=" + e.localizedMessage)
//            updateUI(null)
        }
    }

        fun signInWithGoogle(activity : LoginActivity) {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(activity,gso)
            val account = GoogleSignIn.getLastSignedInAccount(activity)
            activity.startActivityForResult(mGoogleSignInClient.signInIntent,REQ_ONE_TAP)

//            val signInRequest = BeginSignInRequest.builder()
//                .setPasswordRequestOptions(
//                    BeginSignInRequest.PasswordRequestOptions.builder()
//                        .setSupported(true)
//                        .build()
//                )
//                .setGoogleIdTokenRequestOptions(
//                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId("1093689593287-fvf61gg16jcqu68kvvt5va504lcmgq4q.apps.googleusercontent.com")
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build()
//                )
//                // Automatically sign in when exactly one credential is retrieved.
//                .setAutoSelectEnabled(true)
//                .build();
//
//            oneTapClient.beginSignIn(signInRequest)
//                .addOnSuccessListener {result->
//                    try {
//                        startIntentSenderForResult(activity,
//                            result.pendingIntent.intentSender, REQ_ONE_TAP,
//                            null, 0, 0, 0, null)
//                    } catch (e: IntentSender.SendIntentException) {
//                        Log.d("test---", "Couldn't start One Tap UI: ${e.localizedMessage}")
//                    }
//                }
//                .addOnFailureListener { e ->
//                    // No saved credentials found. Launch the One Tap sign-up flow, or
//                    // do nothing and continue presenting the signed-out UI.
//                    Log.d("test---", "Failure error"+e.localizedMessage)
//                }
        }
    }
