package kr.nutee.nutee_android.ui.setting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.setting_nickname_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.textChangedListener

/*
 * Created by eunseo5355
 * DESC: 닉네임 설정 Fragment
 */

class NickNameSettingFragment:Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.setting_nickname_fragment,container,false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		et_setting_nickname.textChangedListener{
			tv_setting_nickname_save_btn.isEnabled =
				(et_setting_nickname != null && et_setting_nickname.text.isNotEmpty())
		}


	}

}