package com.itachi.explore.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.LanguageRepositoryImpl
import com.itachi.core.data.RemoteConfigRepository
import com.itachi.core.domain.repositories.UserRepository
import com.itachi.core.data.UserRepositoryImpl
import com.itachi.core.data.room.UserRoomDataSource
import com.itachi.core.data.firebase.FirebaseRemoteConfigDataSource
import com.itachi.core.data.firebase.UserFirebaseDataSource
import com.itachi.core.data.sharedpreferences.LanguageSharedPreferencesDataSource
import com.itachi.core.domain.usecases.*
import com.itachi.explore.framework.FirebaseRemoteConfigDataSourceImpl
import com.itachi.explore.framework.LanguageSharedPreferencesDataSourceImpl
import com.itachi.explore.framework.UserFirebaseDataSourceImpl
import com.itachi.explore.framework.UserRoomDataSourceImpl
import com.itachi.explore.framework.mappers.UserEntityToVoMapper
import com.itachi.explore.framework.mappers.UserMapper
import com.itachi.explore.framework.mappers.UserVoToEntityMapper
import com.itachi.explore.framework.mappers.UserVoToFirebaseMapper
import com.itachi.explore.persistence.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object DaggerMainModule {

    @Provides
    fun providesAppUpdateManager(
        @ApplicationContext context: Context
    ) : AppUpdateManager {
        return AppUpdateManagerFactory.create(context)
    }

    @Provides
    fun providesAddUser(
        userRepository : UserRepository
    ) : AddUserUseCase {
        return AddUserUseCase(userRepository)
    }

    @Provides
    fun providesGetUser(
        userRepository: UserRepository
    ) : GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Provides
    fun providesUpdateUserUseCase(
        userRepository: UserRepository
    ) : UpdateUserUseCase {
        return UpdateUserUseCase(userRepository)
    }

    @Provides
    fun providesSignOut(
        userRepository: UserRepository
    ) : SignOut {
        return SignOut(userRepository)
    }

    @Provides
    fun providesUserDataSource(
        userRoomDataSource: UserRoomDataSource,
        userFirebaseDataSource: UserFirebaseDataSource
    ): UserRepository {
        return UserRepositoryImpl(userFirebaseDataSource,userRoomDataSource)
    }

    @Provides
    fun providesUserRoomDataSource(
        auth : FirebaseAuth,
        userMapper: UserMapper,
        database : MyDatabase
    ) : UserRoomDataSource {
        return UserRoomDataSourceImpl(auth,userMapper,database)
    }

    @Provides
    fun providesMyRoomDatabase(@ApplicationContext context: Context) : MyDatabase {
        return Room.databaseBuilder(context, MyDatabase::class.java, "My Database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesUserFirebaseDataSource(userMapper: UserMapper,
                                       firestoreRef : FirebaseFirestore,
                                       firebaseStorage: FirebaseStorage,
                                       firebaseAuth: FirebaseAuth
    ) : UserFirebaseDataSource {
        return UserFirebaseDataSourceImpl(userMapper,firestoreRef,firebaseStorage,firebaseAuth)
    }

    @Provides
    fun providesUserMapper(userEntityToVoMapper: UserEntityToVoMapper,
                           userVoToEntityMapper: UserVoToEntityMapper,
                           userVoToFirebaseMapper: UserVoToFirebaseMapper
    ) : UserMapper {
        return UserMapper(userEntityToVoMapper,userVoToEntityMapper,userVoToFirebaseMapper)
    }

    @Provides
    fun providesUserEntityToVoMapper() : UserEntityToVoMapper {
        return UserEntityToVoMapper()
    }

    @Provides
    fun providesUserVoToEntityMapper() : UserVoToEntityMapper {
        return UserVoToEntityMapper()
    }

    @Provides
    fun providesUserVoToFirebaseMapper() : UserVoToFirebaseMapper {
        return UserVoToFirebaseMapper()
    }


    @Provides
    fun providesGetLanguage(
        languageRepository: LanguageRepositoryImpl
    ) : GetLanguageUseCase {
        return GetLanguageUseCase(languageRepository)
    }

    @Provides
    fun providesLanguageRepository(
        languageSharedPreferencesDataSource: LanguageSharedPreferencesDataSource
    ) : LanguageRepositoryImpl {
        return LanguageRepositoryImpl(languageSharedPreferencesDataSource)
    }

    @Provides
    fun providesLanguageSharedPreferencesDataSource(
        sharedPreferences : SharedPreferences
    ) : LanguageSharedPreferencesDataSource {
        return LanguageSharedPreferencesDataSourceImpl(sharedPreferences)
    }

    @Provides
    fun providesSetLanguage(
        languageRepository: LanguageRepositoryImpl
    ) : SetLanguageUseCase {
        return SetLanguageUseCase(languageRepository)
    }

    @Provides
    fun providesCheckAppVersionUpdate(
        remoteConfigRepository: RemoteConfigRepository
    ) : CheckAppVersionUpdate {
        return CheckAppVersionUpdate(remoteConfigRepository)
    }

    @Provides
    fun providesRemoteConfigRepository(
        firebaseRemoteConfigDataSource: FirebaseRemoteConfigDataSource
    ) : RemoteConfigRepository {
        return RemoteConfigRepository(firebaseRemoteConfigDataSource)
    }

    @Provides
    fun providesFirebaseRemoteConfigRepository(
        firebaseRemoteConfig : FirebaseRemoteConfig
    ) : FirebaseRemoteConfigDataSource {
        return FirebaseRemoteConfigDataSourceImpl(firebaseRemoteConfig)
    }

    @Provides
    fun providesRemoteConfig() : FirebaseRemoteConfig {
        return Firebase.remoteConfig
    }
}