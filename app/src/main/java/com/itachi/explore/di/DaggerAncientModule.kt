package com.itachi.explore.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.domain.repositories.AncientRepository
import com.itachi.core.data.AncientRepositoryImpl
import com.itachi.core.data.room.AncientRoomDataSource
import com.itachi.core.data.firebase.AncientFirebaseDataSource
import com.itachi.core.domain.models.AncientVO
import com.itachi.core.domain.usecases.*
import com.itachi.explore.framework.AncientFirebaseDataSourceImpl
import com.itachi.explore.framework.AncientRoomDataSourceImpl
import com.itachi.explore.framework.mappers.*
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.persistence.entities.AncientEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object DaggerAncientModule {

    @Provides
    fun providesAddAllAncients(
        ancientRepository : AncientRepository
    ) : AddAllAncientsUseCase {
        return AddAllAncientsUseCase(ancientRepository)
    }

    @Provides
    fun providesAddAncient(
        ancientRepository: AncientRepository
    ) : AddAncientUseCase {
        return AddAncientUseCase(ancientRepository)
    }

    @Provides
    fun providesDeleteAllAncients(
        ancientRepository: AncientRepository
    ) : DeleteAllAncientsUseCase {
        return DeleteAllAncientsUseCase(ancientRepository)
    }

    @Provides
    fun providesDeleteAncient(
        ancientRepository: AncientRepository
    ) : DeleteAncientUseCase {
        return DeleteAncientUseCase(ancientRepository)
    }

    @Provides
    fun providesGetAllAncient(
        ancientRepository: AncientRepository
    ) : GetAllAncientUseCase {
        return GetAllAncientUseCase(ancientRepository)
    }

    @Provides
    fun providesGetAncientById(
        ancientRepository: AncientRepository
    ) : GetAncientByIdUseCase {
        return GetAncientByIdUseCase(ancientRepository)
    }

    @Provides
    fun providesUpdateAncient(
        ancientRepository: AncientRepository
    ) : UpdateAncientUseCase {
        return UpdateAncientUseCase(ancientRepository)
    }

    @Provides
    fun providesGetAncientBackground(
        ancientRepository: AncientRepository
    ) : GetAncientBackgroundUseCase {
        return GetAncientBackgroundUseCase(ancientRepository)
    }

    @Provides
    fun providesGetAncientsByUserIdUseCase(
        ancientRepository: AncientRepository
    ) : GetAncientsByUserIdUseCase {
        return GetAncientsByUserIdUseCase(ancientRepository)
    }

    @Provides
    fun providesAncientRepository(
        ancientFirebaseDataSource: AncientFirebaseDataSource,
        ancientRoomDataSource: AncientRoomDataSource
    ) : AncientRepository {
        return AncientRepositoryImpl(ancientFirebaseDataSource,ancientRoomDataSource)
    }

    @Provides
    fun providesAncientFirebaseDataSource(
        ancientMapper: AncientMapper,
        firestoreRef : FirebaseFirestore,
        firebaseStorage : FirebaseStorage,
        auth : FirebaseAuth
    ) : AncientFirebaseDataSource {
        return AncientFirebaseDataSourceImpl(ancientMapper,firestoreRef,firebaseStorage,auth)
    }

    @Provides
    fun providesAncientRoomDataSource(
        ancientMapper: AncientMapper,
        database : MyDatabase
    ) : AncientRoomDataSource {
        return AncientRoomDataSourceImpl(ancientMapper,database)
    }

    @Provides
    fun providesAncientMapper(
        ancientEntityToVoMapper: Mapper<AncientEntity, AncientVO>,
        ancientVoToEntityMapper: Mapper<AncientVO,AncientEntity>,
        ancientVoToFirebaseMapper: Mapper<AncientVO,HashMap<String,Any>>,
        ancientEntityListToVoListMapper : ListMapper<AncientEntity, AncientVO>,
        ancientVoListToEntityListMapper : ListMapper<AncientVO,AncientEntity>
    ) : AncientMapper {
        return AncientMapper(ancientEntityToVoMapper,ancientVoToEntityMapper,ancientVoToFirebaseMapper,
            ancientEntityListToVoListMapper as ListMapperImpl<AncientEntity, AncientVO>,
            ancientVoListToEntityListMapper as ListMapperImpl<AncientVO, AncientEntity>
        )
    }

    @Provides
    fun providesAncientEntityListToVoListMapper(
        mapper : Mapper<AncientEntity, AncientVO>
    ) : ListMapper<AncientEntity, AncientVO> {
        return ListMapperImpl(mapper)
    }

    @Provides
    fun providesAncientVoListToEntityListMapper(
        mapper : Mapper<AncientVO,AncientEntity>
    ) : ListMapper<AncientVO,AncientEntity> {
        return ListMapperImpl(mapper)
    }

    @Provides
    fun providesAncientEntityToVoMapper() : Mapper<AncientEntity, AncientVO>{
        return AncientEntityToVoMapper()
    }

    @Provides
    fun providesAncientVoToEntityMapper() : Mapper<AncientVO,AncientEntity> {
        return AncientVoToEntityMapper()
    }

    @Provides
    fun providesAncientVoToFirebaseMapper() : Mapper<AncientVO,HashMap<String,Any>> {
        return AncientVoToFirebaseMapper()
    }

}