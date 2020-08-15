package kr.nutee.nutee_android.ui.main.fragment.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.nutee.nutee_android.R

class SearchView : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_view)

		var testList=ArrayList<String>()

		for(i in 0..9){
			testList.add("test")
		}

	}
}