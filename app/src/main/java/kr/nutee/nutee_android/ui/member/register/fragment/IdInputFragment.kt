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
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.member.DataValid
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener

/*
* Created by jinsu4755
* DESC: id입력 부분 Fragment
*/

class IdInputFragment : Fragment(),View.OnClickListener {

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
		return inflater.inflate(
			R.layout.member_register_id_input_fragment,
			container,
			false
		)
	}

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
		tv_id_input_next.setOnClickListener(this)
		tv_id_input_previous.setOnClickListener(this)
	}

	private fun buttonEnableEventMapping(){
		et_register_id_input.textChangedListener {id ->
			tv_id_input_next.isEnabled = !(id.isNullOrBlank()&&isVaildId(id))
		}
	}

	private fun isVaildId(id:Editable?):Boolean{
		if (dataValid.isValidId(id.toString())) {
			this.id = et_register_id_input.text.toString()
			tv_register_id_input_result_text.text = ""
			return true
		}
		this.id = null
		requireContext().showTextShake(
			tv_register_id_input_result_text,
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
			R.id.tv_id_input_next ->{
				idInputEventListener?.invoke(et_register_id_input, tv_register_id_input_result_text)
			}
			R.id.tv_id_input_previous->{
				registerIdPreviousEventListener?.invoke()
			}
		}
	}

	fun idInputCheckSuccessEvnet(){
		id = et_register_id_input.text.toString()
		onRegisterDataSetListener?.onRegisterIdDataSetListener(id!!)
		registerIdNextEventListener?.invoke()
	}
}