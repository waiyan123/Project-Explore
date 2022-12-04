package com.itachi.explore.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.AncientRepository
import com.itachi.core.data.AncientRepositoryImpl
import com.itachi.core.data.db.AncientRoomDataSource
import com.itachi.core.data.network.AncientFirebaseDataSource
import com.itachi.core.domain.AncientVO
import com.itachi.core.interactors.*
import com.itachi.explore.framework.AncientFirebaseDataSourceImpl
import com.itachi.explore.framework.AncientRoomDataSourceImpl
import com.itachi.explore.framework.mappers.*
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.persistence.entities.AncientEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DaggerAncientModule {

    @Provides
    @Singleton
    fun providesAddAllAncients(
        ancientRepository : AncientRepository
    ) : AddAllAncientsUseCase {
        return AddAllAncientsUseCase(ancientRepository)
    }

    @Provides
    @Singleton
    fun providesAddAncient(
        ancientRepository: AncientRepository
    ) : AddAncientUseCase {
        return AddAncientUseCase(ancientRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteAllAncients(
        ancientRepository: AncientRepository
    ) : DeleteAllAncientsUseCase {
        return DeleteAllAncientsUseCase(ancientRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteAncient(
        ancientRepository: AncientRepository
    ) : DeleteAncientUseCase {
        return DeleteAncientUseCase(ancientRepository)
    }

    @Provides
    @Singleton
    fun providesGetAllAncient(
        ancientRepository: AncientRepository
    ) : GetAllAncientUseCase {
        return GetAllAncientUseCase(ancientRepository)
    }

    @Provides
    @Singleton
    fun providesGetAncientById(
        ancientRepository: AncientRepository
    ) : GetAncientByIdUseCase {
        return GetAncientByIdUseCase(ancientRepository)
    }

    @Provides
    @Singleton
    fun providesUpdateAncient(
        ancientRepository: AncientRepository
    ) : UpdateAncientUseCase {
        return UpdateAncientUseCase(ancientRepository)
    }

    @Provides
    @Singleton
    fun providesGetAncientBackground(
        ancientRepository: AncientRepository
    ) : GetAncientBackgroundUseCase {
        return GetAncientBackgroundUseCase(ancientRepository)
    }

    @Provides
    @Singleton
    fun providesAncientRepository(
        ancientFirebaseDataSource: AncientFirebaseDataSource,
        ancientRoomDataSource: AncientRoomDataSource
    ) : AncientRepository {
        return AncientRepositoryImpl(ancientFirebaseDataSource,ancientRoomDataSource)
    }

    @Provides
    @Singleton
    fun providesAncientFirebaseDataSource(
        ancientMapper: AncientMapper,
        firestoreRef : FirebaseFirestore,
        firebaseStorage : FirebaseStorage,
        auth : FirebaseAuth
    ) : AncientFirebaseDataSource {
        return AncientFirebaseDataSourceImpl(ancientMapper,firestoreRef,firebaseStorage,auth)
    }

    @Provides
    @Singleton
    fun providesAncientRoomDataSource(
        ancientMapper: AncientMapper,
        database : MyDatabase
    ) : AncientRoomDataSource {
        return AncientRoomDataSourceImpl(ancientMapper,database)
    }

    @Provides
    @Singleton
    fun providesAncientMapper(
        ancientEntityToVoMapper: Mapper<AncientEntity,AncientVO>,
        ancientVoToEntityMapper: Mapper<AncientVO,AncientEntity>,
        ancientVoToFirebaseMapper: Mapper<AncientVO,HashMap<String,Any>>,
        ancientEntityListToVoListMapper : ListMapper<AncientEntity,AncientVO>,
        ancientVoListToEntityListMapper : ListMapper<AncientVO,AncientEntity>
    ) : AncientMapper {
        return AncientMapper(ancientEntityToVoMapper,ancientVoToEntityMapper,ancientVoToFirebaseMapper,
            ancientEntityListToVoListMapper as ListMapperImpl<AncientEntity, AncientVO>,
            ancientVoListToEntityListMapper as ListMapperImpl<AncientVO, AncientEntity>
        )
    }

    @Provides
    @Singleton
    fun providesAncientEntityListToVoListMapper(
        mapper : Mapper<AncientEntity,AncientVO>
    ) : ListMapper<AncientEntity,AncientVO> {
        return ListMapperImpl(mapper)
    }
    @Provides
    @Singleton
    fun providesAncientVoListToEntityListMapper(
        mapper : Mapper<AncientVO,AncientEntity>
    ) : ListMapper<AncientVO,AncientEntity> {
        return ListMapperImpl(mapper)
    }
    @Provides
    @Singleton
    fun providesAncientEntityToVoMapper() : Mapper<AncientEntity,AncientVO>{
        return AncientEntityToVoMapper()
    }
    @Provides
    @Singleton
    fun providesAncientVoToEntityMapper() : Mapper<AncientVO,AncientEntity> {
        return AncientVoToEntityMapper()
    }
    @Provides
    @Singleton
    fun providesAncientVoToFirebaseMapper() : Mapper<AncientVO,HashMap<String,Any>> {
        return AncientVoToFirebaseMapper()
    }

}