package kr.nutee.nutee_android.ui.member.register.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kr.nutee.nutee_android.databinding.MemberRegisterSelectDepartmentFragmentBinding
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener
import kr.nutee.nutee_android.ui.member.register.bottomsheet.ModalSelectDepartment

class SelectDepartmentFragment : Fragment() {
    private var binding: MemberRegisterSelectDepartmentFragmentBinding? = null
    private var onRegisterDataSetListener: OnRegisterDataSetListener? = null

    private var registerDepartmentPreviousEvnetListener: (() -> Unit)? = null
    private var registerDepartmentNextEventListener: (() -> Unit)? = null

    private val modalSelectDepartment by lazy { ModalSelectDepartment() }

    fun setRegisterDepartmentPreviousEventListener(listener: () -> Unit) {
        this.registerDepartmentPreviousEvnetListener = listener
    }

    fun setReigsterDepartmentNextEventListener(listener: () -> Unit) {
        this.registerDepartmentNextEventListener = listener
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
        binding = MemberRegisterSelectDepartmentFragmentBinding.inflate(inflater, container, false)
        return requireBinding().root
    }

    private fun requireBinding(): MemberRegisterSelectDepartmentFragmentBinding = binding
        ?: error("binding is not init")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(requireBinding()) {
            tvRegisterSelect1stDepartment.setOnClickListener { onClick1stDepartment() }
            tvRegisterSelect2ndDepartment.setOnClickListener { onClick2ndDepartment() }
            tvSelectDepartmentNext.setOnClickListener { onClickNextEvent() }
            tvSelectDepartmentPrevious.setOnClickListener { onClickPreviousEvent() }
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
            onRegisterDataSetListener?.onRegisterMajorDataSetListener(getMajorList())
            registerDepartmentNextEventListener?.invoke()
        }
    }

    private fun onClickPreviousEvent() {
        registerDepartmentPreviousEvnetListener?.invoke()
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
}
