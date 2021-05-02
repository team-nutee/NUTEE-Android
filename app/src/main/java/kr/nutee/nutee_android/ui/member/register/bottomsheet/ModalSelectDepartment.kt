package kr.nutee.nutee_android.ui.member.register.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.nutee.nutee_android.databinding.ModelBottomListBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue

class ModalSelectDepartment : BottomSheetDialogFragment() {
    private var binding: ModelBottomListBinding? = null
    private lateinit var adapter: ModalDepartmentListAdapter
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
        requireBinding().modelBottomTitle.text = "전공을 선택해주세요."
        requireBinding().modelBottomTextButton.visibility=View.GONE
        setCategoryAdapter()
    }

    private fun setCategoryAdapter() {
        adapter = ModalDepartmentListAdapter(
            itemClickListener,
            { dismiss() }
        )
        requireBinding().modelList.adapter = adapter
        loadDepartmentList()
    }

    private fun loadDepartmentList() {
        RequestToServer.snsService
            .getMajors()
            .customEnqueue(
                onSuccess = { adapter.addAllData(it.body()?.body ?: listOf()) }
            )
    }
}
