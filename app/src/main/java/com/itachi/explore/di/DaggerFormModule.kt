package com.itachi.explore.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.*
import com.itachi.core.data.room.PagodaRoomDataSource
import com.itachi.core.data.firebase.PagodaFirebaseDataSource
import com.itachi.core.data.firebase.PhotoFirebaseDataSource
import com.itachi.core.domain.models.PagodaVO
import com.itachi.core.domain.models.UploadedPhotoVO
import com.itachi.core.domain.repositories.AncientRepository
import com.itachi.core.domain.repositories.PagodaRepository
import com.itachi.core.domain.repositories.PhotoRepository
import com.itachi.core.domain.repositories.ViewRepository
import com.itachi.core.domain.usecases.*
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
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object DaggerFormModule {

    @Provides
    fun providesAddPagodaUseCase(
        pagodaRepository: PagodaRepository
    ) : AddPagodaUseCase {
        return AddPagodaUseCase(pagodaRepository)
    }

    @Provides
    fun providesGetPagoda(
        pagodaRepository: PagodaRepository
    ) : GetPagodaByIdUseCase {
        return GetPagodaByIdUseCase(pagodaRepository)
    }

    @Provides
    fun providesDeletePagoda(
        pagodaRepository : PagodaRepository
    ) : DeletePagodaUseCase {
        return DeletePagodaUseCase(pagodaRepository)
    }

    @Provides
    fun providesGetAllPagodas(
        pagodaRepository: PagodaRepository
    ) : GetAllPagodasUseCase {
        return GetAllPagodasUseCase(pagodaRepository)
    }

    @Provides
    fun providesUpdatePagoda(
        pagodaRepository: PagodaRepository
    ) : UpdatePagodaUseCase {
        return UpdatePagodaUseCase(pagodaRepository)
    }

    @Provides
    fun providesGetPagodasByUserIdUseCase(
        pagodaRepository: PagodaRepository
    ) : GetPagodasByUserIdUseCase {
        return GetPagodasByUserIdUseCase(pagodaRepository)
    }

    @Provides
    fun providesGetPagodaBanner(
        pagodaRepository: PagodaRepository
    ) : GetPagodaBannerUseCase {
        return GetPagodaBannerUseCase(pagodaRepository)
    }

    @Provides
    fun providesPagodaRepository(
        pagodaFirebaseDataSource: PagodaFirebaseDataSource,
        pagodaRoomDataSource : PagodaRoomDataSource
    ) : PagodaRepository {
        return PagodaRepositoryImpl(pagodaFirebaseDataSource,pagodaRoomDataSource)
    }

    @Provides
    fun providesUploadPhotoUseCase(
        photoRepository: PhotoRepository
    ) : UploadPhotosUseCase {
        return UploadPhotosUseCase(photoRepository)
    }

    @Provides
    fun providesDeletePhotoUseCase(
        photoRepository: PhotoRepository
    ) : DeletePhotoUseCase {
        return DeletePhotoUseCase(photoRepository)
    }

    @Provides
    fun providesUploadPhotoUrlUseCase(
        photoRepository: PhotoRepository
    ) : UploadPhotoUrlUseCase {
        return UploadPhotoUrlUseCase(photoRepository)
    }

    @Provides
    fun providesPhotoRepository(
        photoFirebaseDataSource: PhotoFirebaseDataSource
    ) : PhotoRepository {
        return PhotoRepositoryImpl(photoFirebaseDataSource)
    }

    @Provides
    fun providesPhotoFirebaseDataSource(
        uploadedPhotoMapper: UploadedPhotoMapperFunctions,
        firestoreRef : FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
        @ApplicationContext context: Context
    ) : PhotoFirebaseDataSource {
        return PhotoFirebaseDataSourceImpl(uploadedPhotoMapper,firestoreRef,firebaseStorage,context)
    }

    @Provides
    fun providesUploadedPhotoMapper(
        uploadedPhotoEntityToVoMapper: Mapper<UploadedPhotoEntity, UploadedPhotoVO>,
        uploadedPhotoVoToEntityMapper: Mapper<UploadedPhotoVO,UploadedPhotoEntity>,
        uploadedPhotoVoToFirebaseMapper: Mapper<UploadedPhotoVO,HashMap<String,Any>>
    ) : UploadedPhotoMapperFunctions {
        return UploadedPhotoMapper(uploadedPhotoEntityToVoMapper,
            uploadedPhotoVoToEntityMapper,uploadedPhotoVoToFirebaseMapper)
    }

    @Provides
    fun providesUploadedPhotoEntityToVoMapper() : Mapper<UploadedPhotoEntity, UploadedPhotoVO> {
        return UploadedPhotoEntityToVoMapper()
    }

    @Provides
    fun providesUploadedPhotoVoToEntityMapper() : Mapper<UploadedPhotoVO,UploadedPhotoEntity> {
        return UploadedPhotoVoToEntityMapper()
    }

    @Provides
    fun providesUploadedPhotoVoToFirebaseMapper() : Mapper<UploadedPhotoVO,HashMap<String,Any>> {
        return UploadedPhotoVoToFirebaseMapper()
    }


    @Provides
    fun providesPagodaFirebaseDataSource(
        pagodaMapper : PagodaMapper,
        firestoreRef: FirebaseFirestore,
        firebaseStorage: FirebaseStorage,
        auth : FirebaseAuth
    ) : PagodaFirebaseDataSource {
        return PagodaFirebaseDataSourceImpl(pagodaMapper,firestoreRef,firebaseStorage,auth)
    }

    @Provides
    fun providesPagodaMapper(
        pagodaEntityToVoMapper: PagodaEntityToVoMapper,
        pagodaVoToEntityMapper: PagodaVoToEntityMapper,
        pagodaVoToFirebaseMapper : PagodaVoToFirebaseMapper,
        pagodaEntityListToVoListMapper : ListMapper<PagodaEntity, PagodaVO>,
        pagodaVoListToEntityListMapper : ListMapper<PagodaVO,PagodaEntity>
    ) : PagodaMapper {
        return PagodaMapper(pagodaEntityToVoMapper,pagodaVoToEntityMapper,pagodaVoToFirebaseMapper,
            pagodaEntityListToVoListMapper as ListMapperImpl<PagodaEntity, PagodaVO>,
            pagodaVoListToEntityListMapper as ListMapperImpl<PagodaVO, PagodaEntity>
        )
    }

    @Provides
    fun providesPagodaEntityToVoMapper() : Mapper<PagodaEntity, PagodaVO> {
        return PagodaEntityToVoMapper()
    }

    @Provides
    fun providesPagodaVoToEntityMapper() : Mapper<PagodaVO,PagodaEntity> {
        return PagodaVoToEntityMapper()
    }

    @Provides
    fun providesPagodaVoToFirebaseMapper() :Mapper<PagodaVO,HashMap<String,Any>> {
        return PagodaVoToFirebaseMapper()
    }

    @Provides
    fun providesPagodaEntityListToVoListMapper(
        mapper : Mapper<PagodaEntity, PagodaVO>
    ) : ListMapper<PagodaEntity, PagodaVO> {
        return ListMapperImpl(mapper)
    }

    @Provides
    fun providesPagodaVoListToEntityListMapper(
        mapper : Mapper<PagodaVO,PagodaEntity>
    ) : ListMapper<PagodaVO,PagodaEntity> {
        return ListMapperImpl(mapper)
    }


    @Provides
    fun providesPagodaRoomDataSource(
        pagodaMapper: PagodaMapper,
        database : MyDatabase
    ) : PagodaRoomDataSource {
        return PagodaRoomDataSourceImpl(pagodaMapper,database)
    }

}