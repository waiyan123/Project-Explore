package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.domain.models.ItemVO
import com.itachi.core.domain.models.toAncientVO
import com.itachi.core.domain.models.toPagodaVO
import com.itachi.core.domain.models.toViewVO
import com.itachi.core.domain.usecases.*
import com.itachi.explore.utils.ANCIENT_TYPE
import com.itachi.explore.utils.PAGODA_TYPE
import com.itachi.explore.utils.VIEW_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPagodaByIdUseCase : GetPagodaByIdUseCase,
    private val getViewByIdUseCase: GetViewByIdUseCase,
    private val getAncientByIdUseCase: GetAncientByIdUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val deletePagodaUseCase: DeletePagodaUseCase,
    private val deleteViewUseCase: DeleteViewUseCase,
    private val deleteAncientUseCase: DeleteAncientUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase
) : ViewModel(){

    val mItemVO = MutableLiveData<ItemVO>()
    lateinit var itemVO : ItemVO

    val isUploader = MutableLiveData<Boolean>()
    val successMsg = MutableLiveData<String>()
    val errorMsg = MutableLiveData<String>()
    val language = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            getLanguageUseCase()
                .buffer()
                .collect(FlowCollector{
                    language.postValue(it)
                })
        }
    }

    fun refreshDetails() {
            when(itemVO.item_type){
                PAGODA_TYPE -> {
                    viewModelScope.launch {
                        getPagodaByIdUseCase(itemVO.item_id)
                            .buffer()
                            .collect(FlowCollector{resource->
                            when(resource) {
                                is Resource.Success -> {
                                    resource.data?.let { pagodaVO->
                                        mItemVO.postValue(pagodaVO)
                                    }
                                }
                                is Resource.Error -> {

                                }
                                is Resource.Loading -> {

                                }
                            }
                        })
                    }
                }
                VIEW_TYPE -> {
                    viewModelScope.launch {
                        getViewByIdUseCase(itemVO.item_id)
                            .buffer()
                            .collect(FlowCollector{ resource->
                            when(resource) {
                                is Resource.Success -> {
                                    resource.data?.let { viewVO->
                                        mItemVO.postValue(viewVO)
                                    }
                                }
                                is Resource.Error -> {

                                }
                                is Resource.Loading -> {

                                }
                            }
                        })
                    }
                }
                ANCIENT_TYPE -> {
                    viewModelScope.launch {
                        getAncientByIdUseCase(itemVO.item_id)
                            .buffer()
                            .collect(FlowCollector{ resource->
                            when(resource) {
                                is Resource.Success -> {
                                    resource.data?.let { ancientVO->
                                        mItemVO.postValue(ancientVO)
                                    }
                                }
                                is Resource.Error -> {

                                }
                                is Resource.Loading -> {

                                }
                            }
                        })
                    }
                }

            }
    }


    fun setupItemVO(vo: ItemVO) {
        mItemVO.postValue(vo)
        itemVO = vo
        viewModelScope.launch {
            getUserUseCase()
                .buffer()
                .collect { resource->
                when(resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            isUploader.postValue(itemVO.uploader_id==it.user_id)
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
    }
    fun deleteItem() {
            when(itemVO.item_type) {
                PAGODA_TYPE->{
                    viewModelScope.launch {
                        deletePhotoUseCase(itemVO.photos,itemVO.item_id)
                            .flatMapConcat {
                                deletePagodaUseCase(itemVO.toPagodaVO())
                            }
                            .collect { resource->
                                when(resource) {
                                    is Resource.Success -> {
                                        resource.data?.let {strSuccess->
                                            successMsg.postValue(strSuccess)
                                        }
                                    }
                                    is Resource.Error -> {
                                        errorMsg.postValue(resource.message ?: "Unexpected error occur")
                                    }
                                    is Resource.Loading -> {

                                    }
                                }
                            }


                    }
                }
                VIEW_TYPE->{
                    viewModelScope.launch {
                        deletePhotoUseCase(itemVO.photos,itemVO.item_id)
                            .flatMapConcat {
                                deleteViewUseCase(itemVO.toViewVO())
                            }
                            .collect { resource->
                                when(resource) {
                                    is Resource.Success -> {
                                        resource.data?.let {strSuccess->
                                            successMsg.postValue(strSuccess)
                                        }
                                    }
                                    is Resource.Error -> {
                                        errorMsg.postValue(resource.message ?: "Unexpected error occur")
                                    }
                                    is Resource.Loading -> {

                                    }
                                }
                            }


                    }
                }
                ANCIENT_TYPE->{
                    viewModelScope.launch {
                        deletePhotoUseCase(itemVO.photos,itemVO.item_id)
                            .flatMapConcat {
                                deleteAncientUseCase(itemVO.toAncientVO())
                            }
                            .collect { resource->
                                when(resource) {
                                    is Resource.Success -> {
                                        resource.data?.let {strSuccess->
                                            successMsg.postValue(strSuccess)
                                        }
                                    }
                                    is Resource.Error -> {
                                        errorMsg.postValue(resource.message ?: "Unexpected error occur")
                                    }
                                    is Resource.Loading -> {

                                    }
                                }
                            }
                    }
                }
            }
    }
}