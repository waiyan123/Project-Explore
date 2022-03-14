package com.itachi.explore.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itachi.explore.framework.Interactors

object MyViewModelProviderFactory : ViewModelProvider.Factory {

    lateinit var dependencies: Interactors

    fun inject (interactors: Interactors) {
        dependencies = interactors
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(AppViewmodel::class.java.isAssignableFrom(modelClass)) {
            return modelClass.getConstructor(Interactors::class.java)
                .newInstance(
                    dependencies
                )
        } else {
            throw IllegalStateException("ViewModel Class not found")
        }
    }
}