package com.itachi.explore.mvp.views

import com.itachi.core.domain.*

interface PreviewView : BaseView{

    fun showPreview(uploadedPhotoVO : UploadedPhotoVO)

    fun onClickedBackButton()

    fun onClickedDetailButtonWithPagodaVO(pagodaVO : PagodaVO)

    fun onClickedDetailButtonWithViewVO(viewVO : ViewVO)

    fun onClickedDetailButtonWithAncientVO(ancientVO: AncientVO)

    fun onClickedImageItem(url : String)

    fun showFullInfo(title : String , photos : ArrayList<PhotoVO>)

    fun showError(error : String)

    fun showUploader(userVO : UserVO)

    fun showZoomageDialog(url : String)

    fun navigateToGoogleMap(geoPoints : String)

    fun isGeoPointsValid(valid : Boolean)

}