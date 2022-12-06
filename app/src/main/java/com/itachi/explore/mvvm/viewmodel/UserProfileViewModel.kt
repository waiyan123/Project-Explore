package com.itachi.explore.mvvm.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UserVO
import com.itachi.core.interactors.DeletePhotoUseCase
import com.itachi.core.interactors.GetUserUseCase
import com.itachi.core.interactors.UpdateUserUseCase
import com.itachi.core.interactors.UploadPhotosUseCase
import com.itachi.explore.mvvm.model.UserProfileUploadDialogModel
import com.itachi.explore.utils.REQUEST_BACKGROUND_PIC
import com.itachi.explore.utils.REQUEST_PROFILE_PIC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserUseCase : GetUserUseCase,
    private val updateUserUseCase : UpdateUserUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase,
    private val uploadPhotosUseCase : UploadPhotosUseCase
) : ViewModel(){

    var mUserVO = UserVO()
    val userVOLiveData = MutableLiveData<UserVO>()
    val mutableUploadProfileModel = MutableLiveData<UserProfileUploadDialogModel>()
    val profilePicLiveData = MutableLiveData<PhotoVO>()
    val backgroundPicLiveData = MutableLiveData<PhotoVO>()
    val progressLoadingLiveData = MutableLiveData<Boolean>()
    val responseMessageLiveData = MutableLiveData<String>()

    val editable = MutableLiveData<Boolean>()
    val onEdit = MutableLiveData<Boolean>()

    init {

    }

    fun getUser(userVO : UserVO?){
        viewModelScope.launch {
            getUserUseCase(userVO?.user_id).collect { resource->
                when(resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            userVOLiveData.postValue(it)
                            mUserVO = it
                        }
                    }
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
        if(userVO==null) editable.postValue(true)
    }

    fun onClickedEditButton() {
        onEdit.postValue(true)
    }

    fun onClickedDoneButton() {
        progressLoadingLiveData.postValue(true)
        viewModelScope.launch {
            updateUserUseCase(mUserVO)
                .collect {resource->
                    when(resource){
                        is Resource.Success -> {
                            resource.data?.let {
                                userVOLiveData.postValue(it)
                            }
                        }
                        is Resource.Error -> {
                            resource.message?.let {
                                responseMessageLiveData.postValue(it)
                            }

                        }
                        is Resource.Loading -> TODO()
                    }
                    onEdit.postValue(false)
                    progressLoadingLiveData.postValue(false)
                }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PROFILE_PIC) {
                data?.let {
                    mutableUploadProfileModel.postValue(
                        UserProfileUploadDialogModel(
                            title = "Profile",
                            imagePath = it.data.toString(),
                            clickAction = "Change"
                        )
                    )
                }
            }
            else if (requestCode == REQUEST_BACKGROUND_PIC) {
                data?.let {
                    mutableUploadProfileModel.postValue(
                        UserProfileUploadDialogModel(
                            title = "Background",
                            imagePath = it.data.toString(),
                            clickAction = "Change"
                        )
                    )
                }
            }

        }
    }

    fun onClickedUserProfilePic() {
        mutableUploadProfileModel.postValue(
            UserProfileUploadDialogModel(
                title = "Profile",
                imagePath = mUserVO.profile_pic.url ?: "",
                clickAction = "Pick up"
            )
        )
    }
    fun onClickedUserBackgroundPic() {
        mutableUploadProfileModel.postValue(
            UserProfileUploadDialogModel(
                title = "Background",
                imagePath = mUserVO.background_pic.url ?: "",
                clickAction = "Pick up"
            )
        )
    }

    fun onClickedChangeButton(dialogModel : UserProfileUploadDialogModel) {
        progressLoadingLiveData.postValue(true)
        val photo = if(dialogModel.title=="Profile") {
            mUserVO.profile_pic
        } else mUserVO.background_pic

        viewModelScope.launch {
            deletePhotoUseCase(listOf(photo),photo.id)
                .flatMapConcat {
                    uploadPhotosUseCase(listOf(dialogModel.imagePath))
                }
                .collect {resource->
                    when(resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                if(dialogModel.title == "Profile") {
                                    mUserVO.profile_pic = it[0]
                                    profilePicLiveData.postValue(it[0])

                                }else {
                                    mUserVO.background_pic = it[0]
                                    backgroundPicLiveData.postValue(it[0])
                                }

                            }
                            responseMessageLiveData.postValue("Successfully uploaded!")
                        }
                        is Resource.Error -> {
                            responseMessageLiveData.postValue(resource.message ?: "Error occur in uploading.")
                        }
                        is Resource.Loading -> {

                        }
                    }
                    progressLoadingLiveData.postValue(false)
                }
        }
    }

    fun addItem(userVO : UserVO){

    }
}