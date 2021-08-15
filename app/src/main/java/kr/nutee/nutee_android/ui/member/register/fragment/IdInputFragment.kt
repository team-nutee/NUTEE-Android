package kr.nutee.nutee_android.ui.member.register.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.member_register_id_input_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.MemberRegisterEmailAuthFragmentBinding
import kr.nutee.nutee_android.databinding.MemberRegisterIdInputFragmentBinding
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.member.DataValid
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener

/*
* Created by jinsu4755
* DESC: id입력 부분 Fragment
*/

class IdInputFragment : Fragment(),View.OnClickListener {

	private var binding: MemberRegisterIdInputFragmentBinding? =null
	private var id:String? = null

	private var onRegisterDataSetListener: OnRegisterDataSetListener? = null
	private val dataValid = DataValid()

	private var idInputEventListener:((id: EditText,resultText:TextView)->Unit)? = null
	private var registerIdPreviousEventListener: (() -> Unit)? = null
	private var registerIdNextEventListener: (() -> Unit)? = null

	fun setIdInputEventListener(listener: (id: EditText, resultText: TextView) -> Unit) {
		this.idInputEventListener = listener
	}

	fun setRegisterIdPreviousEventListener(listener: () -> Unit) {
		this.registerIdPreviousEventListener = listener
	}

	fun setRegisterIdNextEvnetListener(listener: () -> Unit) {
		this.registerIdNextEventListener = listener
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = MemberRegisterIdInputFragmentBinding.inflate(inflater, container, false)
		return requireBinding().root
	}

	private fun requireBinding(): MemberRegisterIdInputFragmentBinding = binding
			?: error("binding is not init")

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context !is OnRegisterDataSetListener)
			throw RuntimeException(context.toString() + "must implement OnRegisterDataSetListener")
		onRegisterDataSetListener = context
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		idInputButtonEventMapping()
		buttonEnableEventMapping()
	}

	private fun idInputButtonEventMapping(){
		requireBinding().tvIdInputNext.setOnClickListener(this)
		requireBinding().tvIdInputPrevious.setOnClickListener(this)
	}

	private fun buttonEnableEventMapping(){
		with(requireBinding()){
			etRegisterIdInput.textChangedListener { id ->
				tvIdInputNext.isEnabled = !(id.isNullOrBlank()&&isVaildId(id))
			}
		}
	}

	private fun isVaildId(id:Editable?):Boolean{
		if (dataValid.isValidId(id.toString())) {
			this.id = requireBinding().etRegisterIdInput.text.toString()
			requireBinding().tvRegisterIdInputResultText.text = ""
			return true
		}
		this.id = null
		requireContext().showTextShake(
			requireBinding().tvRegisterIdInputResultText,
				"영문, 숫자만 이용하실 수 있습니다.",
				R.color.colorRed
		)
		return false
	}

	override fun onDetach() {
		super.onDetach()
		onRegisterDataSetListener = null
		id = null
	}

	override fun onClick(idFragmentButton: View?) {
		when (idFragmentButton!!.id) {
			requireBinding().tvIdInputNext.id ->{
				with(requireBinding()){
					idInputEventListener?.invoke(etRegisterIdInput, tvRegisterIdInputResultText)
				}
			}
			requireBinding().tvIdInputPrevious.id->{
				registerIdPreviousEventListener?.invoke()
			}
		}
	}

	fun idInputCheckSuccessEvnet(){
		id = requireBinding().etRegisterIdInput.text.toString()
		//id = et_register_id_input.text.toString()
		onRegisterDataSetListener?.onRegisterIdDataSetListener(id!!)
		registerIdNextEventListener?.invoke()
	}
}