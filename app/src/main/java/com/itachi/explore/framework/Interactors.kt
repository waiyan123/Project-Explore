package com.itachi.explore.framework

import com.itachi.core.interactors.*

data class Interactors(
    val addUser : AddUser,
    val addAncient: AddAncient,
    val addPagoda: AddPagoda,
    val addView: AddView,
    val addAllAncients: AddAllAncients,
    val addAllPagodas: AddAllPagodas,
    val addAllViews: AddAllViews,
    val deleteUser: DeleteUser,
    val deleteAncient: DeleteAncient,
    val deletePagoda: DeletePagoda,
    val deleteView: DeleteView,
    val deleteAllAncients : DeleteAllAncients,
    val deleteAllPagodas: DeleteAllPagodas,
    val deleteAllViews: DeleteAllViews,
    val getUser: GetUser,
    val getAncient: GetAncient,
    val getPagoda: GetPagoda,
    val getView: GetView,
    val getAllAncient: GetAllAncient,
    val getAllPagodas: GetAllPagodas,
    val getAllViews: GetAllViews,
    val getAllPhotoViews: GetAllPhotoViews,
    val updateUser: UpdateUser,
    val updateAncient: UpdateAncient,
    val updatePagoda: UpdatePagoda,
    val updateView: UpdateView
) {
}