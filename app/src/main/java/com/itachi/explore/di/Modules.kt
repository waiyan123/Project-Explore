package com.itachi.explore.di

import android.content.SharedPreferences
import androidx.room.Room
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.myanmartools.ZawgyiDetector
import com.itachi.explore.mvvm.model.*
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.third_parties.GlideLoadingService
import com.itachi.explore.utils.LANGUAGE
import com.itachi.explore.utils.PRIVATE_MODE
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module(override = true) {

    single {
        Identity.getSignInClient(androidContext())
    }

    single {
        LanguageModelImpl()
    }
    single {
        AuthenticationModelImpl()
    }
    single {
        UserModelImpl()
    }
    single {

        Room.databaseBuilder(androidContext(), MyDatabase::class.java, "My Database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(LANGUAGE, PRIVATE_MODE)
    }

    single {
        GlideLoadingService(androidContext())
    }

    single {
        ViewsModelImpl()
    }

//    single {
//        AncientModelImpl()
//    }

    single {
        UploadModelImpl()
    }

    single {
        ZawgyiDetector()
    }

    single {
        YoutubeModelImpl()
    }

    single {
        ShweboModelImpl()
    }

    single {
        FirebaseRemoteConfig.getInstance()
    }
}