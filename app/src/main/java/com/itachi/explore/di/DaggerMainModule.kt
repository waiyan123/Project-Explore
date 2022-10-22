package com.itachi.explore.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.LanguageRepository
import com.itachi.core.data.RemoteConfigRepository
import com.itachi.core.data.UserDataSource
import com.itachi.core.data.UserRepository
import com.itachi.core.data.db.UserRoomDataSource
import com.itachi.core.data.network.FirebaseRemoteConfigDataSource
import com.itachi.core.data.network.UserFirebaseDataSource
import com.itachi.core.data.sharedpreferences.LanguageSharedPreferencesDataSource
import com.itachi.core.interactors.*
import com.itachi.explore.framework.FirebaseRemoteConfigDataSourceImpl
import com.itachi.explore.framework.LanguageSharedPreferencesDataSourceImpl
import com.itachi.explore.framework.UserFirebaseDataSourceImpl
import com.itachi.explore.framework.UserRoomDataSourceImpl
import com.itachi.explore.framework.mappers.UserEntityToVoMapper
import com.itachi.explore.framework.mappers.UserMapper
import com.itachi.explore.framework.mappers.UserVoToEntityMapper
import com.itachi.explore.framework.mappers.UserVoToFirebaseMapper
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.utils.LANGUAGE
import com.itachi.explore.utils.PRIVATE_MODE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.koin.android.ext.koin.androidContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaggerMainModule {

    @Provides
    @Singleton
    fun providesGetUser(
        userDataSource: UserDataSource
    ) : GetUser{
        return GetUser(userDataSource)
    }

    @Provides
    @Singleton
    fun providesSignOut(
        userDataSource: UserDataSource
    ) : SignOut {
        return SignOut(userDataSource)
    }

    @Provides
    @Singleton
    fun providesUserDataSource(
        userRoomDataSource: UserRoomDataSource,
        userFirebaseDataSource: UserFirebaseDataSource
    ): UserDataSource {
        return UserRepository(userFirebaseDataSource,userRoomDataSource)
    }

    @Provides
    @Singleton
    fun providesUserRoomDataSource(
        auth : FirebaseAuth,
        userMapper: UserMapper,
        database : MyDatabase
    ) : UserRoomDataSource {
        return UserRoomDataSourceImpl(auth,userMapper,database)
    }

    @Provides
    @Singleton
    fun providesMyRoomDatabase(@ApplicationContext context: Context) : MyDatabase {
        return Room.databaseBuilder(context, MyDatabase::class.java, "My Database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesUserFirebaseDataSource(userMapper: UserMapper,
                                       firestoreRef : FirebaseFirestore,
                                       firebaseStorage: FirebaseStorage,
                                       firebaseAuth: FirebaseAuth
    ) : UserFirebaseDataSource {
        return UserFirebaseDataSourceImpl(userMapper,firestoreRef,firebaseStorage,firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesUserMapper(userEntityToVoMapper: UserEntityToVoMapper,
                           userVoToEntityMapper: UserVoToEntityMapper,
                           userVoToFirebaseMapper: UserVoToFirebaseMapper
    ) : UserMapper {
        return UserMapper(userEntityToVoMapper,userVoToEntityMapper,userVoToFirebaseMapper)
    }

    @Provides
    @Singleton
    fun providesUserEntityToVoMapper() : UserEntityToVoMapper {
        return UserEntityToVoMapper()
    }

    @Provides
    @Singleton
    fun providesUserVoToEntityMapper() : UserVoToEntityMapper {
        return UserVoToEntityMapper()
    }

    @Provides
    @Singleton
    fun providesUserVoToFirebaseMapper() : UserVoToFirebaseMapper {
        return UserVoToFirebaseMapper()
    }


    @Provides
    @Singleton
    fun providesGetLanguage(
        languageRepository: LanguageRepository
    ) : GetLanguage{
        return GetLanguage(languageRepository)
    }

    @Provides
    @Singleton
    fun providesLanguageRepository(
        languageSharedPreferencesDataSource: LanguageSharedPreferencesDataSource
    ) : LanguageRepository {
        return LanguageRepository(languageSharedPreferencesDataSource)
    }

    @Provides
    @Singleton
    fun providesLanguageSharedPreferencesDataSource(
        sharedPreferences : SharedPreferences
    ) : LanguageSharedPreferencesDataSource {
        return LanguageSharedPreferencesDataSourceImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providesSetLanguage(
        languageRepository: LanguageRepository
    ) : SetLanguage {
        return SetLanguage(languageRepository)
    }

    @Provides
    @Singleton
    fun providesCheckAppVersionUpdate(
        remoteConfigRepository: RemoteConfigRepository
    ) : CheckAppVersionUpdate {
        return CheckAppVersionUpdate(remoteConfigRepository)
    }

    @Provides
    @Singleton
    fun providesRemoteConfigRepository(
        firebaseRemoteConfigDataSource: FirebaseRemoteConfigDataSource
    ) : RemoteConfigRepository {
        return RemoteConfigRepository(firebaseRemoteConfigDataSource)
    }

    @Provides
    @Singleton
    fun providesFirebaseRemoteConfigRepository(
        firebaseRemoteConfig : FirebaseRemoteConfig
    ) : FirebaseRemoteConfigDataSource {
        return FirebaseRemoteConfigDataSourceImpl(firebaseRemoteConfig)
    }

//    @Provides
//    @Singleton
//    fun providesFirebaseFirestore() : FirebaseFirestore {
//        return FirebaseFirestore.getInstance()
//    }
//
//    @Provides
//    @Singleton
//    fun providesFirebaseStorage() : FirebaseStorage {
//        return FirebaseStorage.getInstance()
//    }
//
//    @Provides
//    @Singleton
//    fun providesFirebaseAuth() : FirebaseAuth {
//        return FirebaseAuth.getInstance()
//    }

//    @Provides
//    @Singleton
//    fun providesSharedPreferences(@ApplicationContext context: Context) : SharedPreferences {
//        return context.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE)
//    }

    @Provides
    @Singleton
    fun providesRemoteConfig() : FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance()
    }
}