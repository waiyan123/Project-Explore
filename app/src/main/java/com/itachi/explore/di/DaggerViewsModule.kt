package com.itachi.explore.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.domain.repositories.ViewRepository
import com.itachi.core.data.ViewRepositoryImpl
import com.itachi.core.data.room.ViewRoomDataSource
import com.itachi.core.data.firebase.ViewFirebaseDataSource
import com.itachi.core.domain.usecases.*
import com.itachi.explore.framework.ViewFirebaseDataSourceImpl
import com.itachi.explore.framework.ViewRoomDataSourceImpl
import com.itachi.explore.framework.mappers.*
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.utils.LANGUAGE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaggerViewsModule {

    @Provides
    @Singleton
    fun providesAddView(
        viewRepository: ViewRepository
    ) : AddViewUseCase {
        return AddViewUseCase(viewRepository)
    }

    @Provides
    @Singleton
    fun providesAddAllViews(
        viewRepository: ViewRepository
    ) : AddAllViewsUseCase {
        return AddAllViewsUseCase(viewRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteAllViews(
        viewRepository: ViewRepository
    ) : DeleteAllViewsUseCase {
        return DeleteAllViewsUseCase(viewRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteView(
        viewRepository: ViewRepository
    ) : DeleteViewUseCase {
        return DeleteViewUseCase(viewRepository)
    }

    @Provides
    @Singleton
    fun providesGetAllViews(
        viewRepository: ViewRepository
    ) : GetAllViewsUseCase {
        return GetAllViewsUseCase(viewRepository)
    }

    @Provides
    @Singleton
    fun providesGetAllViewsPhoto(
        viewRepository: ViewRepository
    ) : GetAllViewsPhotoUseCase {
        return GetAllViewsPhotoUseCase(viewRepository)
    }

    @Provides
    @Singleton
    fun providesGetViewById(
        viewRepository: ViewRepository
    ) : GetViewByIdUseCase {
        return GetViewByIdUseCase(viewRepository)
    }

    @Provides
    @Singleton
    fun providesUpdateView(
        viewRepository: ViewRepository
    ) : UpdateViewUseCase {
        return UpdateViewUseCase(viewRepository)
    }

    @Provides
    @Singleton
    fun providesViewRepository(
        viewFirebaseDataSource: ViewFirebaseDataSource,
        viewRoomDataSource: ViewRoomDataSource
    ): ViewRepository {
        return ViewRepositoryImpl(viewFirebaseDataSource, viewRoomDataSource)
    }

    @Provides
    @Singleton
    fun providesViewFirebaseDataSource(
        viewMapper: ViewMapper,
        firebaseFirestore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
        firebaseAuth: FirebaseAuth
    ): ViewFirebaseDataSource {
        return ViewFirebaseDataSourceImpl(
            viewMapper,
            firebaseFirestore,
            firebaseStorage,
            firebaseAuth
        )
    }

    @Provides
    @Singleton
    fun providesViewRoomDataSource(
        viewMapper: ViewMapper,
        database: MyDatabase
    ): ViewRoomDataSource {
        return ViewRoomDataSourceImpl(viewMapper,database)
    }

    @Provides
    @Singleton
    fun providesViewMapper(): ViewMapper {
        return ViewMapper()
    }

    @Provides
    @Singleton
    fun providesFirebaseFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseStorage() : FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext context: Context) : SharedPreferences{
        return context.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE)
    }
}