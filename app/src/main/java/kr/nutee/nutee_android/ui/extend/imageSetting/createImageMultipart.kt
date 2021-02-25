package kr.nutee.nutee_android.ui.extend.imageSetting

import android.content.Context
import android.net.Uri
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun Context.createImageMultipart(fileUri: ArrayList<Uri>): ArrayList<MultipartBody.Part> {
	val body: ArrayList<MultipartBody.Part> =  arrayListOf()
	fileUri.forEach {uri ->
		val parcelFileDescriptor
				= contentResolver.openFileDescriptor(uri, "r", null)

		parcelFileDescriptor?.let {
			val inputStream = FileInputStream(it.fileDescriptor)
			val file = File(cacheDir, contentResolver.getFileName(uri))
			val outputStream = FileOutputStream(file)
			inputStream.copyTo(outputStream)
			Log.d("UriFilePath", file.path)
			val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
			Log.d("UriFilePath", requestFile.toString())
			body.add(MultipartBody.Part.createFormData("images", file.path, requestFile))
		}
	}

	return body
}

fun Context.createProfileMultipart(fileUri: Uri): MultipartBody.Part? {
	var body:MultipartBody.Part? = null
	val parcelFileDescriptor
			= contentResolver.openFileDescriptor(fileUri, "r", null)

	parcelFileDescriptor?.let {
		val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
		val file = File(cacheDir, contentResolver.getFileName(fileUri))
		val outputStream = FileOutputStream(file)
		inputStream.copyTo(outputStream)
		Log.d("UriFilePath", file.path)
		val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
		body = MultipartBody.Part.createFormData("image", file.path, requestFile)
	}

	return body
}


