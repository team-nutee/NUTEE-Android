package kr.nutee.nutee_android.ui.member.register.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.nutee.nutee_android.databinding.ModelBottomListBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue

/*
* 카테고리 단일 선택
* */


class ModalSelectCategory: BottomSheetDialogFragment() {
    private var binding: ModelBottomListBinding? = null
    private lateinit var adapter: ModalCategoryListAdapter
    private var itemClickListener: ((String) -> Unit)? = null

    fun setItemClickListener(listener: (String) -> Unit) {
        this.itemClickListener = listener
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
        requireBinding().modelBottomTitle.text = "카테고리를 선택해주세요."
        requireBinding().modelBottomTextButton.visibility=View.GONE
        setCategoryAdapter()
    }

    private fun setCategoryAdapter() {
        adapter = ModalCategoryListAdapter(
                itemClickListener,
                { dismiss() }
        )
        requireBinding().modelList.adapter = adapter
        loadDepartmentList()
    }

    private fun loadDepartmentList() {
        RequestToServer.snsService
                .getCategory()
                .customEnqueue(
                        onSuccess = { adapter.addAllData(it.body()?.body ?: listOf()) }
                )
    }
}