package com.itachi.explore.mvvm.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.interactors.GetLanguageUseCase
import com.itachi.core.interactors.SetLanguageUseCase
import com.itachi.explore.utils.DONT_SHOW_INTRO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val setLanguageUseCase: SetLanguageUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val sharedPreferences : SharedPreferences
) : ViewModel(){

    private val _langStateFlow = MutableStateFlow<String>("")
    val langStateFlow : StateFlow<String> = _langStateFlow

    init {
        getLanguage()
    }

    private fun getLanguage() {
        viewModelScope.launch {
            getLanguageUseCase()
                .collectLatest {
                    _langStateFlow.value = it
                }
        }
    }
    fun onClickedContinue(boo : Boolean) {
        sharedPreferences.edit().putBoolean(DONT_SHOW_INTRO,boo).apply()
    }

    fun setLanguage(lang : String) {
        setLanguageUseCase(lang)
        getLanguage()
    }

}