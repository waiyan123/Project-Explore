//package com.itachi.explore.mvvm.model
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.itachi.core.domain.AncientVO
//import com.itachi.core.domain.ItemVO
//import com.itachi.explore.utils.*
//import io.reactivex.Observable
//
//class AncientModelImpl : AncientModel,BaseModel(){
//
//    override fun updateAncient(ancientVO: AncientVO): LiveData<String> {
//        val liveData = MutableLiveData<String>()
//        val firestoreAncient = hashMapOf(
//            ABOUT to ancientVO.about,
//            COMMENTS to ancientVO.comments,
//            CREATED_DATE to ancientVO.created_date,
//            FESTIVAL_DATE to ancientVO.festival_date,
//            IS_THERE_FESTIVAL to ancientVO.is_there_festival,
//            ITEM_ID to ancientVO.item_id,
//            ITEM_TYPE to ancientVO.item_type,
//            PHOTOS to ancientVO.photos,
//            TITLE to ancientVO.title,
//            UPLOADER_ID to ancientVO.uploader_id
//        )
//        firestoreRef.collection(ANCIENTS)
//            .document(ancientVO.item_id)
//            .update(firestoreAncient)
//            .addOnSuccessListener {
//                liveData.value = "Successfully updated Ancient"
//            }
//            .addOnFailureListener {
//
//            }
//        return liveData
//    }
//
//    override fun addAncient(ancientVO: AncientVO): LiveData<String> {
//        val liveData = MutableLiveData<String>()
//        val firestoreAncient = hashMapOf(
//            ABOUT to ancientVO.about,
//            COMMENTS to ancientVO.comments,
//            CREATED_DATE to ancientVO.created_date,
//            FESTIVAL_DATE to ancientVO.festival_date,
//            IS_THERE_FESTIVAL to ancientVO.is_there_festival,
//            ITEM_ID to ancientVO.item_id,
//            ITEM_TYPE to ancientVO.item_type,
//            PHOTOS to ancientVO.photos,
//            TITLE to ancientVO.title,
//            UPLOADER_ID to ancientVO.uploader_id
//        )
//
//        firestoreRef.collection(ANCIENTS)
//            .document(ancientVO.item_id)
//            .set(firestoreAncient)
//            .addOnSuccessListener {
//                liveData.value = "Successfully added Ancient"
//            }
//            .addOnFailureListener {
//
//            }
//        return liveData
//    }
//
//    override fun getAncientBackground(onSuccess: (String) -> Unit,onFailure: (String) -> Unit) {
//        firestoreRef.collection(BANNERS).document(ANCIENT_BACKGROUND)
//            .get()
//            .addOnSuccessListener {
//                onSuccess(it.get(ANCIENT_BACKGROUND_PHOTO) as String)
//            }
//            .addOnFailureListener{
//                onFailure(it.localizedMessage)
//            }
//    }
//
//    override fun getAncientList(
//        onSuccess: (ArrayList<AncientVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        firestoreRef.collection(ANCIENTS)
//            .get()
//            .addOnSuccessListener {
//                if (it.documents.size != 0) {
//
//                    val ancientList = it.toObjects(AncientVO::class.java)
//                    onSuccess(ancientList as ArrayList<AncientVO>)
//                }
//                else {
//                    onFailure("There's no item yet ")
//                }
//            }
//    }
//
//    override fun getAncientById(
//        ancientId: String,
//        onSuccess: (Observable<AncientVO>) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        var observable : Observable<AncientVO>
//
//        firestoreRef.collection(ANCIENTS).whereEqualTo(ITEM_ID, ancientId)
//
//            .get()
//            .addOnSuccessListener {
//                if (it.documents.isNotEmpty()) {
//                    val ancientVO = it.documents[0].toObject(AncientVO::class.java)
//                    observable = Observable.just(ancientVO)
//                    onSuccess(observable)
//                }
//            }
//            .addOnFailureListener {
//            }
//    }
//
//    override fun getAncientsListByUserId(userId: String, onSuccess: (Observable<ArrayList<ItemVO>>) -> Unit,
//                                         onFailure: (String) -> Unit) {
//        var observable : Observable<ArrayList<ItemVO>>
//        val itemList = ArrayList<ItemVO>()
//
//        firestoreRef.collection(ANCIENTS).whereEqualTo(UPLOADER_ID, userId)
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
//                    onFailure(it.toString())
//                }
//    }
//
//    override fun deleteAncientById(
//        ancient: AncientVO,
//        onSuccess: (String) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        deletePhotos(ancient.photos,ancient.item_id)
//
//        firestoreRef.collection(ANCIENTS)
//            .document(ancient.item_id)
//            .delete()
//            .addOnSuccessListener {
//                onSuccess("Successfully deleted...")
//            }
//            .addOnFailureListener {
//                onFailure("Failed to delete $it")
//            }
//    }
//}