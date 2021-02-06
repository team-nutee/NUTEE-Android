package kr.nutee.nutee_android.ui.main.fragment.add

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_activity.*
import kotlinx.android.synthetic.main.main_home_detail_activtiy.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.add.RequestFixPost
import kr.nutee.nutee_android.data.main.add.RequestPost
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.extend.dialog.CustomLodingDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.createImageMultipart
import kr.nutee.nutee_android.ui.main.MainActivity
import org.w3c.dom.Text


/*
* 글쓰기
*/
class AddActivity : AppCompatActivity(), View.OnClickListener {

	val requestToServer = RequestToServer
	private val REQUEST_CODE_PICK_IMAGE = 1001
	var selectedImage = arrayListOf<Uri>()
	private lateinit var imageAdapter: ImageAdapter
	lateinit var loadingDialog:CustomLodingDialog
	lateinit var addContent:EditText
	//val viewTitle=view.findViewById<EditText>(R.id.et_add_title)
	//var viewCategory=view.findViewById<TextView>(R.id.text_add_category)
	lateinit var viewTitle:EditText
	lateinit var viewCategory:TextView
	var postId:Int = 0


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.add_activity)
		viewTitle=findViewById(R.id.et_add_title)
		viewCategory=findViewById(R.id.text_add_category)
		fixDataMapping()
		loadingDialog = CustomLodingDialog(this)
		init()

		addContent=findViewById(R.id.et_add_content)
		addContent.movementMethod = ScrollingMovementMethod()

	}

	private fun fixDataMapping() {
		val content = intent.getStringExtra("content")
		val title = intent.getStringExtra("title")
		//val category=intent.getStringExtra("category")
		postId = intent.getIntExtra("postId", 0)
		et_add_content.setText(content)
		viewTitle.setText(title)

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
		customDialog("작성을 취소하시겠습니까?") {
			finish()
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
		loadingDialog.startLoadingDialog()
		if (selectedImage.size > 0) {
			uploadHasImage()
		} else {
			uploadNonImage()
		}
	}

	private fun fixPostUpload() {
		requestToServer.backService.requestFixPost(
			App.prefs.local_login_token,
			postId,
			RequestFixPost(
				viewTitle.toString(),
				et_add_content.text.toString(),
				null
			))
			.customEnqueue(
				onSuccess = {
					gotoMain()
				},
				onError = {}
			)
	}

	private fun uploadHasImage() {
		requestToServer.backService.requestImage(createImageMultipart(selectedImage))
			.customEnqueue(
				onSuccess = {
					Log.d("imageUplod", "업로드 완료>${it.toString()}")
					requestToServer.backService.requestPost(
						App.prefs.local_login_token,
						RequestPost(
							viewTitle.toString(),
							et_add_content.text.toString(),
							it.body(),
							viewCategory.toString()
						))
						.customEnqueue(
							onSuccess = {
								gotoMain()
							},
							onError = {}
						)
				},
				onError = {}
			)
	}

	private fun uploadNonImage() {
		requestToServer.backService
			.requestPost(
				App.prefs.local_login_token,
				RequestPost(
					viewTitle.toString(),
					et_add_content.text.toString(),
					null,
					viewCategory.toString()
				))
			.customEnqueue(
				onSuccess = {
					gotoMain()
				},
				onError = {}
			)
	}


	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (
			requestCode == REQUEST_CODE_PICK_IMAGE &&
			resultCode == Activity.RESULT_OK &&
			data != null
		) {
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
				} else {//멀티 선택 미지원 기기에서 clipData가 없음.
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
		loadingDialog.dismissDialog()
		val intent = Intent(applicationContext,MainActivity::class.java)
		finish()
		startActivity(intent)
	}

}
