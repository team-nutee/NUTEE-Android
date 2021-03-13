package kr.nutee.nutee_android.ui.member.register.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.nutee.nutee_android.databinding.ModelBottomListBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue

class ModalSelectMultipleCategory : BottomSheetDialogFragment() {
    private var binding: ModelBottomListBinding? = null
    private lateinit var adapterMultipleCategory: ModalMultipleCategoryListAdapter
    private var completionButtonListener: ((List<String>) -> Unit)? = null

    fun setCompletionButtonListener(listener: (List<String>) -> Unit) {
        this.completionButtonListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ModelBottomListBinding.inflate(inflater, container, false)
        return requireBinding().root
    }

    private fun requireBinding(): ModelBottomListBinding = binding
        ?: error("binding is not init")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        requireBinding().modelBottomTitle.text = "카테고리를 선택해주세요"
        setCategoryAdapter()
        requireBinding().modelBottomTextButton.setOnClickListener {
            completionButtonListener?.invoke(
                adapterMultipleCategory.getSelectedItemList()
            )
            dismiss()
        }
    }

    private fun setCategoryAdapter() {
        adapterMultipleCategory = ModalMultipleCategoryListAdapter()
        requireBinding().modelList.adapter = adapterMultipleCategory
        loadCategoryList()
    }

    private fun loadCategoryList() {
        RequestToServer.backService
            .getCategory()
            .customEnqueue(
                onSuccess = { adapterMultipleCategory.addAllData(it.body()?.body ?: listOf()) }
            )
    }
}
