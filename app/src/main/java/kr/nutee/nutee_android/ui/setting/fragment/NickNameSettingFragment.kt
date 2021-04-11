package kr.nutee.nutee_android.ui.setting.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.custom_loading_dialog.*
import kotlinx.android.synthetic.main.setting_nickname_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.setting.RequestChangeNickname
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.textChangedListener
import org.w3c.dom.Text

/*
 * Created by eunseo5355
 * DESC: 닉네임 설정 Fragment
 */

class NickNameSettingFragment:Fragment() {

	private var isChangedData:String? = ""
	lateinit var textViewNickname:TextView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.setting_nickname_fragment,container,false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		textViewNickname=view.findViewById(R.id.tv_setting_nickname_text)

		init()
	}

	private fun init(){
		loadUserData()
		nickNameSettingEnableEvent()
		nickNameSettingButtonEvnet()
	}

	private fun loadUserData() {
		RequestToServer.backService.requestUserData(
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
		et_setting_nickname.textChangedListener{
			tv_setting_nickname_save_btn.isEnabled =
				(et_setting_nickname != null && et_setting_nickname.text.isNotEmpty())
		}
	}

	private fun nickNameSettingButtonEvnet(){
		tv_setting_nickname_save_btn.setOnClickListener {
			isChangedEvnet()
		}
	}

	private fun isChangedEvnet(){
		if (isChangedData != et_setting_nickname.text.toString()) {
			requestToNickNameChange()
			return
		}
		requireContext().showTextShake(
			tv_nick_name_change_resutl,
			"이미 성공적으로 변경되었습니다!",
			R.color.nuteeBase
		)
	}

	private fun requestToNickNameChange(){
		RequestToServer.authService
			.requestToNickNameChange(
					"Bearer "+ App.prefs.local_login_token,
					RequestChangeNickname(et_setting_nickname.text.toString())
			).customEnqueue(
				onSuccess = {
					requireContext().showTextShake(
						tv_nick_name_change_resutl,
						"성공적으로 변경되었습니다.",
						R.color.nuteeBase
					)
					isChangedData = et_setting_nickname.text.toString()
				},
				onError = {
					requireContext().showTextShake(
						tv_nick_name_change_resutl,
						"이미 사용중인 닉네임 입니다",
						R.color.colorRed
					)
				}
			)
	}

}