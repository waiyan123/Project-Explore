package com.itachi.explore.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.*
import com.itachi.core.data.db.PagodaRoomDataSource
import com.itachi.core.data.network.PagodaFirebaseDataSource
import com.itachi.core.data.network.PhotoFirebaseDataSource
import com.itachi.core.domain.PagodaVO
import com.itachi.core.interactors.*
import com.itachi.explore.framework.PagodaFirebaseDataSourceImpl
import com.itachi.explore.framework.PagodaRoomDataSourceImpl
import com.itachi.explore.framework.PhotoFirebaseDataSourceImpl
import com.itachi.explore.framework.mappers.*
import com.itachi.explore.persistence.entities.PagodaEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaggerFormModule {
    @Provides
    @Singleton
    fun providesAddPagodaUseCase(
        pagodaRepository: PagodaRepository
    ) : AddPagodaUseCase {
        return AddPagodaUseCase(pagodaRepository)
    }

    @Provides
    @Singleton
    fun providesGetPagoda(
        pagodaRepository: PagodaRepository
    ) : GetPagoda{
        return GetPagoda(pagodaRepository)
    }

    @Provides
    @Singleton
    fun providesDeletePagoda(
        pagodaRepository : PagodaRepository
    ) : DeletePagoda{
        return DeletePagoda(pagodaRepository)
    }

    @Provides
    @Singleton
    fun providesGetAllPagodas(
        pagodaRepository: PagodaRepository
    ) : GetAllPagodas {
        return GetAllPagodas(pagodaRepository)
    }

    @Provides
    @Singleton
    fun providesGetPagodaBanner(
        pagodaRepository: PagodaRepository
    ) : GetPagodaBanner {
        return GetPagodaBanner(pagodaRepository)
    }

    @Provides
    @Singleton
    fun providesPagodaRepository(
        pagodaFirebaseDataSource: PagodaFirebaseDataSource,
        pagodaRoomDataSource : PagodaRoomDataSource
    ) : PagodaRepository {
        return PagodaRepositoryImpl(pagodaFirebaseDataSource,pagodaRoomDataSource)
    }

    @Provides
    @Singleton
    fun providesUploadPhotoUseCase(
        photoRepository: PhotoRepository
    ) : UploadPhotosUseCase{
        return UploadPhotosUseCase(photoRepository)
    }

    @Provides
    @Singleton
    fun providesPhotoRepository(
        photoFirebaseDataSource: PhotoFirebaseDataSource
    ) : PhotoRepository {
        return PhotoRepositoryImpl(photoFirebaseDataSource)
    }

    @Provides
    @Singleton
    fun providesPhotoFirebaseDataSource(
        firestoreRef : FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
        @ApplicationContext context: Context
    ) : PhotoFirebaseDataSource {
        return PhotoFirebaseDataSourceImpl(firestoreRef,firebaseStorage,context)
    }


    @Provides
    @Singleton
    fun providesPagodaFirebaseDataSource(
        pagodaMapper : PagodaMapper,
        firestoreRef: FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
        auth : FirebaseAuth
    ) : PagodaFirebaseDataSource {
        return PagodaFirebaseDataSourceImpl(pagodaMapper,firestoreRef,firebaseStorage,auth)
    }

    @Provides
    @Singleton
    fun providesPagodaMapper(
        pagodaEntityToVoMapper: PagodaEntityToVoMapper,
        pagodaVoToEntityMapper: PagodaVoToEntityMapper,
        pagodaVoToFirebaseMapper : PagodaVoToFirebaseMapper,
        pagodaEntityListToVoListMapper : ListMapper<PagodaEntity,PagodaVO>,
        pagodaVoListToEntityListMapper : ListMapper<PagodaVO,PagodaEntity>
    ) : PagodaMapper {
        return PagodaMapper(pagodaEntityToVoMapper,pagodaVoToEntityMapper,pagodaVoToFirebaseMapper,
            pagodaEntityListToVoListMapper as ListMapperImpl<PagodaEntity, PagodaVO>,
            pagodaVoListToEntityListMapper as ListMapperImpl<PagodaVO, PagodaEntity>
        )
    }

    @Provides
    @Singleton
    fun providesPagodaEntityToVoMapper() : Mapper<PagodaEntity,PagodaVO> {
        return PagodaEntityToVoMapper()
    }

    @Provides
    @Singleton
    fun providesPagodaVoToEntityMapper() : Mapper<PagodaVO,PagodaEntity> {
        return PagodaVoToEntityMapper()
    }

    @Provides
    @Singleton
    fun providesPagodaVoToFirebaseMapper() :Mapper<PagodaVO,HashMap<String,Any>> {
        return PagodaVoToFirebaseMapper()
    }

    @Provides
    @Singleton
    fun providesPagodaEntityListToVoListMapper(
        mapper : Mapper<PagodaEntity,PagodaVO>
    ) : ListMapper<PagodaEntity,PagodaVO> {
        return ListMapperImpl(mapper)
    }

    @Provides
    @Singleton
    fun providesPagodaVoListToEntityListMapper(
        mapper : Mapper<PagodaVO,PagodaEntity>
    ) : ListMapper<PagodaVO,PagodaEntity> {
        return ListMapperImpl(mapper)
    }


    @Provides
    @Singleton
    fun providesPagodaRoomDataSource(
        pagodaMapper: PagodaMapper
    ) : PagodaRoomDataSource {
        return PagodaRoomDataSourceImpl(pagodaMapper)
    }

}