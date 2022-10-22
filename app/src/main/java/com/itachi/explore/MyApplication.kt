package com.itachi.explore

import com.itachi.core.data.AncientRepository
import android.app.Application
import androidx.room.Room
import com.facebook.FacebookSdk
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.PagodaRepository
import com.itachi.core.data.UserRepository
import com.itachi.core.data.ViewRepository
import com.itachi.core.interactors.*
import com.itachi.explore.di.applicationModule
import com.itachi.explore.framework.*
import com.itachi.explore.framework.mappers.*
import com.itachi.explore.mvvm.viewmodel.MyViewModelProviderFactory
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.third_parties.GlideLoadingService
import dagger.hilt.android.HiltAndroidApp
import me.myatminsoe.mdetect.MDetect
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ss.com.bannerslider.Slider

@HiltAndroidApp
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

        //for ancient
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

        //for pagoda
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

        //for view
        val viewEntityToVoMapper = ViewEntityToVoMapper()
        val viewVoToEntityMapper = ViewVoToEntityMapper()
        val viewVoToFirebaseMapper = ViewVoToFirebaseMapper()
        val photoEntityToVoMapper = UploadedPhotoEntityToVoMapper()
        val viewMapper = ViewMapper()
        val viewFirebaseDataSource = ViewFirebaseDataSourceImpl(viewMapper,firestoreRef,firebaseStorageRef,firebaseAuthRef)
        val viewRoomDataSource = ViewRoomDataSourceImpl(viewMapper)

        val viewRepository = ViewRepository(viewFirebaseDataSource,viewRoomDataSource)

        //for user
        val userEntityToVoMapper = UserEntityToVoMapper()
        val userVoToEntityMapper = UserVoToEntityMapper()
        val userVoToFirebaseMapper = UserVoToFirebaseMapper()
        val userMapper = UserMapper(userEntityToVoMapper,userVoToEntityMapper,userVoToFirebaseMapper)
        val userFirebaseDataSource = UserFirebaseDataSourceImpl(userMapper,firestoreRef,firebaseStorageRef,firebaseAuthRef)

        val roomDatabase = Room.databaseBuilder(this, MyDatabase::class.java, "My Database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        val firebaseAuth = FirebaseAuth.getInstance()
        val userRoomDataSource = UserRoomDataSourceImpl(firebaseAuth,userMapper,roomDatabase)
        val userRepository = UserRepository(userFirebaseDataSource,userRoomDataSource)

        MyViewModelProviderFactory.inject(
            Interactors(
                AddUser(userRepository),
                AddAncient(ancientRepository),
                AddPagoda(pagodaRepository),
                AddView(viewRepository),
                AddAllAncients(ancientRepository),
                AddAllPagodas(pagodaRepository),
                AddAllViews(viewRepository),
                DeleteUser(userRepository),
                DeleteAncient(ancientRepository),
                DeletePagoda(pagodaRepository),
                DeleteView(viewRepository),
                DeleteAllAncients(ancientRepository),
                DeleteAllPagodas(pagodaRepository),
                DeleteAllViews(viewRepository),
                GetUser(userRepository),
                GetAncient(ancientRepository),
                GetPagoda(pagodaRepository),
                GetView(viewRepository),
                GetAllAncient(ancientRepository),
                GetAllPagodas(pagodaRepository),
                GetAllViews(viewRepository),
                GetAllPhotoViews(viewRepository),
                UpdateUser(userRepository),
                UpdateAncient(ancientRepository),
                UpdatePagoda(pagodaRepository),
                UpdateView(viewRepository)
            )
        )

    }
}