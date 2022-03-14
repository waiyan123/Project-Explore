//package com.itachi.explore.mvvm.model
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Transformations
//import com.itachi.core.domain.ItemVO
//import com.itachi.core.domain.PagodaVO
//import com.itachi.explore.persistence.MyDatabase
//import com.itachi.explore.persistence.entities.PagodaEntity
//import com.itachi.explore.utils.*
//import io.reactivex.Observable
//import org.koin.core.KoinComponent
//import org.koin.core.inject
//
//class PagodaModelImpl : PagodaModel, BaseModel(), KoinComponent {
//
//    private val database: MyDatabase by inject()
//
//    override fun updatePagoda(pagodaVO: PagodaVO): LiveData<String> {
//        val liveData = MutableLiveData<String>()
//        val firestorePagoda = hashMapOf(
//            ABOUT to pagodaVO.about,
//            COMMENTS to pagodaVO.comments,
//            CREATED_DATE to pagodaVO.created_date,
//            FESTIVAL_DATE to pagodaVO.festival_date,
//            IS_THERE_FESTIVAL to pagodaVO.is_there_festival,
//            ITEM_ID to pagodaVO.item_id,
//            ITEM_TYPE to pagodaVO.item_type,
//            PHOTOS to pagodaVO.photos,
//            TITLE to pagodaVO.title,
//            UPLOADER_ID to pagodaVO.uploader_id
//        )
//        firestoreRef.collection(PAGODAS)
//            .document(pagodaVO.item_id)
//            .update(firestorePagoda as Map<String, Any>)
//            .addOnSuccessListener {
//                liveData.value = "Successfully updated Pagoda"
//            }
//            .addOnFailureListener {
//
//            }
//        return liveData
//    }
//
//    override fun addPagoda(pagodaVO: PagodaVO): LiveData<String> {
//        val liveData = MutableLiveData<String>()
//        val firestorePagoda = hashMapOf(
//            ABOUT to pagodaVO.about,
//            COMMENTS to pagodaVO.comments,
//            CREATED_DATE to pagodaVO.created_date,
//            FESTIVAL_DATE to pagodaVO.festival_date,
//            IS_THERE_FESTIVAL to pagodaVO.is_there_festival,
//            ITEM_ID to pagodaVO.item_id,
//            ITEM_TYPE to pagodaVO.item_type,
//            PHOTOS to pagodaVO.photos,
//            TITLE to pagodaVO.title,
//            UPLOADER_ID to pagodaVO.uploader_id
//        )
//
//
//        firestoreRef.collection(PAGODAS)
//            .document(pagodaVO.item_id)
//            .set(firestorePagoda)
//            .addOnSuccessListener {
//                liveData.value = "Successfully added Pagoda"
//            }
//            .addOnFailureListener {
//
//            }
//        return liveData
//    }
//
//    override fun deletePagodaById(
//        pagoda: PagodaVO,
//        onSuccess: (String) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//
////        deletePhotos(pagoda.photos, pagoda.item_id)
//
//        firestoreRef.collection(PAGODAS)
//            .document(pagoda.item_id)
//            .delete()
//            .addOnSuccessListener {
//                onSuccess("Successfully deleted...")
//                database.pagodaDao().deletePagodaById(pagoda.item_id)
//            }
//            .addOnFailureListener {
//                onFailure("Failed to delete $it")
//            }
//
//    }
//
//
//    override fun getPagodaById(
//        pagodaId: String,
//        onSuccess: (Observable<PagodaVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//
//        var observable: Observable<PagodaVO>
//
//        firestoreRef.collection(PAGODAS).whereEqualTo(ITEM_ID, pagodaId)
//
//            .get()
//            .addOnSuccessListener {
//                if (it.documents.isNotEmpty()) {
//                    val pagodaVO = it.documents[0].toObject(PagodaVO::class.java)
//                    observable = Observable.just(pagodaVO)
//                    onSuccess(observable)
//                }
//            }
//            .addOnFailureListener {
//            }
//    }
//
//    override fun getPagodaListByUserId(userId: String, onSuccess: (Observable<ArrayList<ItemVO>>) -> Unit,
//                                       onFailure: (String) -> Unit) {
//        var observable: Observable<ArrayList<ItemVO>>
//        val itemList = ArrayList<ItemVO>()
//
//        firestoreRef.collection(PAGODAS).whereEqualTo(UPLOADER_ID, userId)
//
//                .get()
//                .addOnSuccessListener {snapShot->
//                    if (snapShot.documents.isNotEmpty()) {
//
//                        snapShot.forEach {
//                            val itemVO = it.toObject(ItemVO::class.java)
//                            itemList.add(itemVO)
//                        }
//
//                        observable = Observable.just(itemList)
//                        onSuccess(observable)
//                    }
//                }
//                .addOnFailureListener {
//                }
//    }
//
//    override fun getPagodasList(onFailure: (String) -> Unit)
//            : LiveData<List<PagodaEntity>> {
//        firestoreRef.collection(PAGODAS)
//            .get()
//            .addOnSuccessListener {
//                if (it.documents.size != 0) {
//                    val pagodaList = it.toObjects(PagodaEntity::class.java)
//                    database.pagodaDao().deletePagodaList()
//                    database.pagodaDao().insertPagodaList(pagodaList)
//
//                } else {
//                    onFailure("There's no item yet ")
//                }
//            }
//
//        return Transformations.distinctUntilChanged(database.pagodaDao().getPagodaList())
//    }
//
//    override fun getPagodaBanner(onSuccess: (ArrayList<String>) -> Unit) {
//        firestoreRef.collection(BANNERS).document(PAGODA_BANNER)
//            .get()
//            .addOnSuccessListener {
//                onSuccess(it.get(PAGODA_BANNER_PHOTOS) as ArrayList<String>)
//            }
//
//    }
//
//
//}