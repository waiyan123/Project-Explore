package com.itachi.explore.utils

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import androidx.exifinterface.media.ExifInterface
import io.reactivex.Observable
import org.koin.core.KoinComponent
import java.io.ByteArrayOutputStream

object Util {

    suspend fun compressImage(
        filePath: List<String>,
        context: Context,
        quality: Int = 30
    ): ArrayList<ByteArray> {
        val list = ArrayList<ByteArray>()
        filePath.forEach {
            val bmp = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(it))
            val baos = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, quality, baos)
            list.add(baos.toByteArray())
        }
        return list
    }

    suspend fun getRealPathFromUrl(
        context: Context,
        url: String?
    ): String? {
        val contentUri = Uri.parse(url)
        var cursor: Cursor? = null
        return try {
            val proj =
                arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
            val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        } finally {
            cursor?.close()
        }
    }

    suspend fun getGeoPointsFromImageList(
        context: Context,
        imageList: List<String>
    ): ArrayList<String> {
        val geoPointsList = ArrayList<String>()
        imageList.forEach {
            geoPointsList.add(getGeoPointFromImage(getRealPathFromUrl(context, it)!!))
        }
        return geoPointsList
    }

    suspend fun getGeoPointFromImage(filePath: String): String {
        val exif = ExifInterface(filePath)

        val LATITUDE =
            exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
        val LATITUDE_REF =
            exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)
        val LONGITUDE =
            exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
        val LONGITUDE_REF =
            exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)

        var latitude: Float = 0f
        var longitude: Float = 0f

        if ((LATITUDE != null)
            && (LATITUDE_REF != null)
            && (LONGITUDE != null)
            && (LONGITUDE_REF != null)
        ) {

            latitude = if (LATITUDE_REF == "N") {
                convertToDegree(LATITUDE)
            } else {
                0 - convertToDegree(LATITUDE)
            }

            longitude = if (LONGITUDE_REF == "E") {
                convertToDegree(LONGITUDE)
            } else {
                0 - convertToDegree(LONGITUDE)
            }
        }
        return "$latitude,$longitude"
    }

    private suspend fun convertToDegree(stringDMS: String): Float {
        var result: Float? = null
        val dms = stringDMS.split(",".toRegex(), 3).toTypedArray()
        val stringD =
            dms[0].split("/".toRegex(), 2).toTypedArray()
        val d0: Double = stringD[0].toDouble()
        val d1: Double = stringD[1].toDouble()
        val floatD = d0 / d1
        val stringM =
            dms[1].split("/".toRegex(), 2).toTypedArray()
        val m0: Double = stringM[0].toDouble()
        val m1: Double = stringM[1].toDouble()
        val floatM = m0 / m1
        val stringS =
            dms[2].split("/".toRegex(), 2).toTypedArray()
        val s0: Double = stringS[0].toDouble()
        val s1: Double = stringS[1].toDouble()
        val floatS = s0 / s1
        result = (floatD + floatM / 60 + (floatS / 3600)).toFloat()
        return result
    }
}
