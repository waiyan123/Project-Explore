//package com.itachi.explore.di
//
//import com.google.firebase.firestore.FirebaseFirestore
//import com.itachi.core.data.db.ViewRoomDataSource
//import com.itachi.core.data.network.ViewFirebaseDataSource
//import com.itachi.explore.framework.ViewFirebaseDataSourceImpl
//import com.itachi.explore.framework.ViewRoomDataSourceImpl
//import dagger.Binds
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class DataSourceModule {
//
//    @Binds
//    @Singleton
//    abstract fun bindsViewRoomDataSource(viewRoomDataSourceImpl : ViewRoomDataSourceImpl) : ViewRoomDataSource
//
//    @Binds
//    @Singleton
//    abstract fun bindsViewFirebaseDataSource(viewFirebaseDataSourceImpl: ViewFirebaseDataSourceImpl) : ViewFirebaseDataSource
//
//    @Binds
//    @Singleton
//    abstract fun bindsFirebaseFirestore() : FirebaseFirestore
//
//
//}