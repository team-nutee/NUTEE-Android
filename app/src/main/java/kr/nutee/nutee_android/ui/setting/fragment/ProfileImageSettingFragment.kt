package kr.nutee.nutee_android.ui.setting.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.setting_profile_image_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.setting.RequestChangeProfileImage
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.customDialogDevInfo
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.extend.imageSetting.createProfileMultipart

/*
 * Created by eunseo5355
 * DESC: 프로필 이미지 설정 Fragment
 * by 88yhtserof
 * DESC: 2.0 버전 수정
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
		loadProfileImage()
		profileImageSettingFragmentButtneEvent()
	}

	private fun loadProfileImage() {
		RequestToServer.snsService.requestUserData(
				"Bearer "+ App.prefs.local_login_token
		).customEnqueue(
				onSuccess = {
					Glide.with(this)
							.load(it.body()?.body?.image?.src)
							.placeholder(R.drawable.ic_baseline_rotate_left_24)
							.error(R.mipmap.nutee_character_background_white_round)
							.into(img_setting_profile_image_btn)
				}
		)
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

			tv_setting_progile_image_save_btn.apply {
				isEnabled=true
				setTextColor(context.getColor(R.color.nuteeBase))
			}
			profileImageSaveEvnetListner={profileImageRequestToServer(data.data!!)}
			return
		}
	}

	private fun profileImageRequestToServer(data: Uri) {
			RequestToServer.snsService.requestUploadImage(context?.createProfileMultipart(data)!!)
					.customEnqueue(
							onSuccess = {
								RequestToServer.authService
										.requestToUploadProfile(
												"Bearer "+ App.prefs.local_login_token,
												RequestChangeProfileImage(it.body()!!.body[0])
										).customEnqueue(
												onSuccess = {
													context?.customDialogDevInfo(getString(R.string.changeSuccess))
												},
												onError = {
													context?.customDialogDevInfo(getString(R.string.changeError))
												},
												onFail ={context?.customDialogDevInfo(getString(R.string.changeFailProfilImage))}
										)
		}
					)
		}

	companion object {
	private const val PROFILE_CODE_PICK_IMAGE = 1011
}
}

