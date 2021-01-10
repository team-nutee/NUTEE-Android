package kr.nutee.nutee_android.ui.main.fragment.profile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment_profile_written_post.*
import kr.nutee.nutee_android.R

class WrittenPostFragment: Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_fragment_profile_written_post, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		rv_profile_written_post.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		rv_profile_written_post.setHasFixedSize(true)

	}
}