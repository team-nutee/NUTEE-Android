package kr.nutee.nutee_android.ui.extend.imageSetting

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.File

fun ContentResolver.getFileName(fileUri: Uri): String {
	var name = ""
	val returnCursor = this.query(fileUri, null, null, null, null)
	if (returnCursor != null) {
		val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
		returnCursor.moveToFirst()
		name = returnCursor.getString(nameIndex)
		returnCursor.close()
	}
	return name

}