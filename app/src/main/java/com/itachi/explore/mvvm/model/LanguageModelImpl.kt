package com.itachi.explore.mvvm.model

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itachi.explore.utils.LANGUAGE
import me.myatminsoe.mdetect.MDetect
import org.koin.core.KoinComponent
import org.koin.core.inject

@SuppressLint("StaticFieldLeak")
class LanguageModelImpl : LanguageModel,BaseModel(),KoinComponent{


    private val sharPreferences : SharedPreferences by inject()

    override fun getLanguage(lang: (String) -> Unit) {
        when(sharPreferences.getString(LANGUAGE,"en")) {
            null -> lang("en")
            "en" -> lang("en")
            "mm" -> {
                if(MDetect.isUnicode()){
                    lang("mm_unicode")
                }
                else {
                    lang("mm_zawgyi")
                }
            }
        }

    }

    override fun setLanguage(lang:String) : LiveData<String> {
        val liveData = MutableLiveData<String>()
        liveData.value = lang
        sharPreferences.edit().putString(LANGUAGE,lang).apply()
        return liveData
    }
}