package kr.nutee.nutee_android.ui.extend.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.CustomDialogBinding

fun Context.customDialog(msg: String, okClickEvent: () -> Unit) {
	val dialog = Dialog(this)
	val binding: CustomDialogBinding = CustomDialogBinding.inflate(LayoutInflater.from(this))

	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
	dialog.setCancelable(false)

	dialog.setContentView(binding.root)
	//dialog.setContentView(R.layout.custom_dialog)

	val body = binding.dialogText
	//val body = dialog.findViewById<TextView>(R.id.dialog_text)
	body.text = msg

	val okButton = binding.okButton
	//val okButton = dialog.findViewById<TextView>(R.id.okButton)
	val cancelButton = binding.cancelButton
	//val cancelButton = dialog.findViewById<TextView>(R.id.cancelButton)

	dialog.show()

	okButton.setOnClickListener {
		dialog.dismiss()
		okClickEvent()
	}
	cancelButton.setOnClickListener { dialog.dismiss() }
}
