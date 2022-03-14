package com.itachi.explore

import com.itachi.core.data.AncientRepository
import android.app.Application
import com.facebook.FacebookSdk
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.PagodaRepository
import com.itachi.core.interactors.*
import com.itachi.explore.di.applicationModule
import com.itachi.explore.framework.*
import com.itachi.explore.framework.mappers.*
import com.itachi.explore.mvvm.viewmodel.MyViewModelProviderFactory
import com.itachi.explore.third_parties.GlideLoadingService
import me.myatminsoe.mdetect.MDetect
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ss.com.bannerslider.Slider

class MyApplication : Application() {

    private val imageLoadingService: GlideLoadingService by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(applicationModule))
        }
        MDetect.init(this@MyApplication)
        Slider.init(imageLoadingService)
        FacebookSdk.sdkInitialize(this@MyApplication)

        val ancientEntityToVoMapper = AncientEntityToVoMapper()
        val ancientVoToEntityMapper = AncientVoToEntityMapper()
        val ancientVoToFirebaseMapper = AncientVoToFirebaseMapper()
        val ancientMapper = AncientMapper(
            ancientEntityToVoMapper, ancientVoToEntityMapper,
            ancientVoToFirebaseMapper, ListMapperImpl(ancientEntityToVoMapper),
            ListMapperImpl(ancientVoToEntityMapper)
        )
        val firestoreRef = FirebaseFirestore.getInstance()
        val firebaseStorageRef = FirebaseStorage.getInstance()
        val firebaseAuthRef = FirebaseAuth.getInstance()
        val ancientFirebaseDataSource = AncientFirebaseDataSourceImpl(
            ancientMapper,
            firestoreRef,
            firebaseStorageRef,
            firebaseAuthRef
        )
        val ancientRoomDataSource = AncientRoomDataSourceImpl(
            ancientMapper
        )

        val ancientRepository = AncientRepository(ancientFirebaseDataSource, ancientRoomDataSource)

        val pagodaEntityToVoMapper = PagodaEntityToVoMapper()
        val pagodaVoToEntityMapper = PagodaVoToEntityMapper()
        val pagodaVoToFirebaseMapper = PagodaVoToFirebaseMapper()
        val pagodaMapper = PagodaMapper(
            pagodaEntityToVoMapper,pagodaVoToEntityMapper,
            pagodaVoToFirebaseMapper,ListMapperImpl(pagodaEntityToVoMapper),
        ListMapperImpl(pagodaVoToEntityMapper))
        val pagodaFirebaseDataSource = PagodaFirebaseDataSourceImpl(pagodaMapper,firestoreRef,firebaseStorageRef,firebaseAuthRef)
        val pagodaRoomDataSource = PagodaRoomDataSourceImpl(pagodaMapper)

        val pagodaRepository = PagodaRepository(pagodaFirebaseDataSource,pagodaRoomDataSource)

        MyViewModelProviderFactory.inject(
            Interactors(
                AddAncient(ancientRepository),
                AddPagoda(pagodaRepository),
                AddAllAncients(ancientRepository),
                AddAllPagodas(pagodaRepository),
                DeleteAncient(ancientRepository),
                DeletePagoda(pagodaRepository),
                DeleteAllAncients(ancientRepository),
                DeleteAllPagodas(pagodaRepository),
                GetAncient(ancientRepository),
                GetPagoda(pagodaRepository),
                GetAllAncient(ancientRepository),
                GetAllPagodas(pagodaRepository),
                UpdateAncient(ancientRepository),
                UpdatePagoda(pagodaRepository)
            )
        )

    }
}