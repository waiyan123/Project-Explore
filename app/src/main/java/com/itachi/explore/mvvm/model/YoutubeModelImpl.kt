package com.itachi.explore.mvvm.model

import com.itachi.explore.utils.THUMBNAILS
import com.itachi.explore.utils.VIDEOS
import com.itachi.explore.utils.YOUTUBE_VIDEOS

class YoutubeModelImpl : YoutubeModel,BaseModel(){

    override fun getAncientList(
        onSuccess: (ArrayList<String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestoreRef.collection(YOUTUBE_VIDEOS).document(VIDEOS)
            .get()
            .addOnSuccessListener {
                onSuccess(it.get(THUMBNAILS) as ArrayList<String>)
            }

    }
}