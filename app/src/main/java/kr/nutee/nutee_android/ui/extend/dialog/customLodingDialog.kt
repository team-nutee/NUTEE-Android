package kr.nutee.nutee_android.ui.extend.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.CustomLoadingDialogBinding

class CustomLodingDialog(private val activity: Activity){

	private lateinit var alertDialog:AlertDialog
	private lateinit var binding: CustomLoadingDialogBinding

	fun startLoadingDialog(){
		val bulider: AlertDialog.Builder = AlertDialog.Builder(activity)
		binding = CustomLoadingDialogBinding.inflate(LayoutInflater.from(activity))

		val layoutInflater:LayoutInflater = activity.layoutInflater
		bulider.setView(binding.root)
		//bulider.setView(layoutInflater.inflate(R.layout.custom_loading_dialog, null))
		bulider.setCancelable(false)

		alertDialog = bulider.create()
		alertDialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
		alertDialog.show()
	}

	fun dismissDialog(){
		alertDialog.dismiss()
	}
}