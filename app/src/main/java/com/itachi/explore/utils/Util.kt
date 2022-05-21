package com.itachi.explore.utils

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.EditText
import androidx.exifinterface.media.ExifInterface
import io.reactivex.Observable
import org.koin.core.KoinComponent
import java.io.ByteArrayOutputStream


class Util {

    companion object : KoinComponent {

        fun checkEditTextValidattion(et: EditText): Boolean {
            var valid = true
            if (et.text.toString() == "" || et.text.toString() == null) {
                valid = false
            }
            return valid
        }

        fun compressImage(
            filePath: ArrayList<String>,
            context: Context,
            quality: Int
        ): Observable<ArrayList<ByteArray>> {
            val list = ArrayList<ByteArray>()
            filePath.forEach {
                val bmp = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(it))
                val baos = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.JPEG, quality, baos)
                list.add(baos.toByteArray())
            }
            return Observable.just(list)
        }

        fun getRealPathFromUrl(
            context: Context,
            url: String?
        ): String? {
            val contentUri = Uri.parse(url)
            var cursor: Cursor? = null
            return try {
                val proj =
                    arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                cursor.getString(column_index)
            } finally {
                cursor?.close()
            }
        }

        fun getGeoPointsFromImageList(context: Context,imageList : ArrayList<String>) : Observable<ArrayList<String>>{
            var geoPointsList = ArrayList<String>()
            imageList.forEach {
                geoPointsList.add(getGeoPointFromImage(getRealPathFromUrl(context,it)!!))
            }
            return Observable.just(geoPointsList)
        }

        fun getGeoPointFromImage(filePath: String) : String{
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

                if (LATITUDE_REF.equals("N")) {
                    latitude = convertToDegree(LATITUDE)!!
                } else {
                    latitude = 0 - convertToDegree(LATITUDE)!!
                }

                if (LONGITUDE_REF.equals("E")) {
                    longitude = convertToDegree(LONGITUDE)!!
                } else {
                    longitude = 0 - convertToDegree(LONGITUDE)!!
                }
            }
            return "$latitude,$longitude"
        }

        private fun convertToDegree(stringDMS: String): Float? {
            var result: Float? = null
            val DMS = stringDMS.split(",".toRegex(), 3).toTypedArray()
            val stringD =
                DMS[0].split("/".toRegex(), 2).toTypedArray()
            val D0: Double = stringD[0].toDouble()
            val D1: Double = stringD[1].toDouble()
            val FloatD = D0 / D1
            val stringM =
                DMS[1].split("/".toRegex(), 2).toTypedArray()
            val M0: Double = stringM[0].toDouble()
            val M1: Double = stringM[1].toDouble()
            val FloatM = M0 / M1
            val stringS =
                DMS[2].split("/".toRegex(), 2).toTypedArray()
            val S0: Double = stringS[0].toDouble()
            val S1: Double = stringS[1].toDouble()
            val FloatS = S0 / S1
            result = (FloatD + FloatM / 60 + (FloatS / 3600)).toFloat()
            return result
        }
    }
}