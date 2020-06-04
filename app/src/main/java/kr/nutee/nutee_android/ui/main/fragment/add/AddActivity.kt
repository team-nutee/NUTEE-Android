package kr.nutee.nutee_android.ui.main.fragment.add

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customDialog
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.main.MainActivity


/*
* 글쓰기
*/
class AddActivity : AppCompatActivity(), View.OnClickListener {

	val requestToServer = RequestToServer
	val REQUEST_CODE_PICK_IMAGE = 1001



	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.add_activity)
		init()
	}

	private fun init() {
		//해당 edit Text 에 자동 클릭하도록 한다.
		et_add_content.requestFocus()
		//자동으로 키보드가 올라오는 이벤트 처리
		val inputMethodManager: InputMethodManager =
			getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
		inputMethodManager.showSoftInput(et_add_content, 0)
		//내용이 변경되는 이벤트 처리
		et_add_content.textChangedListener {
			text_create_button.isEnabled = !it.isNullOrBlank()
		}
		text_back_button.setOnClickListener(this)
		img_upload_image_btn.setOnClickListener(this)
		text_create_button.setOnClickListener(this)
	}

	override fun onClick(v: View?) {
		when (v!!.id) {
			R.id.text_back_button -> onBackPressed()
			R.id.img_upload_image_btn -> {
				openImageChooser()
			}
			R.id.text_create_button -> {
				uploadContent()
			}
		}
	}

	override fun onBackPressed() {
		//뒤로가기 버튼을 클릭한경우 custom dialog를 띄우고 이벤트 처리
		customDialog("작성을 취소하시겠습니까??") {
			finish()
			val intent = Intent(this, MainActivity::class.java)
			startActivity(intent)
		}
	}

	private fun openImageChooser() {
		Intent(Intent.ACTION_PICK).also {
			it.type = "image/*"
			it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
			val mimeTypes = arrayOf("image/jpeg", "image/png")
			it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
			startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
		}
	}

	private fun uploadContent() {


	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
			if (data.data != null) {
				img_selected_image.setImageBitmap(createImageBitmap(data.data!!))
				Log.d("filePath", getRealPathFromURI(data.data!!)!!)
			}

		}
	}
	private fun createImageBitmap(data: Uri): Bitmap? {
		val inputStream = contentResolver.openInputStream(data)
		val img = BitmapFactory.decodeStream(inputStream)
		inputStream?.close()
		return img
	}

	private fun getRealPathFromURI(contentUri: Uri): String? {
		val result:String?
		val filePathColumns = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
		val cursor = contentResolver.query(contentUri, filePathColumns, null, null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file path
			result = contentUri.path;
		} else {
			cursor.moveToFirst();
			val idx = cursor.getColumnIndex(filePathColumns[0]);
			result = cursor.getString(idx);
			cursor.close();
		}
		return result
	}

}
