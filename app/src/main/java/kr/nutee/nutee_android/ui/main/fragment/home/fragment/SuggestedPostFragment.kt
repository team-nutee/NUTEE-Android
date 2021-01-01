package kr.nutee.nutee_android.ui.main.fragment.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter
import kr.nutee.nutee_android.ui.main.fragment.home.HomeViewHolder

class SuggestedPostFragment : Fragment() {

	private lateinit var recyclerView:RecyclerView
	private lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.home_suggested_post_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		recyclerView= view.findViewById(R.id.rv_main_home_suggested_post)
		recyclerView.apply {
			layoutManager=LinearLayoutManager(this.context,
				LinearLayoutManager.VERTICAL, false)
			setHasFixedSize(true)
		}

	}
}