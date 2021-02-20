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
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.TestToken
import kr.nutee.nutee_android.data.main.add.RequestRewritePost
import kr.nutee.nutee_android.data.main.add.RequestPost
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.extend.dialog.CustomLodingDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.createImageMultipart
import kr.nutee.nutee_android.ui.main.fragment.home.detail.HomeDetailActivity


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
	lateinit var addTitle:EditText
	lateinit var addCategory:TextView

	private var booleanTitle=false
	private var booleanContent=false


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.add_activity)
		addTitle=findViewById(R.id.et_add_title)
		addContent=findViewById(R.id.et_add_content)
		addCategory=findViewById(R.id.text_add_category)
		fixDataMapping()
		loadingDialog = CustomLodingDialog(this)
		init()

		addContent.movementMethod = ScrollingMovementMethod()

	}

	private fun fixDataMapping() {//기존에 작성된 게시글 연결
		val content = intent.getStringExtra("content")
		val title = intent.getStringExtra("title")
		val category=intent.getStringExtra("category")
		addContent.setText(content)
		addTitle.setText(title)
		addCategory.text = category

	}

	private fun init() {
		addTitle.requestFocus()

		text_back_button.setOnClickListener(this)
		img_upload_image_btn.setOnClickListener(this)
		text_create_button.setOnClickListener(this)
	}

	override fun onClick(v: View?) {
		when (v!!.id) {
			R.id.text_back_button -> onBackPressed()
			R.id.img_upload_image_btn -> openImageChooser()
			R.id.text_create_button -> {
				if(checkPostBlank()){
					if (intent.hasExtra("content"))
						rewritePost()
					else
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
		} else
			uploadNonImage()
	}

	private fun rewritePost() {
		val postId=intent.getIntExtra("postId",0)

		requestToServer.backService.requestRewritePost(
			"Bearer "+ TestToken.testToken ,
			//App.prefs.local_login_token,
			RequestRewritePost(
				addTitle.text.toString(),
				addContent.text.toString(),
				null
			),
			postId
		)
			.customEnqueue(
				onSuccess = {
					gotoMain(it.body()?.body?.id!!)
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
							addTitle.text.toString(),
							addContent.text.toString(),
							it.body(),
							addCategory.toString()
						))
						.customEnqueue(
							onSuccess = {
								gotoMain(it.body()?.body?.id!!)
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
				"Bearer "+TestToken.testToken,
				RequestPost(
					title = addTitle.text.toString(),
					content = addContent.text.toString(),
					image = null,
					category = "IT2"
					//viewCategory.toString()
				))
			.customEnqueue(
				onSuccess = {
					Log.d("Network", "포스트 생성 성공")
					gotoMain(it.body()?.body?.id!!)
				},
				onError = {
					Log.d("Network", "포스트 생성 실패")
				}
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

	private fun gotoMain(id: Int) {
		//loadingDialog.dismissDialog()
		val intent = Intent(applicationContext, HomeDetailActivity::class.java)
		intent.putExtra("Detail_id",id)
		finish()
		startActivity(intent)
	}

	private fun checkPostBlank(): Boolean {//글 작성 여부 확인
		if(addTitle.text.toString().isBlank()
			||addContent.text.toString().isBlank()){
			Toast.makeText(this,"아직 글이 완성되지 않았습니다.",Toast.LENGTH_LONG).show()
			return false
		}
		return true
	}

}
