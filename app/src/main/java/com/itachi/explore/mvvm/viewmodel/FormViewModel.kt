package com.itachi.explore.mvvm.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.domain.*
import com.itachi.core.common.Resource
import com.itachi.core.interactors.*
import com.itachi.explore.utils.*
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.MimeType
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import com.sangcomz.fishbun.define.Define
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@OptIn(FlowPreview::class)
@HiltViewModel
class FormViewModel @Inject constructor(
    private val getUser: GetUser,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val uploadPhotosUseCase: UploadPhotosUseCase,
    private val uploadPhotoUrlUseCase: UploadPhotoUrlUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase,
    private val addPagodaUseCase: AddPagodaUseCase,
    private val addViewUseCase: AddViewUseCase,
    private val addAncientUseCase: AddAncientUseCase,
    private val updatePagodaUseCase: UpdatePagodaUseCase,
    private val updateViewUseCase: UpdateViewUseCase,
    private val updateAncientUseCase: UpdateAncientUseCase

) : ViewModel() {

    private val uris = ArrayList<Uri>()
    private val mPickupImages = ArrayList<String>()
    private lateinit var mUserVO: UserVO
    private val itemId = UUID.randomUUID().toString()
    private lateinit var mItemVO: ItemVO

    val images = MutableLiveData<List<Uri>>()
    val progressLoading = MutableLiveData<Boolean>()
    val successMsg = MutableLiveData<String>()
    val errorMsg = MutableLiveData<String>()
    val pickupImageError = MutableLiveData<Boolean>()
    var formType = "Add"
    val language = MutableLiveData<String>()
    val mItemVOLiveData = MutableLiveData<ItemVO>()

    init {
        viewModelScope.launch {
            getUser().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            mUserVO = it
                        }
                    }
                    is Resource.Error -> {
                        resource.message?.let {
                            errorMsg.postValue(it)
                        }
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
        checkLanguage()
    }

    private fun checkLanguage() {
        viewModelScope.launch {
            getLanguageUseCase().collect {

                language.postValue(it)
            }
        }
    }

    fun onEditItemVO(itemVO: ItemVO) {
        mItemVOLiveData.postValue(itemVO)
        mItemVO = itemVO

        val uri = itemVO.photos.map {
            Uri.parse(it.url)
        }
        images.postValue(ArrayList(uri))
        formType = "Update"

    }

    fun checkValidate(text: String = ""): Boolean {
        return text.isNotEmpty() && text.isNotBlank()
    }

    fun chooseMultiplePhotos(context: Context) {

        FishBun.with(context as Activity)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(3)
            .setActionBarColor(Color.parseColor("#795548"), Color.parseColor("#5D4037"), false)
            .setActionBarTitleColor(Color.parseColor("#ffffff"))
            .setAlbumSpanCount(2, 3)
            .setButtonInAlbumActivity(false)
            .setCamera(true)
            .setAllViewTitle("All")
            .setMenuAllDoneText("All Done")
            .setActionBarTitle("Choose Images ...")
            .textOnNothingSelected("Please select three !")
            .exceptMimeType(listOf(MimeType.GIF))
            .startAlbum()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Define.ALBUM_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val path = data?.getParcelableArrayListExtra<Uri>(Define.INTENT_PATH)
                    if (path != null) {
                        mPickupImages.clear()
                        uris.clear()
                        for (image in path) {
                            mPickupImages.add(image.toString())
                            uris.add(image)
                        }
                        images.postValue(uris)
                        pickupImageError.postValue(false)
                    }
                }
            }
        }
    }

    fun addItem(
        name: String,
        created: String,
        festival: String,
        about: String,
        type: String
    ) {
        progressLoading.postValue(true)
        when (type) {
            PAGODA_TYPE -> {
                addPagoda(name, created, festival, about, type)
            }

            VIEW_TYPE -> {
                addView(name, created, festival, about, type)
            }

            ANCIENT_TYPE -> {
                addAncient(name, created, festival, about, type)
            }

            FOOD_TYPE -> {

            }

            ACCESSORY_TYPE -> {

            }

            SUPPORT_TYPE -> {

            }
        }
    }

    fun updateItem(name: String, created: String, festival: String, about: String) {
        progressLoading.postValue(true)
        var mAbout = about
        var mName = name
        var mCreatedDate = created
        var mFestivalDate = festival

        if (!MDetect.isUnicode()) {
            mAbout = Rabbit.zg2uni(about)
            mName = Rabbit.zg2uni(name)
            mCreatedDate = Rabbit.zg2uni(created)
            mFestivalDate = Rabbit.zg2uni(festival)
        }
        mItemVO.title = mName
        mItemVO.created_date = mCreatedDate
        mItemVO.festival_date = mFestivalDate
        mItemVO.about = mAbout

        when (mItemVO.item_type) {
            PAGODA_TYPE -> {
                val pagodaVO = mItemVO.toPagodaVO()
                viewModelScope.launch {
                    if (mPickupImages.size > 0) {
                        deletePhotoUseCase(pagodaVO.photos, pagodaVO.item_id)
                            .flatMapConcat {
                                uploadPhotosUseCase(mPickupImages)
                            }
                            .collect {resource->
                                resource.data?.let { photoList ->
                                    photoList.forEach {
                                        val uploadedPhotoVO = UploadedPhotoVO(it.url,mUserVO.user_id,pagodaVO.item_id,
                                            pagodaVO.item_type,it.geo_points)
                                        uploadPhotoUrlUseCase(uploadedPhotoVO)
                                    }
                                    pagodaVO.photos = photoList
                                    updatePagoda(pagodaVO)
                                }
                            }
                    } else {
                        updatePagoda(pagodaVO)
                    }
                }
            }
            VIEW_TYPE -> {
                val viewVO = mItemVO.toViewVO()
                viewModelScope.launch {
                    if (mPickupImages.size > 0) {
                        deletePhotoUseCase(viewVO.photos, viewVO.item_id)
                            .flatMapConcat {
                                uploadPhotosUseCase(mPickupImages)
                            }
                            .collect {resource->
                                resource.data?.let { photoList ->
                                    photoList.forEach {
                                        val uploadedPhotoVO = UploadedPhotoVO(it.url,mUserVO.user_id,viewVO.item_id,
                                            viewVO.item_type,it.geo_points)
                                        uploadPhotoUrlUseCase(uploadedPhotoVO)
                                    }
                                    viewVO.photos = photoList
                                    updateView(viewVO)
                                }
                            }
                    } else {
                        updateView(viewVO)
                    }
                }
            }

            ANCIENT_TYPE -> {
                val ancientVO = mItemVO.toAncientVO()
                viewModelScope.launch {
                    if (mPickupImages.size > 0) {
                        deletePhotoUseCase(ancientVO.photos, ancientVO.item_id)
                            .flatMapConcat {
                                uploadPhotosUseCase(mPickupImages)
                            }
                            .collect {resource->
                                resource.data?.let { photoList ->
                                    photoList.forEach {
                                        val uploadedPhotoVO = UploadedPhotoVO(it.url,mUserVO.user_id,ancientVO.item_id,
                                            ancientVO.item_type,it.geo_points)
                                        uploadPhotoUrlUseCase(uploadedPhotoVO)
                                    }
                                    ancientVO.photos = photoList
                                    updateAncient(ancientVO)
                                }
                            }
                    } else {
                        updateAncient(ancientVO)
                    }
                }
            }

            FOOD_TYPE -> {

            }

            ACCESSORY_TYPE -> {

            }

            SUPPORT_TYPE -> {

            }
        }
    }

    private suspend fun updatePagoda(pagodaVO: PagodaVO) {
        updatePagodaUseCase(pagodaVO)
            .collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        progressLoading.postValue(false)
                        successMsg.postValue(resource.data)
                    }
                    is Resource.Error -> {
                        progressLoading.postValue(false)
                        errorMsg.postValue(resource.message)
                    }
                }
            }

    }

    private suspend fun updateView(viewVO: ViewVO) {
        updateViewUseCase(viewVO)
            .collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        progressLoading.postValue(false)
                        successMsg.postValue(resource.data)
                    }
                    is Resource.Error -> {
                        progressLoading.postValue(false)
                        errorMsg.postValue(resource.message)
                    }

                }

            }
    }

    private fun updateAncient(ancientVO: AncientVO) {
        viewModelScope.launch {
            updateAncientUseCase(ancientVO)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            progressLoading.postValue(false)
                            successMsg.postValue(resource.data)
                        }
                        is Resource.Error -> {
                            progressLoading.postValue(false)
                            errorMsg.postValue(resource.message)
                        }

                    }
                }
        }
    }

    @SuppressLint("CheckResult")
    private fun addPagoda(
        name: String,
        created: String,
        festival: String,
        about: String,
        type: String
    ) {
        val isFestival = festival.isNotEmpty()

        val pagodaVO = PagodaVO(about, emptyList(), created, festival, isFestival, itemId, type, emptyList(), name, mUserVO.user_id)

        if (!MDetect.isUnicode()) {
            val mAbout = Rabbit.zg2uni(about)
            val mName = Rabbit.zg2uni(name)
            val mCreatedDate = Rabbit.zg2uni(created)
            val mFestivalDate = Rabbit.zg2uni(festival)
            pagodaVO.about = mAbout
            pagodaVO.title = mName
            pagodaVO.created_date = mCreatedDate
            pagodaVO.festival_date = mFestivalDate
        }

        progressLoading.postValue(true)
        if (mPickupImages.size > 0) {
            pickupImageError.postValue(false)
            viewModelScope.launch {
                uploadPhotosUseCase(mPickupImages)
                    .collect {resource->
                    resource.data?.let { photoList ->
                        photoList.forEach {
                            val uploadedPhotoVO = UploadedPhotoVO(it.url,mUserVO.user_id,pagodaVO.item_id,
                            pagodaVO.item_type,it.geo_points)
                            uploadPhotoUrlUseCase(uploadedPhotoVO)
                        }
                        pagodaVO.photos = photoList
                        addPagodaUseCase(pagodaVO)
                            .collect { resource ->
                                when (resource) {
                                    is Resource.Success -> {
                                        successMsg.postValue(resource.data)
                                        progressLoading.postValue(false)
                                    }
                                    is Resource.Error -> {
                                        errorMsg.postValue(resource.message)
                                        progressLoading.postValue(false)
                                    }

                                }
                            }
                    }
                }
            }

        } else {
            pickupImageError.postValue(true)
        }
    }

    private fun addView(
        name: String,
        created: String,
        festival: String,
        about: String,
        type: String
    ) {
        var isFestival = false
        if (festival.isNotEmpty()) isFestival = true
        val viewVO = ViewVO(
            about,
            emptyList(),
            created, festival,
            isFestival,
            itemId,
            type,
            emptyList(),
            name,
            mUserVO.user_id
        )

        if (!MDetect.isUnicode()) {
            val mAbout = Rabbit.zg2uni(about)
            val mName = Rabbit.zg2uni(name)
            val mCreatedDate = Rabbit.zg2uni(created)
            val mFestivalDate = Rabbit.zg2uni(festival)
            viewVO.about = mAbout
            viewVO.title = mName
            viewVO.created_date = mCreatedDate
            viewVO.festival_date = mFestivalDate
        }

        progressLoading.postValue(true)
        if (mPickupImages.size > 0) {
            pickupImageError.postValue(false)
            viewModelScope.launch {
                uploadPhotosUseCase(mPickupImages).collect {resource->
                    resource.data?.let { photoList ->
                        photoList.forEach {
                            val uploadedPhotoVO = UploadedPhotoVO(it.url,mUserVO.user_id,viewVO.item_id,
                                viewVO.item_type,it.geo_points)
                            uploadPhotoUrlUseCase(uploadedPhotoVO)
                        }
                        viewVO.photos = photoList
                        addViewUseCase(viewVO)
                            .collect { resource ->
                                when (resource) {
                                    is Resource.Success -> {
                                        successMsg.postValue(resource.data)
                                        progressLoading.postValue(false)
                                    }
                                    is Resource.Error -> {
                                        errorMsg.postValue(resource.message)
                                        progressLoading.postValue(false)
                                    }

                                }
                            }
                    }

                }
            }

        } else {
            pickupImageError.postValue(true)
        }
    }

    private fun addAncient(
        name: String,
        created: String,
        festival: String,
        about: String,
        type: String
    ) {
        var isFestival = false
        if (festival.isNotEmpty()) isFestival = true
        val ancientVO = AncientVO(about, emptyList(), created, festival, isFestival, itemId, type, emptyList(), name, mUserVO.user_id)

        if (!MDetect.isUnicode()) {
            val mAbout = Rabbit.zg2uni(about)
            val mName = Rabbit.zg2uni(name)
            val mCreatedDate = Rabbit.zg2uni(created)
            val mFestivalDate = Rabbit.zg2uni(festival)
            ancientVO.about = mAbout
            ancientVO.title = mName
            ancientVO.created_date = mCreatedDate
            ancientVO.festival_date = mFestivalDate
        }

        progressLoading.postValue(true)
        if (mPickupImages.size > 0) {
            pickupImageError.postValue(false)
            progressLoading.postValue(true)
            viewModelScope.launch {
                uploadPhotosUseCase(mPickupImages).collect {resource->
                    resource.data?.let { photoList ->
                        photoList.forEach {
                            val uploadedPhotoVO = UploadedPhotoVO(it.url,mUserVO.user_id,ancientVO.item_id,
                                ancientVO.item_type,it.geo_points)
                            uploadPhotoUrlUseCase(uploadedPhotoVO)
                        }
                        ancientVO.photos = photoList
                        addAncientUseCase(ancientVO)
                            .collect { resource ->
                                when (resource) {
                                    is Resource.Success -> {
                                        successMsg.postValue(resource.data)
                                        progressLoading.postValue(false)
                                    }
                                    is Resource.Error -> {
                                        errorMsg.postValue(resource.message)
                                        progressLoading.postValue(false)
                                    }
                                }
                            }
                    }

                }
            }

        } else {
            pickupImageError.postValue(true)
        }
    }
}