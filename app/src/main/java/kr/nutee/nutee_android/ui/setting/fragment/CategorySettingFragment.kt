package kr.nutee.nutee_android.ui.setting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.setting.RequestChangeCategory
import kr.nutee.nutee_android.databinding.MemberRegisterSelectCategoryFragmentBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.member.register.bottomsheet.ModalSelectMultipleCategory
import kr.nutee.nutee_android.ui.member.register.fragment.SelectedCategoryAdapter

/*
 * Created by 88yhtserof
 * DESC: 카테고리 설정 Fragment
 */

class CategorySettingFragment: Fragment() {

	private var binding: MemberRegisterSelectCategoryFragmentBinding? = null
	private lateinit var adapter: SelectedCategoryAdapter
	private val modalSelectCategory: ModalSelectMultipleCategory by lazy {
		ModalSelectMultipleCategory()
	}


	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = MemberRegisterSelectCategoryFragmentBinding.inflate(inflater, container, false)
		return requireBinding().root
	}

	private fun requireBinding(): MemberRegisterSelectCategoryFragmentBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		requireBinding().apply {
			tvCategoryNext.visibility=View.GONE
			tvCategoryPrevious.visibility=View.GONE
			tvRegisterCategorySave.visibility= View.VISIBLE
		}
		initView()
	}

	private fun initView() {
		adapter = SelectedCategoryAdapter()
		requireBinding().selectedItemList.adapter = adapter
		addCategoryClickEvent()
	}

	//카테고리 설정 클릭
	private fun addCategoryClickEvent() {
		requireBinding().registerSelectCategory.setOnClickListener {
			modalSelectCategory.show(requireActivity().supportFragmentManager, null)
		}
		requireBinding().tvRegisterCategorySave.setOnClickListener {
					requestChangeCategory(adapter.getSelectedCategory())
		}
		modalSelectCategory.setCompletionButtonListener {
			adapter.addAllData(it)
		}
	}

	private fun requestChangeCategory(selectedCategory: List<String>) {
		RequestToServer.authService
				.requestChageCategory(
						"Bearer "+ App.prefs.local_login_token,
						RequestChangeCategory(selectedCategory)
				).customEnqueue(
						onSuccess = {
							context?.customDialogSingleButton(getString(R.string.changeSuccess)) {}
						},
						onError = {
							context?.customDialogSingleButton(getString(R.string.changeError)) {}
						}
				)
	}

	override fun onDestroyView() {
		binding = null
		super.onDestroyView()
	}
}