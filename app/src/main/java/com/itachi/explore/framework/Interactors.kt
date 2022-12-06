package com.itachi.explore.framework

import com.itachi.core.interactors.*

data class Interactors(
    val addUserUseCase : AddUserUseCase,
    val addAncientUseCase: AddAncientUseCase,
    val addPagodaUseCase: AddPagodaUseCase,
    val addViewUseCase: AddViewUseCase,
    val addAllAncientsUseCase: AddAllAncientsUseCase,
    val addAllViewsUseCase: AddAllViewsUseCase,
    val deleteUserUseCase: DeleteUserUseCase,
    val deleteAncientUseCase: DeleteAncientUseCase,
    val deletePagodaUseCase: DeletePagodaUseCase,
    val deleteViewUseCase: DeleteViewUseCase,
    val deleteAllAncientsUseCase : DeleteAllAncientsUseCase,
    val deleteAllPagodasUseCase: DeleteAllPagodasUseCase,
    val deleteAllViewsUseCase: DeleteAllViewsUseCase,
    val getUserUseCase: GetUserUseCase,
    val getAncientByIdUseCase: GetAncientByIdUseCase,
    val getPagodaUseCase: GetPagodaByIdUseCase,
    val getViewByIdUseCase: GetViewByIdUseCase,
    val getAllAncientUseCase: GetAllAncientUseCase,
    val getAllPagodasUseCase: GetAllPagodasUseCase,
    val getAllViewsUseCase: GetAllViewsUseCase,
    val getAllViewsPhotoUseCase: GetAllViewsPhotoUseCase,
    val updateUserUseCase: UpdateUserUseCase,
    val updateAncientUseCase: UpdateAncientUseCase,
    val updatePagodaUseCase: UpdatePagodaUseCase,
    val updateViewUseCase: UpdateViewUseCase
) {
}