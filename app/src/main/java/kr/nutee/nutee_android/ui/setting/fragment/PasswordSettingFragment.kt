package kr.nutee.nutee_android.ui.setting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.setting_password_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.textChangedListener

class PasswordSettingFragment: Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.setting_password_fragment,container,false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		et_setting_password_check.textChangedListener{
			tv_setting_password_save_btn.isEnabled =
				(et_setting_password.text != null &&
						et_setting_password.text.toString()
						== et_setting_password_check.text.toString())
		}


	}
}