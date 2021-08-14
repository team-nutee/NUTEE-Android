package kr.nutee.nutee_android.ui.setting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.setting_nickname_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.setting.RequestChangeNickname
import kr.nutee.nutee_android.databinding.MemberRegisterSelectCategoryFragmentBinding
import kr.nutee.nutee_android.databinding.SettingMainFragmentBinding
import kr.nutee.nutee_android.databinding.SettingNicknameFragmentBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.textChangedListener

/*
 * Created by eunseo5355
 * DESC: 닉네임 설정 Fragment
 */

class NickNameSettingFragment:Fragment() {

	private var binding: SettingNicknameFragmentBinding? = null
	private var isChangedData:String? = ""
	lateinit var textViewNickname:TextView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = SettingNicknameFragmentBinding.inflate(inflater, container, false)
		return requireBinding().root
	}

	private fun requireBinding(): SettingNicknameFragmentBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		textViewNickname= requireBinding().tvSettingNicknameText

		init()
	}

	private fun init(){
		loadUserData()
		nickNameSettingEnableEvent()
		nickNameSettingButtonEvnet()
	}

	private fun loadUserData() {
		RequestToServer.snsService.requestUserData(
				"Bearer "+ App.prefs.local_login_token
		).customEnqueue(
				onSuccess = {
					textViewNickname.text=it.body()!!.body.nickname
				},
				onError = {
					textViewNickname.text="닉네임"
				}
		)
	}

	private fun nickNameSettingEnableEvent(){
		with(requireBinding()){
			etSettingNickname.textChangedListener {
				tvSettingNicknameSaveBtn.isEnabled =
						(etSettingNickname != null && etSettingNickname.text.isNotEmpty())
			}
		}
	}

	private fun nickNameSettingButtonEvnet(){
		requireBinding().tvSettingNicknameSaveBtn.setOnClickListener {
			isChangedEvnet()
		}
	}

	private fun isChangedEvnet(){
		if(isChangedData != requireBinding().etSettingNickname.text.toString()){
			requestToNickNameChange()
			return
		}
		requireContext().showTextShake(
				requireBinding().tvNickNameChangeResutl,
				"이미 성공적으로 변경되었습니다!",
				R.color.nuteeBase
		)
	}

	private fun requestToNickNameChange(){
		RequestToServer.authService
			.requestToNickNameChange(
					"Bearer "+ App.prefs.local_login_token,
					RequestChangeNickname(requireBinding().etSettingNickname.text.toString())
			).customEnqueue(
				onSuccess = {
					requireContext().showTextShake(
						requireBinding().tvNickNameChangeResutl,
						"성공적으로 변경되었습니다.",
						R.color.nuteeBase
					)
					isChangedData = requireBinding().etSettingNickname.text.toString()
				},
				onError = {
					requireContext().showTextShake(
						requireBinding().tvNickNameChangeResutl,
						"이미 사용중인 닉네임 입니다",
						R.color.colorRed
					)
				}
			)
	}

}