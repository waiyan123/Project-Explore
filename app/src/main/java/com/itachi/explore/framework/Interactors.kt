package com.itachi.explore.framework

import com.itachi.core.interactors.*

data class Interactors(
    val addAncient: AddAncient,
    val addPagoda: AddPagoda,
    val addAllAncients: AddAllAncients,
    val addAllPagodas: AddAllPagodas,
    val deleteAncient: DeleteAncient,
    val deletePagoda: DeletePagoda,
    val deleteAllAncients : DeleteAllAncients,
    val deleteAllPagodas: DeleteAllPagodas,
    val getAncient: GetAncient,
    val getPagoda: GetPagoda,
    val getAllAncient: GetAllAncient,
    val getAllPagodas: GetAllPagodas,
    val updateAncient: UpdateAncient,
    val updatePagoda: UpdatePagoda
) {
}