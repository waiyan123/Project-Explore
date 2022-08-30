package com.itachi.explore.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.ViewRepository
import com.itachi.core.data.db.ViewRoomDataSource
import com.itachi.core.data.network.ViewFirebaseDataSource
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.framework.ViewFirebaseDataSourceImpl
import com.itachi.explore.framework.ViewRoomDataSourceImpl
import com.itachi.explore.framework.mappers.*
import com.itachi.explore.persistence.entities.UploadedPhotoEntity
import com.itachi.explore.persistence.entities.ViewEntity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaggerModule {

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
    fun providesViewRoomDataSource(viewMapper: ViewMapper): ViewRoomDataSource {
        return ViewRoomDataSourceImpl(viewMapper)
    }

    @Provides
    @Singleton
    fun providesViewRepository(
        viewFirebaseDataSource: ViewFirebaseDataSource,
        viewRoomDataSource: ViewRoomDataSource
    ): ViewRepository {
        return ViewRepository(viewFirebaseDataSource, viewRoomDataSource)
    }


}