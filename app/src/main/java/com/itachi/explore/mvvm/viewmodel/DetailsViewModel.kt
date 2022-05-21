package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.framework.Interactors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailsViewModel(interactors: Interactors) : AppViewmodel(interactors){

    val mItemVO = MutableLiveData<ItemVO>()
    val isUploader = MutableLiveData<Boolean>()
    val errorMsg = MutableLiveData<String>()

    fun setupItemVO(itemVO: ItemVO) {
        mItemVO.postValue(itemVO)
        GlobalScope.launch {
            interactors.getUser.fromRoom(
                {userVO->
                    isUploader.postValue(itemVO.uploader_id==userVO.user_id)
                },
                {roomError->
                    errorMsg.postValue(roomError)
                    GlobalScope.launch {
                        interactors.getUser.fromFirebase(
                            {userVO->
                                isUploader.postValue(itemVO.uploader_id==userVO.user_id)
                            },
                            {firebaseError->
                                errorMsg.postValue(firebaseError)
                            }
                        )
                    }
                }
            )
        }
    }


}