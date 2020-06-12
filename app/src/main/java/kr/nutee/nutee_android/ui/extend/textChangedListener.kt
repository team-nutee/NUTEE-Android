package kr.nutee.nutee_android.ui.extend

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.textChangedListener(
	textChanged : (CharSequence?) -> Unit
)
{
	this.addTextChangedListener(object : TextWatcher {
		override fun afterTextChanged(s: Editable?) {
			textChanged(s)
		}

		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)=Unit

		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

	})
}