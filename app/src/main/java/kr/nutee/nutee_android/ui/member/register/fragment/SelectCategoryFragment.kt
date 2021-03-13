package kr.nutee.nutee_android.ui.member.register.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.nutee.nutee_android.databinding.MemberRegisterSelectCategoryFragmentBinding
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener
import kr.nutee.nutee_android.ui.member.register.bottomsheet.ModalSelectMultipleCategory

class SelectCategoryFragment : Fragment() {
    private var binding: MemberRegisterSelectCategoryFragmentBinding? = null
    private var onRegisterDataSetListener: OnRegisterDataSetListener? = null
    private lateinit var adapter: SelectedCategoryAdapter
    private val modalSelectCategory: ModalSelectMultipleCategory by lazy {
        ModalSelectMultipleCategory()
    }

    private var registerCategoryPreviousEvnetListener: (() -> Unit)? = null
    private var registerCategoryNextEventListener: ((Boolean) -> Unit)? = null

    fun setRegisterCategoryPreviousEventListener(listener: () -> Unit) {
        this.registerCategoryPreviousEvnetListener = listener
    }

    fun setReigsterCategoryNextEventListener(listener: (Boolean) -> Unit) {
        this.registerCategoryNextEventListener = listener
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is OnRegisterDataSetListener)
            throw RuntimeException(context.toString() + "must implement OnRegisterDataSetListener")
        onRegisterDataSetListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MemberRegisterSelectCategoryFragmentBinding.inflate(inflater, container, false)
        return requireBinding().root
    }

    private fun requireBinding(): MemberRegisterSelectCategoryFragmentBinding = binding
        ?: error("binding is not init")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        adapter = SelectedCategoryAdapter()
        requireBinding().selectedItemList.adapter = adapter
        addCategoryClickEvent()
    }

    private fun addCategoryClickEvent() {
        requireBinding().registerSelectCategory.setOnClickListener {
            modalSelectCategory.show(requireActivity().supportFragmentManager, null)
        }
        requireBinding().tvCategoryNext.setOnClickListener {
            onRegisterDataSetListener?.onRegisterCategoryDataSetListener(adapter.getSelectedCategory())
            registerCategoryNextEventListener?.invoke(adapter.hasSelectedCategory())
        }
        requireBinding().tvCategoryPrevious.setOnClickListener {
            registerCategoryPreviousEvnetListener?.invoke()
        }
        modalSelectCategory.setCompletionButtonListener {
            adapter.addAllData(it)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
