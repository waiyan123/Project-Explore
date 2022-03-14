package com.itachi.explore.mvvm.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.utils.*
import io.reactivex.Observable

class ViewsModelImpl : BaseModel(),ViewsModel{

    override fun updateView(viewVO: ViewVO): LiveData<String> {
        val liveData = MutableLiveData<String>()
        val firestoreView = hashMapOf(
            ABOUT to viewVO.about,
            COMMENTS to viewVO.comments,
            CREATED_DATE to viewVO.created_date,
            FESTIVAL_DATE to viewVO.festival_date,
            IS_THERE_FESTIVAL to viewVO.is_there_festival,
            ITEM_ID to viewVO.item_id,
            ITEM_TYPE to viewVO.item_type,
            PHOTOS to viewVO.photos,
            TITLE to viewVO.title,
            UPLOADER_ID to viewVO.uploader_id
        )
        firestoreRef.collection(VIEWS)
            .document(viewVO.item_id)
            .update(firestoreView as Map<String, Any>)
            .addOnSuccessListener {
                liveData.value = "Successfully updated View"
            }
            .addOnFailureListener {

            }
        return liveData
    }

    override fun addView(viewVO: ViewVO): LiveData<String> {
        val liveData = MutableLiveData<String>()
        val firestoreView = hashMapOf(
            ABOUT to viewVO.about,
            COMMENTS to viewVO.comments,
            CREATED_DATE to viewVO.created_date,
            FESTIVAL_DATE to viewVO.festival_date,
            IS_THERE_FESTIVAL to viewVO.is_there_festival,
            ITEM_ID to viewVO.item_id,
            ITEM_TYPE to viewVO.item_type,
            PHOTOS to viewVO.photos,
            TITLE to viewVO.title,
            UPLOADER_ID to viewVO.uploader_id
        )

        firestoreRef.collection(VIEWS)
            .document(viewVO.item_id)
            .set(firestoreView)
            .addOnSuccessListener {
                liveData.value = "Successfully added View"
            }
            .addOnFailureListener {

            }
        return liveData
    }

    override fun getPhotoViews(
        onSuccess: (uploadedPhotoList: ArrayList<UploadedPhotoVO>) -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        firestoreRef.collection(UPLOADED_PHOTO)
            .get()
            .addOnSuccessListener {
                if (it.documents.size != 0) {

                    val viewPhotoList = it.toObjects(UploadedPhotoVO::class.java)
                    onSuccess(viewPhotoList as ArrayList<UploadedPhotoVO>)
                }
                else {
                    onFailure("There's no item yet ")
                }
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage.toString())
            }
    }

    override fun getViewsList(onSuccess: (ArrayList<ViewVO>) -> Unit, onFailure: (String) -> Unit) {
        firestoreRef.collection(VIEWS)
                .get()
                .addOnSuccessListener {
                    if (it.documents.size != 0) {

                        val viewsList = it.toObjects(ViewVO::class.java)
                        onSuccess(viewsList as ArrayList<ViewVO>)
                    }
                    else {
                        onFailure("There's no item yet ")
                    }
                }
    }

    override fun getViewById(
        viewId: String,
        onSuccess: (Observable<ViewVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        var observable : Observable<ViewVO>

        firestoreRef.collection(VIEWS).whereEqualTo(ITEM_ID, viewId)

            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    val viewVO = it.documents[0].toObject(ViewVO::class.java)
                    observable = Observable.just(viewVO)
                    onSuccess(observable)
                }
            }
            .addOnFailureListener {

            }
    }

    override fun getViewsListByUserId(userId: String, onSuccess: (Observable<ArrayList<ItemVO>>) -> Unit, onFailure: (String) -> Unit) {
        var observable : Observable<ArrayList<ItemVO>>
        val itemList = ArrayList<ItemVO>()

        firestoreRef.collection(VIEWS).whereEqualTo(UPLOADER_ID, userId)

                .get()
                .addOnSuccessListener {snapShot->
                    if (snapShot.documents.isNotEmpty()) {

                        snapShot.forEach {
                            val itemVO = it.toObject(ItemVO::class.java)
                            itemList.add(itemVO)
                        }

                        observable = Observable.just(itemList)
                        onSuccess(observable)
                    }
                }
                .addOnFailureListener {
                    onFailure(it.toString())
                }
    }

    override fun deleteViewById(
        view: ViewVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
//        deletePhotos(view.photos,view.item_id)

        firestoreRef.collection(VIEWS)
            .document(view.item_id)
            .delete()
            .addOnSuccessListener {
                onSuccess("Successfully deleted...")
            }
            .addOnFailureListener {
                onFailure("Failed to delete $it")
            }

    }

}