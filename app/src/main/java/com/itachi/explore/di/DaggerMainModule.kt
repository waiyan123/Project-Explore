package com.itachi.explore.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.UserRepository
import com.itachi.core.data.db.UserRoomDataSource
import com.itachi.core.data.network.UserFirebaseDataSource
import com.itachi.explore.framework.UserFirebaseDataSourceImpl
import com.itachi.explore.framework.UserRoomDataSourceImpl
import com.itachi.explore.framework.mappers.UserEntityToVoMapper
import com.itachi.explore.framework.mappers.UserMapper
import com.itachi.explore.framework.mappers.UserVoToEntityMapper
import com.itachi.explore.framework.mappers.UserVoToFirebaseMapper
import com.itachi.explore.utils.LANGUAGE
import com.itachi.explore.utils.PRIVATE_MODE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaggerMainModule {

    @Provides
    @Singleton
    fun providesUserRepository(
        userRoomDataSource: UserRoomDataSource,
        userFirebaseDataSource: UserFirebaseDataSource
    ): UserRepository {
        return UserRepository(userRoomDataSource,userFirebaseDataSource)
    }

    @Provides
    @Singleton
    fun providesUserRoomDataSource(userMapper: UserMapper) : UserRoomDataSource {
        return UserRoomDataSourceImpl(userMapper)
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