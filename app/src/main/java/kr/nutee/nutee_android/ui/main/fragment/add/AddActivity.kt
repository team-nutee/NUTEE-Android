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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.add.RequestRewritePost
import kr.nutee.nutee_android.data.main.add.RequestPost
import kr.nutee.nutee_android.data.main.home.Image
import kr.nutee.nutee_android.databinding.AddActivityBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.extend.dialog.CustomLodingDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.createImageMultipart
import kr.nutee.nutee_android.ui.main.fragment.home.detail.HomeDetailActivity
import kr.nutee.nutee_android.ui.member.register.bottomsheet.ModalSelectCategory
import kr.nutee.nutee_android.ui.member.register.bottomsheet.ModalSelectMyMajorsList


/*
created by jinsu47555
* 글쓰기
*
* created by 88yhtserofG
* DESC: 2.0버전으로 수정 및 구현
*/
@Suppress("UNCHECKED_CAST")
class AddActivity : AppCompatActivity(), View.OnClickListener {

	private val binding by lazy { AddActivityBinding.inflate(layoutInflater) }

	val requestToServer = RequestToServer
	private val REQUEST_CODE_PICK_IMAGE = 1001
	private val modalSelectMyMajorsList by lazy {  ModalSelectMyMajorsList() }
	private val modalSelectCategory by lazy { ModalSelectCategory() }
	private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
		if (
				it.resultCode == Activity.RESULT_OK &&
				it.data != null
		) {
			if (it.data!!.data != null) {
				val selectedImageArrayList = arrayListOf<Uri>()
				if (it.data!!.clipData != null) {
					val clipdata = it.data!!.clipData!!
					when {
						clipdata.itemCount > 10 -> {
							Toast.makeText(this, "사진은 10개까지 선택 가능합니다", Toast.LENGTH_SHORT).show()
						}
						else -> {
							for (i in 0 until clipdata.itemCount) {
								selectedImageArrayList.add(clipdata.getItemAt(i).uri)
							}
							selectedImage.addAll(selectedImageArrayList)
							setImageAndAdpater()

						}
					}
				} else {//멀티 선택 미지원 기기에서 clipData가 없음.
					selectedImageArrayList.add(it.data!!.data!!)
					selectedImage.addAll(selectedImageArrayList)
					setImageAndAdpater()
				}

			}
		}
	}

	var selectedImage = arrayListOf<Uri>()
	lateinit var loadingDialog:CustomLodingDialog
	lateinit var addContent:EditText
	lateinit var addTitle:EditText
	lateinit var addCategory:TextView
	lateinit var addMajor:TextView
	lateinit var addImageList:RecyclerView
	private var postId: Int? = 0

	private lateinit var requestCategory:String


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		with(binding){
			addTitle = etAddTitle
			addContent = etAddContent
			addCategory = textAddCategory
			addMajor = textAddMajor
			addImageList = rvImageList
		}
		/*addTitle=findViewById(R.id.et_add_title)
		addContent=findViewById(R.id.et_add_content)
		addCategory=findViewById(R.id.text_add_category)
		addMajor=findViewById(R.id.text_add_major)
		addImageList=findViewById(R.id.rv_image_list)*/

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
		addMajor.visibility=View.GONE
	}

	private fun init() {
		addTitle.requestFocus()

		binding.textBackButton.setOnClickListener(this)
		binding.imgUploadImageBtn.setOnClickListener(this)
		binding.textCreateButton.setOnClickListener(this)
		/*text_back_button.setOnClickListener(this)
		img_upload_image_btn.setOnClickListener(this)
		text_create_button.setOnClickListener(this)*/
		addMajor.setOnClickListener{onClickMajor(it)}
		addCategory.setOnClickListener { onClickCategory(it) }
	}

	private fun onClickMajor(view: View) {
		showModel(view)
		modalSelectMyMajorsList.setItemClickListener {
			addMajor.text = it
			addCategory.apply {
				isActivated=true
				isEnabled=false
			}
		}
	}

	private fun onClickCategory(view: View) {
		showModel(view)
		modalSelectCategory.setItemClickListener {
			addCategory.text = it
			addMajor.apply {
				isActivated=true
				isEnabled=false
			}
		}
	}

	private fun showModel(view: View) {
		when (view) {
			addMajor ->modalSelectMyMajorsList.show(supportFragmentManager, null)
			addCategory->modalSelectCategory.show(supportFragmentManager,null)
		}
	}

	override fun onClick(v: View?) {
		with(binding){
			when(v!!.id){
				textBackButton.id -> onBackPressed()
				imgUploadImageBtn.id -> openImageChooser()
				textCreateButton.id -> {
					Log.d("Network", "사진 포함 게시글 수정 버튼 클릭")
					if(checkPostBlank()){
						if (intent.hasExtra("content"))
							uploadRewriteContent()
						else
							uploadContent()
					}
				}
			}
		}

		/*when (v!!.id) {
			R.id.text_back_button -> onBackPressed()
			R.id.img_upload_image_btn -> openImageChooser()
			R.id.text_create_button -> {
				Log.d("Network", "사진 포함 게시글 수정 버튼 클릭")
				if(checkPostBlank()){
					if (intent.hasExtra("content"))
						uploadRewriteContent()
					else
						uploadContent()
				}
			}
		}*/
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
			//startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
			getContent.launch(it)
		}
	}

	private fun uploadContent() {
		//요청카테고리 설정
		requestCategory = if(addCategory.isActivated)
			addMajor.text.toString()
		else
			addCategory.text.toString()

		loadingDialog.startLoadingDialog()
		if (selectedImage.size > 0) {
			uploadHasImage()
		} else
			uploadNonImage()
	}

	private fun uploadRewriteContent() {
		loadingDialog.startLoadingDialog()

			rewritePostNonImage()
	}

	private fun rewritePostNonImage() {
		requestToServer.snsService.requestRewritePost(
				"Bearer "+ App.prefs.local_login_token,
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
		requestToServer.snsService.requestUploadMultipleImage(createImageMultipart(selectedImage))
			.customEnqueue(
				onSuccess = { uploadIt ->
					val imagesArray= arrayOfNulls<Image>(uploadIt.body()?.body!!.size)
					uploadIt.body()?.body!!.forEachIndexed() { index, str ->
						val src=Image(str)
						imagesArray[index] = src
					}
					requestToServer.snsService.requestRewritePost(
							"Bearer "+ App.prefs.local_login_token,
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
		requestToServer.snsService.requestUploadMultipleImage(createImageMultipart(selectedImage))
			.customEnqueue(
				onSuccess = {
					val imagesArray= arrayOfNulls<Image>(it.body()?.body!!.size)
					it.body()?.body!!.forEachIndexed() { index, str ->
						val src=Image(str)
						imagesArray[index] = src
					}
					requestToServer.snsService.requestPost(
							"Bearer "+ App.prefs.local_login_token,
						RequestPost(
							addTitle.text.toString(),
							addContent.text.toString(),
							imagesArray,
								requestCategory
						))
						.customEnqueue(
							onSuccess = {
								Log.d("Network", "사진 포스트 업로드 완료")
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

	private fun uploadNonImage() {
		requestToServer.snsService
			.requestPost(
					"Bearer "+ App.prefs.local_login_token,
				RequestPost(
					addTitle.text.toString(),
					addContent.text.toString(),
					null,
							requestCategory
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


/*	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
							selectedImage.addAll(selectedImageArrayList)
							setImageAndAdpater()

						}
					}
				} else {//멀티 선택 미지원 기기에서 clipData가 없음.
					selectedImageArrayList.add(data.data!!)
					selectedImage.addAll(selectedImageArrayList)
					setImageAndAdpater()
				}

			}
		}
	}*/

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
			Toast.makeText(this,"아직 글이 완성되지 않았습니다!",Toast.LENGTH_LONG)
					.show()
			return false
		}
		if(addCategory.text.toString()=="카테고리"
				&&addMajor.text.toString()=="내 전공") {
			Toast.makeText(this, "카테고리를 설정해 주세요!", Toast.LENGTH_LONG)
					.show()
			return false
		}
		return true
	}

}
