package com.itachi.explore.mvvm.model

import com.itachi.explore.utils.ABOUT_SHWEBO
import com.itachi.explore.utils.INTRO
import com.itachi.explore.utils.UTIL_TEXTS

class ShweboModelImpl : BaseModel(),ShweboModel{

    override fun getIntro(
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit) {

        firestoreRef.collection(UTIL_TEXTS).document(ABOUT_SHWEBO)
            .get()
            .addOnSuccessListener {
                onSuccess(it.get(INTRO) as String)
            }
            .addOnFailureListener{
                onFailure(it.localizedMessage)
            }
    }

}