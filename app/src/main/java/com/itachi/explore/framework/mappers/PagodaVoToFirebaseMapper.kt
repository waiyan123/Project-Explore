package com.itachi.explore.framework.mappers

import com.itachi.core.domain.PagodaVO
import com.itachi.explore.utils.*
import javax.inject.Inject

class PagodaVoToFirebaseMapper @Inject constructor() : Mapper<PagodaVO,HashMap<String,Any>> {
    override fun map(input: PagodaVO?): HashMap<String,Any> {
        return if(input!=null) {
            hashMapOf(
                ABOUT to input.about,
                COMMENTS to input.comments,
                CREATED_DATE to input.created_date,
                FESTIVAL_DATE to input.festival_date,
                IS_THERE_FESTIVAL to input.is_there_festival,
                ITEM_ID to input.item_id,
                ITEM_TYPE to input.item_type,
                PHOTOS to input.photos,
                TITLE to input.title,
                UPLOADER_ID to input.uploader_id
            )
        } else hashMapOf()
    }

}