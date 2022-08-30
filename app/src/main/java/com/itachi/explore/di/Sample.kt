//package com.itachi.explore.di
//
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.storage.FirebaseStorage
//import com.itachi.core.data.ViewRepository
//import com.itachi.core.data.db.ViewRoomDataSource
//import com.itachi.core.data.network.ViewFirebaseDataSource
//import com.itachi.core.domain.UploadedPhotoVO
//import com.itachi.core.domain.ViewVO
//import com.itachi.explore.framework.ViewFirebaseDataSourceImpl
//import com.itachi.explore.framework.ViewRoomDataSourceImpl
//import com.itachi.explore.framework.mappers.*
//import com.itachi.explore.persistence.entities.UploadedPhotoEntity
//import com.itachi.explore.persistence.entities.ViewEntity
//import dagger.Provides
//
//@Provides
//fun provideViewRepository(
//    viewFirebaseDataSource: ViewFirebaseDataSource,
//    viewRoomDataSource: ViewRoomDataSource
//): ViewRepository {
//    return ViewRepository(viewFirebaseDataSource, viewRoomDataSource)
//}
//
//@Provides
//fun provideViewFirebaseDataSourceImpl(
//    viewMapper: ViewMapper, firestoreRef: FirebaseFirestore,
//    firebaseStorageRef: FirebaseStorage, firebaseAuth: FirebaseAuth
//): ViewFirebaseDataSourceImpl {
//    return ViewFirebaseDataSourceImpl(
//        viewMapper,
//        firestoreRef,
//        firebaseStorageRef,
//        firebaseAuth
//    )
//}
//
//
//@Provides
//fun provideViewRoomDataSourceImpl(viewMapper: ViewMapper): ViewRoomDataSourceImpl {
//    return ViewRoomDataSourceImpl(viewMapper)
//}
//
//
//@Provides
//fun provideFirebaseFirestore(): FirebaseFirestore {
//    return FirebaseFirestore.getInstance()
//}
//
//@Provides
//fun provideFirebaseStorage(): FirebaseStorage {
//    return FirebaseStorage.getInstance()
//}
//
//@Provides
//fun provideFirebaseAuth(): FirebaseAuth {
//    return FirebaseAuth.getInstance()
//}
//
//@Provides
//fun provideViewEntityToVoMapper(): Mapper<ViewEntity, ViewVO> {
//    return ViewEntityToVoMapper()
//}
//
//@Provides
//fun provideViewVoToEntityMapper(): Mapper<ViewVO, ViewEntity> {
//    return ViewVoToEntityMapper()
//}
//
//@Provides
//fun provideViewVoToFirebaseMapper() = ViewVoToFirebaseMapper()
//
////may not needed
//@Provides
//fun provideUploadedPhotoEntityToVoMapper(): Mapper<UploadedPhotoEntity, UploadedPhotoVO> {
//    return UploadedPhotoEntityToVoMapper()
//}
//
//@Provides
//fun provideUploadedPhotoEntityListToVoListMapper(
//    uploadedPhotoEntitylistToVoListMapperImpl: ListMapper<ArrayList<UploadedPhotoEntity>,
//            ArrayList<UploadedPhotoVO>>
//): ListMapper<ArrayList<UploadedPhotoEntity>, ArrayList<UploadedPhotoVO>> {
//    return uploadedPhotoEntitylistToVoListMapperImpl
//}
//
//
//@Provides
//fun provideViewEntityListToVoList(
//    viewEntityListToVoListMapperImpl: ListMapperImpl<ArrayList<ViewEntity>, ArrayList<ViewVO>>
//): ListMapper<ArrayList<ViewEntity>, ArrayList<ViewVO>> {
//    return viewEntityListToVoListMapperImpl
//}
//
//@Provides
//fun provideViewVoListToEntityList(viewVoListToEntityListMapperImpl: ListMapperImpl<ArrayList<ViewVO>, ArrayList<ViewEntity>>):
//        ListMapper<ArrayList<ViewVO>, ArrayList<ViewEntity>> {
//    return viewVoListToEntityListMapperImpl
//}
//
//@Provides
//fun provideViewMapper(
//    photoEntityListToVoMapper: ListMapperImpl<UploadedPhotoEntity, UploadedPhotoVO>,
//    viewEntityToVoMapper: ViewEntityToVoMapper,
//    viewVoToEntityMapper: ViewVoToEntityMapper,
//    viewVoToFirebaseMapper: ViewVoToFirebaseMapper,
//    viewEntityListToVOListMapper: ListMapperImpl<ViewEntity, ViewVO>,
//    viewVoListToEntityListMapper: ListMapperImpl<ViewVO, ViewEntity>
//) =
//    ViewMapper(
//        photoEntityListToVoMapper,
//        viewEntityToVoMapper,
//        viewVoToEntityMapper,
//        viewVoToFirebaseMapper,
//        viewEntityListToVOListMapper,
//        viewVoListToEntityListMapper
//    )