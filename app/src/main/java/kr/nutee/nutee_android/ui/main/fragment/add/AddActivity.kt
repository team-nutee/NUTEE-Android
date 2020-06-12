package kr.nutee.nutee_android.ui.main.fragment.add

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.add_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.add.RequestFixPost
import kr.nutee.nutee_android.data.main.add.RequestPost
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.main.MainActivity
import kr.nutee.nutee_android.ui.main.fragment.home.HomeFlagement


/*
* 글쓰기
*/
class AddActivity : AppCompatActivity(), View.OnClickListener {

	val requestToServer = RequestToServer
	private val REQUEST_CODE_PICK_IMAGE = 1001
	var selectedImage = arrayListOf<Uri>()
	private lateinit var imageAdapter: ImageAdapter
	var postId = 0


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.add_activity)
		fixDataMapping()
		init()

	}

	private fun fixDataMapping() {
		val content = intent.getStringExtra("content")
		postId = intent.getIntExtra("postId",0)
		et_add_content.setText(content)
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
				Toast.makeText(this,
					"안드로이드 버전에서는 이미지 업로드시\n 이미지 불러오기가 불안정하여 추후 업데이트 예정입니다.",
					Toast.LENGTH_SHORT
				).show()
				//openImageChooser()
			}
			R.id.text_create_button -> {
				if (intent.hasExtra("content")) {
					fixPostUpload()
				} else {
					uploadContent()
				}

			}
		}
	}

	override fun onBackPressed() {
		//뒤로가기 버튼을 클릭한경우 custom dialog를 띄우고 이벤트 처리
		customDialog("작성을 취소하시겠습니까??") {
			setResult(Activity.RESULT_CANCELED)
			finish()
		}
	}

	private fun openImageChooser() {
		Intent(Intent.ACTION_PICK).also {
			it.type = MediaStore.Images.Media.CONTENT_TYPE
			it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
			val mimeTypes = arrayOf("image/jpeg", "image/png")
			it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
			startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
		}
	}

	private fun uploadContent() {
		if (selectedImage.size > 0) {
			uploadHasImage()
		} else {
			uploadNonImage()
		}
	}
	private fun fixPostUpload() {
		requestToServer.service.requestFixPost(
			App.prefs.local_login_token,
			RequestFixPost(
				postId,
				et_add_content.text.toString(),
				null
			)
		).customEnqueue {
			if (it.isSuccessful) {
				gotoMain()
			}
		}
	}

	private fun uploadHasImage() {
		requestToServer.service.requestImage(createImageMultipart(selectedImage))
			.customEnqueue { response ->
				if (response.isSuccessful) {
					Log.d("imageUplod", "업로드 완료>${response.body().toString()}")
					requestToServer.service.requestPost(
						App.prefs.local_login_token,
						RequestPost(
							et_add_content.text.toString(),
							response.body()
						)
					).customEnqueue { postRes ->
						if (postRes.isSuccessful) {
							gotoMain()
						}
					}
				}
			}
	}
	private fun uploadNonImage() {
		requestToServer.service.requestPost(
			App.prefs.local_login_token,
			RequestPost(
				et_add_content.text.toString(),
				null
			)
		).customEnqueue { postRes ->
			if (postRes.isSuccessful) {
				gotoMain()
			}
		}
	}



	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
			if (data.data != null) {
				val selectedImageArrayList = arrayListOf<Uri>()
				if (data.clipData != null) {
					val clipdata = data.clipData!!
					when {
						clipdata.itemCount > 10 -> {
							Toast.makeText(this, "사진은 10개까지 선택 가능합니다", Toast.LENGTH_SHORT).show()
							return
						}
						else -> {
							for (i in 0 until clipdata.itemCount) {
								selectedImageArrayList.add(clipdata.getItemAt(i).uri)
							}
							selectedImage.clear()
							selectedImage = selectedImageArrayList
							setImageAndAdpater()

						}
					}
				}else{//멀티 선택 미지원 기기에서 clipData가 없음.
					selectedImageArrayList.add(data.data!!)
					selectedImage = selectedImageArrayList
					setImageAndAdpater()
				}

			}
		}
	}

	private fun setImageAndAdpater() {
		sv_selected_image.visibility = View.VISIBLE
		imageAdapter = ImageAdapter(selectedImage, this)
		rv_image_list.adapter = imageAdapter
	}

	private fun gotoMain() {
		val intent = Intent(this, MainActivity::class.java)
		startActivity(intent)
		finish()
	}

}
