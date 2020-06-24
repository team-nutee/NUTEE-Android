package kr.nutee.nutee_android.ui.extend.imageSetting


import android.os.Environment
import android.util.Log
import okio.IOException
import java.io.File

import java.text.SimpleDateFormat
import java.util.*
import java.util.Date as Date1

@Throws(IOException::class)
fun createImageFile(): File? {
	// Create an image file name
	val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date1())
	val imageFileName = "JPEG_${timeStamp}.jpg"
	val mFileName = imageFileName
	var imageFile: File? = null
	val storageDir = File(
		Environment.getExternalStorageDirectory().toString() + "/Pictures", "yuna"
	)
	if (!storageDir.exists()) {
		Log.i("mCurrentPhotoPath1", storageDir.toString())
		storageDir.mkdirs()
	}
	imageFile = File(storageDir, imageFileName)
	val mCurrentPhotoPath = imageFile.absolutePath
	return imageFile
}