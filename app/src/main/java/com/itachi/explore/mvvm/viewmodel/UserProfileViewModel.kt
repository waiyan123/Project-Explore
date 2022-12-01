package com.itachi.explore.mvvm.viewmodel

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.domain.UserVO
import com.itachi.core.interactors.GetUser
import com.itachi.explore.mvvm.model.UserProfileUploadDialogModel
import com.itachi.explore.utils.REQUEST_BACKGROUND_PIC
import com.itachi.explore.utils.REQUEST_PROFILE_PIC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUser : GetUser
) : ViewModel(){

    var mUserVO = UserVO()
    val mutableUploadProfileModel = MutableLiveData<UserProfileUploadDialogModel>()

    val editable = MutableLiveData<Boolean>()
    val onEdit = MutableLiveData<Boolean>()
    private var imagePath = ""

    init {

    }

    fun getUser(userVO : UserVO?) : LiveData<UserVO>{
        val liveData = MutableLiveData<UserVO>()
        viewModelScope.launch {
            getUser(userVO?.user_id).collect {resource->
                when(resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            liveData.postValue(it)
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
        return liveData

    }

    fun onClickedEditButton() {
        onEdit.postValue(true)
    }

    fun onClickedDoneButton() {
        onEdit.postValue(false)
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
                imagePath = mUserVO.profile_pic.url,
                clickAction = "Pick up"
            )
        )
    }
    fun onClickedUserBackgroundPic() {
        mutableUploadProfileModel.postValue(
            UserProfileUploadDialogModel(
                title = "Background",
                imagePath = mUserVO.background_pic.url,
                clickAction = "Pick up"
            )
        )
    }

    fun onClickedChangeButton(dialogModel : UserProfileUploadDialogModel) {

    }

    fun addItem(userVO : UserVO){

    }
}