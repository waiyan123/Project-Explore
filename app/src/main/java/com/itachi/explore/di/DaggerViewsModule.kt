package com.itachi.explore.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.ViewRepository
import com.itachi.core.data.ViewRepositoryImpl
import com.itachi.core.data.db.ViewRoomDataSource
import com.itachi.core.data.network.ViewFirebaseDataSource
import com.itachi.core.interactors.*
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
    ) : AddView {
        return AddView(viewRepository)
    }

    @Provides
    @Singleton
    fun providesAddAllViews(
        viewRepository: ViewRepository
    ) : AddAllViews {
        return AddAllViews(viewRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteAllViews(
        viewRepository: ViewRepository
    ) : DeleteAllViews {
        return DeleteAllViews(viewRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteView(
        viewRepository: ViewRepository
    ) : DeleteView {
        return DeleteView(viewRepository)
    }

    @Provides
    @Singleton
    fun providesGetAllViews(
        viewRepository: ViewRepository
    ) : GetAllViews {
        return GetAllViews(viewRepository)
    }

    @Provides
    @Singleton
    fun providesGetAllViewsPhoto(
        viewRepository: ViewRepository
    ) : GetAllViewsPhoto {
        return GetAllViewsPhoto(viewRepository)
    }

    @Provides
    @Singleton
    fun providesGetViewById(
        viewRepository: ViewRepository
    ) : GetViewById {
        return GetViewById(viewRepository)
    }

    @Provides
    @Singleton
    fun providesUpdateView(
        viewRepository: ViewRepository
    ) : UpdateView {
        return UpdateView(viewRepository)
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