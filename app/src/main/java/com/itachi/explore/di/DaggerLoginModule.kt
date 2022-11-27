package com.itachi.explore.di

import com.itachi.core.data.UserRepository
import com.itachi.core.interactors.AddUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaggerLoginModule {

    @Provides
    @Singleton
    fun providesAddUser(
        userRepository : UserRepository
    ) : AddUser {
        return AddUser(userRepository)
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