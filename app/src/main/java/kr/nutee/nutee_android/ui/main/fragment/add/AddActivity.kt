package kr.nutee.nutee_android.ui.main.fragment.add

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.TestToken
import kr.nutee.nutee_android.data.main.add.RequestRewritePost
import kr.nutee.nutee_android.data.main.add.RequestPost
import kr.nutee.nutee_android.data.main.home.Image
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.extend.dialog.CustomLodingDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.createImageMultipart
import kr.nutee.nutee_android.ui.main.fragment.home.detail.HomeDetailActivity


/*
* 글쓰기
*/
@Suppress("UNCHECKED_CAST")
class AddActivity : AppCompatActivity(), View.OnClickListener {

	val requestToServer = RequestToServer
	private val REQUEST_CODE_PICK_IMAGE = 1001
	var selectedImage = arrayListOf<Uri>()
	lateinit var loadingDialog:CustomLodingDialog
	lateinit var addContent:EditText
	lateinit var addTitle:EditText
	lateinit var addCategory:TextView
	lateinit var addImageList:RecyclerView
	private var postId: Int? = 0

	private var booleanTitle=false
	private var booleanContent=false


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.add_activity)
		addTitle=findViewById(R.id.et_add_title)
		addContent=findViewById(R.id.et_add_content)
		addCategory=findViewById(R.id.text_add_category)
		addImageList=findViewById(R.id.rv_image_list)

		if(intent.hasExtra("content")) fixDataMapping()
		loadingDialog = CustomLodingDialog(this)
		init()

		addContent.movementMethod = ScrollingMovementMethod()

	}

	private fun fixDataMapping() {//기존에 작성된 게시글 연결
		postId=intent.getIntExtra("postId",0)

		val content = intent.getStringExtra("content")
		val title = intent.getStringExtra("title")
		val category=intent.getStringExtra("category")
		addContent.setText(content)
		addTitle.setText(title)
		addCategory.text = category

		//이미지 수정 기능 구현 필요함
		if(intent.hasExtra("rewriteImage")){
			val rewriteImageList: ArrayList<Image>
			= intent.getParcelableArrayListExtra("rewriteImage")
			rewriteImageList.forEach { image ->
				val uri=Uri.parse(image.src)
				selectedImage.add(uri)
				Log.d("selectedImageAdd",selectedImage.size.toString())
			}
			setImageAndAdpater()
		}
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
						uploadRewriteContent()
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

	private fun uploadRewriteContent() {
		loadingDialog.startLoadingDialog()
		if (selectedImage.size > 0) {
			rewritePostHasImage()
		} else
			rewritePostNonImage()
	}

	private fun rewritePostNonImage() {
		requestToServer.backService.requestRewritePost(
			"Bearer "+ TestToken.testToken,
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
					loadDetailPost(it.body()?.body?.id!!)
				},
				onError = {}
			)
	}

	private fun rewritePostHasImage() {
		Log.d("selectedImageAdd",selectedImage.size.toString())
		Log.d("Network", "이미지포함 업로드 시작 이미지 null 여부${createImageMultipart(selectedImage).isNullOrEmpty()}")
		requestToServer.backService.requestUploadImage(createImageMultipart(selectedImage))
			.customEnqueue(
				onSuccess = {
					Log.d("Network", "사진준비 완료")
					Log.d("Network", "사진 개수 ${it.body()?.body?.size}")
					val imagesArray= arrayOfNulls<Image>(it.body()?.body!!.size)
					it.body()?.body!!.forEachIndexed() { index, str ->
						val src=Image(str)
						imagesArray[index] = src
					}
					Log.d("Network", "imagesArray 사진 개수 ${imagesArray.size}")
					requestToServer.backService.requestRewritePost(
						"Bearer "+ TestToken.testToken,
						//App.prefs.local_login_token,
						RequestRewritePost(
							addTitle.text.toString(),
							addContent.text.toString(),
							imagesArray
						),
						postId
					).customEnqueue(
							onSuccess = {
								Log.d("Network", "사진 포스트 업로드 완료")
								Log.d("Network", "사진 개수 ${it.body()?.body?.images?.size}")
								loadingDialog.dismissDialog()
								loadDetailPost(it.body()?.body?.id!!)
							},
							onError = {
								Log.d("Network", "사진 포스트 업로드 실패")
								loadingDialog.dismissDialog()
								Toast.makeText(this, "네트워크 오류로 사진 업로드를 실패했습니다.",Toast.LENGTH_SHORT).show()
							}
						)
				},
				onError = {
					Log.d("Network", "사진 준비 실패")
				}
			)
	}

	private fun uploadHasImage() {
		Log.d("Network", "이미지포함 업로드 시작 이미지 null 여부${createImageMultipart(selectedImage).isNullOrEmpty()}")
		requestToServer.backService.requestUploadImage(createImageMultipart(selectedImage))
			.customEnqueue(
				onSuccess = {
					Log.d("Network", "사진준비 완료")
					Log.d("Network", "사진 개수 ${it.body()?.body?.size}")
					val imagesArray= arrayOfNulls<Image>(it.body()?.body!!.size)
					it.body()?.body!!.forEachIndexed() { index, str ->
						val src=Image(str)
						imagesArray[index] = src
					}
					Log.d("Network", "imagesArray 사진 개수 ${imagesArray.size}")
					requestToServer.backService.requestPost(
						"Bearer "+ TestToken.testToken,
						RequestPost(
							addTitle.text.toString(),
							addContent.text.toString(),
							imagesArray,
							TestToken.testCategory
							//addCategory.toString()
						))
						.customEnqueue(
							onSuccess = {
								Log.d("Network", "사진 포스트 업로드 완료")
								Log.d("Network", "사진 개수 ${it.body()?.body?.images?.size}")
								loadingDialog.dismissDialog()
								loadDetailPost(it.body()?.body?.id!!)
							},
							onError = {
								Log.d("Network", "사진 포스트 업로드 실패")
								loadingDialog.dismissDialog()
								Toast.makeText(this, "네트워크 오류로 사진 업로드를 실패했습니다.",Toast.LENGTH_SHORT).show()
							}
						)
				},
				onError = {
					Log.d("Network", "사진 준비 실패")
				}
			)
	}

	private fun uploadNonImage() {Log.d("Network", "uploadNonImage 함수 사용")
		requestToServer.backService
			.requestPost(
				"Bearer "+TestToken.testToken,
				RequestPost(
					addTitle.text.toString(),
					addContent.text.toString(),
					null,
					TestToken.testCategory
					//viewCategory.toString()
				))
			.customEnqueue(
				onSuccess = {
					Log.d("Network", "사진 없는 포스트 생성 성공")
					loadDetailPost(it.body()?.body?.id!!)
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
							//selectedImage.clear()
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
		addImageList.adapter =ImageAdapter(selectedImage, this)
	}

	private fun loadDetailPost(id: Int) {
		loadingDialog.dismissDialog()
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
