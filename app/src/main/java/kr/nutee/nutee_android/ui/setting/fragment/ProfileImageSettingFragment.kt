package kr.nutee.nutee_android.ui.setting.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.setting_profile_image_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.imageSetting.createProfileMultipart
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting

/*
 * Created by eunseo5355
 * DESC: 프로필 이미지 설정 Fragment
 */

class ProfileImageSettingFragment : Fragment(), View.OnClickListener {

	private var profileImageSaveEvnetListner: (() -> Unit)? = null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.setting_profile_image_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		init()

	}

	private fun init() {
		loadProfileImage(App.prefs.url)
		profileImageSettingFragmentButtneEvent()
	}

	private fun loadProfileImage(profileImage: String?) {
		Glide.with(this)
			.load(setImageURLSetting(profileImage))
			.into(img_setting_profile_image_btn)
	}

	private fun profileImageSettingFragmentButtneEvent() {
		img_setting_profile_image_btn.setOnClickListener(this)
		tv_setting_progile_image_save_btn.setOnClickListener(this)
	}

	override fun onClick(profileFragmentButton: View?) {
		when (profileFragmentButton!!.id) {
			R.id.img_setting_profile_image_btn -> {
				openSelectImage()
			}
			R.id.tv_setting_progile_image_save_btn ->{
				profileImageSaveEvnetListner?.invoke()
			}
		}
	}

	private fun openSelectImage() {
		Intent(Intent.ACTION_PICK).also {
			it.type = "image/*"
			val mimeTypes = arrayOf("image/jpeg", "image/png")
			it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
			startActivityForResult(it, PROFILE_CODE_PICK_IMAGE)
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (
			requestCode == PROFILE_CODE_PICK_IMAGE &&
			resultCode == Activity.RESULT_OK &&
			data != null
		) {
			Glide.with(this)
				.load(data.data!!)
				.into(img_setting_profile_image_btn)
			profileImageSaveEvnetListner={profileImageRequestToServer(data.data!!)}
			return
		}
	}

	private fun profileImageRequestToServer(data: Uri) {
		RequestToServer.authService
			.requestToUploadProfile(
				token = App.prefs.local_login_token,
				src = requireContext().createProfileMultipart(data)!!
			)
			.customEnqueue(
				onSuccess = {
					Toast.makeText(requireContext(),"프로필이 변경되었습니다.\n 적용까지 시간이 걸릴 수 있습니다.",Toast.LENGTH_SHORT).show()
					loadProfileImage(it.body()?.src)
				},
				onError = {
					Toast.makeText(requireContext(),"프로필을 변경 할 수 없습니다!!",Toast.LENGTH_SHORT).show()
				},
				onFail = {
					Toast.makeText(requireContext(),"서버 에러입니다.",Toast.LENGTH_SHORT).show()
				}
			)
	}

	companion object {
	private const val PROFILE_CODE_PICK_IMAGE = 1011
}
}