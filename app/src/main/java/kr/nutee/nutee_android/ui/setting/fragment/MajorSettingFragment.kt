package kr.nutee.nutee_android.ui.setting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.setting.RequestChangeMajors
import kr.nutee.nutee_android.databinding.MemberRegisterSelectDepartmentFragmentBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.member.register.bottomsheet.ModalSelectDepartment

/*
 * Created by 88yhtserof
 * DESC: 전공 설정 Fragment
 */

class MajorSettingFragment: Fragment()
{
	private var binding: MemberRegisterSelectDepartmentFragmentBinding? = null

	private val modalSelectDepartment by lazy { ModalSelectDepartment() }

	override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?
	): View {
		binding = MemberRegisterSelectDepartmentFragmentBinding.inflate(inflater, container, false)
		return requireBinding().root
	}

	private fun requireBinding(): MemberRegisterSelectDepartmentFragmentBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		requireBinding().tvSelectDepartmentPrevious.visibility=View.GONE

		initView()
	}

	private fun initView() {
		with(requireBinding()) {
			tvRegisterSelect1stDepartment.setOnClickListener { onClick1stDepartment() }
			tvRegisterSelect2ndDepartment.setOnClickListener { onClick2ndDepartment() }
			tvSelectDepartmentNext.setOnClickListener { onClickNextEvent() }
		}
	}

	private fun showModel() {
		modalSelectDepartment.show(requireActivity().supportFragmentManager, null)
	}

	private fun onClick1stDepartment() {
		showModel()
		modalSelectDepartment.setItemClickListener {
			requireBinding().tvRegisterSelect1stDepartment.text = it
		}
	}

	private fun onClick2ndDepartment() {
		showModel()
		modalSelectDepartment.setItemClickListener {
			requireBinding().tvRegisterSelect2ndDepartment.text = it
		}
	}

	private fun onClickNextEvent() {
		if (requireBinding().tvRegisterSelect1stDepartment.text.toString() == SELECT_MAJOR_TEXT) {
			Toast.makeText(requireContext(), "전공을 선택해주세요!", Toast.LENGTH_SHORT)
					.show()
		} else {
			requestChangeMajors(getMajorList())

		}
	}

	private fun requestChangeMajors(majorList: List<String>) {
		RequestToServer.authService.requestChangeMajors(
				"Bearer "+ App.prefs.local_login_token,
				RequestChangeMajors(
						majorList
				)
		).customEnqueue(
				onSuccess = {
					context?.customDialogSingleButton(getString(R.string.changeSuccess)) {}
				},
				onError = {
					context?.customDialogSingleButton(getString(R.string.changeError)) {}
				}
		)
	}

	private fun getMajorList(): List<String> {
		val majorList = mutableListOf<String>()
		with(requireBinding()) {
			if (tvRegisterSelect1stDepartment.text.toString() != SELECT_MAJOR_TEXT) {
				majorList.add(tvRegisterSelect1stDepartment.text.toString())
			}
			if (tvRegisterSelect2ndDepartment.text.toString() != SELECT_MAJOR_TEXT) {
				majorList.add(tvRegisterSelect2ndDepartment.text.toString())
			}
		}
		return majorList
	}

	companion object {
		private const val SELECT_MAJOR_TEXT = "전공을 선택해주세요"
	}

	override fun onDestroyView() {
		binding = null
		super.onDestroyView()
	}
}