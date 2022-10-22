package com.itachi.explore.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.LanguageDataSource
import com.itachi.core.data.LanguageRepository
import com.itachi.core.data.UserDataSource
import com.itachi.core.data.UserRepository
import com.itachi.core.data.db.UserRoomDataSource
import com.itachi.core.data.network.UserFirebaseDataSource
import com.itachi.core.data.sharedpreferences.LanguageSharedPreferencesDataSource
import com.itachi.core.interactors.AddUser
import com.itachi.core.interactors.GetLanguage
import com.itachi.explore.framework.LanguageSharedPreferencesDataSourceImpl
import com.itachi.explore.framework.UserFirebaseDataSourceImpl
import com.itachi.explore.framework.UserRoomDataSourceImpl
import com.itachi.explore.framework.mappers.UserEntityToVoMapper
import com.itachi.explore.framework.mappers.UserMapper
import com.itachi.explore.framework.mappers.UserVoToEntityMapper
import com.itachi.explore.framework.mappers.UserVoToFirebaseMapper
import com.itachi.explore.utils.LANGUAGE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaggerLoginModule {

    @Provides
    @Singleton
    fun providesAddUser(
        userDataSource : UserDataSource
    ) : AddUser {
        return AddUser(userDataSource)
    }

//    @Provides
//    @Singleton
//    fun providesUserDataSource(
//        userFirebaseDataSource : UserFirebaseDataSource,
//        userRoomDataSource : UserRoomDataSource
//    ) : UserDataSource {
//        return UserRepository(userFirebaseDataSource,userRoomDataSource)
//    }
//
//    @Provides
//    @Singleton
//    fun providesUserFirebaseDataSource(
//        userMapper : UserMapper,
//        firestoreRef : FirebaseFirestore,
//        firebaseStorage : FirebaseStorage,
//        auth : FirebaseAuth
//    ) : UserFirebaseDataSource {
//        return UserFirebaseDataSourceImpl(userMapper,firestoreRef,firebaseStorage,auth)
//    }
//
//    @Provides
//    @Singleton
//    fun providesUserMapper(userEntityToVoMapper: UserEntityToVoMapper,
//                           userVoToEntityMapper: UserVoToEntityMapper,
//                           userVoToFirebaseMapper: UserVoToFirebaseMapper
//    ) : UserMapper {
//        return UserMapper(userEntityToVoMapper,userVoToEntityMapper,userVoToFirebaseMapper)
//    }
//
//    @Provides
//    @Singleton
//    fun providesUserEntityToVoMapper() : UserEntityToVoMapper {
//        return UserEntityToVoMapper()
//    }
//
//    @Provides
//    @Singleton
//    fun providesUserVoToEntityMapper() : UserVoToEntityMapper {
//        return UserVoToEntityMapper()
//    }
//
//    @Provides
//    @Singleton
//    fun providesUserVoToFirebaseMapper() : UserVoToFirebaseMapper {
//        return UserVoToFirebaseMapper()
//    }

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
//    fun providesUserRoomDataSource(
//        userMapper : UserMapper
//    ) : UserRoomDataSource {
//        return UserRoomDataSourceImpl(userMapper)
//    }
//
//    @Provides
//    @Singleton
//    fun providesGetLanguage(
//        languageDataSource : LanguageDataSource
//    ) : GetLanguage {
//        return GetLanguage(languageDataSource)
//    }
//
//    @Provides
//    @Singleton
//    fun providesLanguageDataSource(
//        languageSharedPreferencesDataSource : LanguageSharedPreferencesDataSource
//    ) : LanguageDataSource {
//        return LanguageRepository(languageSharedPreferencesDataSource)
//    }
//
//    @Provides
//    @Singleton
//    fun providesLanguageSharedPreferencesDataSource(
//        sharedPreferences : SharedPreferences
//    ) : LanguageSharedPreferencesDataSource {
//        return LanguageSharedPreferencesDataSourceImpl(sharedPreferences)
//    }

//    @Provides
//    @Singleton
//    fun providesSharedPreferences(
//        @ApplicationContext context: Context
//    ) : SharedPreferences {
//        return context.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE)
//    }
}