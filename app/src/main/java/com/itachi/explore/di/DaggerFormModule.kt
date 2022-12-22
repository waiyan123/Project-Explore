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
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.interactors.*
import com.itachi.explore.framework.PagodaFirebaseDataSourceImpl
import com.itachi.explore.framework.PagodaRoomDataSourceImpl
import com.itachi.explore.framework.PhotoFirebaseDataSourceImpl
import com.itachi.explore.framework.mappers.*
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.persistence.entities.PagodaEntity
import com.itachi.explore.persistence.entities.UploadedPhotoEntity
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
    ) : GetPagodaByIdUseCase{
        return GetPagodaByIdUseCase(pagodaRepository)
    }

    @Provides
    @Singleton
    fun providesDeletePagoda(
        pagodaRepository : PagodaRepository
    ) : DeletePagodaUseCase{
        return DeletePagodaUseCase(pagodaRepository)
    }

    @Provides
    @Singleton
    fun providesGetAllPagodas(
        pagodaRepository: PagodaRepository
    ) : GetAllPagodasUseCase {
        return GetAllPagodasUseCase(pagodaRepository)
    }

    @Provides
    @Singleton
    fun providesUpdatePagoda(
        pagodaRepository: PagodaRepository
    ) : UpdatePagodaUseCase {
        return UpdatePagodaUseCase(pagodaRepository)
    }

    @Provides
    @Singleton
    fun providesGetPagodaBanner(
        pagodaRepository: PagodaRepository
    ) : GetPagodaBannerUseCase {
        return GetPagodaBannerUseCase(pagodaRepository)
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
    fun providesDeletePhotoUseCase(
        photoRepository: PhotoRepository
    ) : DeletePhotoUseCase {
        return DeletePhotoUseCase(photoRepository)
    }

    @Provides
    @Singleton
    fun providesUploadPhotoUrlUseCase(
        photoRepository: PhotoRepository
    ) : UploadPhotoUrlUseCase {
        return UploadPhotoUrlUseCase(photoRepository)
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
        uploadedPhotoMapper: UploadedPhotoMapperFunctions,
        firestoreRef : FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
        @ApplicationContext context: Context
    ) : PhotoFirebaseDataSource {
        return PhotoFirebaseDataSourceImpl(uploadedPhotoMapper,firestoreRef,firebaseStorage,context)
    }

    @Provides
    @Singleton
    fun providesUploadedPhotoMapper(
        uploadedPhotoEntityToVoMapper: Mapper<UploadedPhotoEntity, UploadedPhotoVO>,
        uploadedPhotoVoToEntityMapper: Mapper<UploadedPhotoVO,UploadedPhotoEntity>,
        uploadedPhotoVoToFirebaseMapper: Mapper<UploadedPhotoVO,HashMap<String,Any>>
    ) : UploadedPhotoMapperFunctions {
        return UploadedPhotoMapper(uploadedPhotoEntityToVoMapper,
            uploadedPhotoVoToEntityMapper,uploadedPhotoVoToFirebaseMapper)
    }

    @Provides
    @Singleton
    fun providesUploadedPhotoEntityToVoMapper() : Mapper<UploadedPhotoEntity,UploadedPhotoVO> {
        return UploadedPhotoEntityToVoMapper()
    }

    @Provides
    @Singleton
    fun providesUploadedPhotoVoToEntityMapper() : Mapper<UploadedPhotoVO,UploadedPhotoEntity> {
        return UploadedPhotoVoToEntityMapper()
    }

    @Provides
    @Singleton
    fun providesUploadedPhotoVoToFirebaseMapper() : Mapper<UploadedPhotoVO,HashMap<String,Any>> {
        return UploadedPhotoVoToFirebaseMapper()
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
        pagodaMapper: PagodaMapper,
        database : MyDatabase
    ) : PagodaRoomDataSource {
        return PagodaRoomDataSourceImpl(pagodaMapper,database)
    }

    @Provides
    @Singleton
    fun providesGetPagodasByUserIdUseCase(
        pagodaRepository: PagodaRepository
    ) : GetPagodasByUserIdUseCase {
        return GetPagodasByUserIdUseCase(pagodaRepository)
    }

    @Provides
    @Singleton
    fun providesGetViewsByUserIdUseCase(
        viewRepository: ViewRepository
    ) : GetViewsByUserIdUseCase {
        return GetViewsByUserIdUseCase(viewRepository)
    }

    @Provides
    @Singleton
    fun providesGetAncientsByUserIdUseCase(
        ancientRepository: AncientRepository
    ) : GetAncientsByUserIdUseCase {
        return GetAncientsByUserIdUseCase(ancientRepository)
    }

}