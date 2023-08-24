package com.example.githubapps.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapps.util.Resource
import com.example.githubapps.ui.adapter.UserAdapter
import com.example.githubapps.data.response.UserResponse
import com.example.githubapps.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FollowViewModel>()
    private var adapter: UserAdapter? = null

    companion object{
        const val TAB_POSITION = "TAB_POSITION"
        const val DATA_USERNAME = "DATA_USERNAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(TAB_POSITION)
        arguments?.getString(DATA_USERNAME)?.let {
            viewModel.setUsername(it)
        }

        setUpRecyclerView()

        position?.let { viewModel.setPosition(it) }
        viewModel.listFollow.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    adapter?.submitList(it.data)
                    onSuccess()
                }
                is Resource.Loading -> {
                    onLoading()
                }
                is Resource.Empty -> onEmpty()

                is Resource.Error -> {
                    onError()
                }
            }
        }
        viewModel.getListFollow()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpRecyclerView(){
        adapter = UserAdapter {
            itemUserClicked(it)
        }
        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.adapter = adapter
    }


    private fun onSuccess(){
        binding.apply {
            pbFollowList.visibility = GONE
            rvFollow.visibility = VISIBLE
            viewError.root.visibility = GONE
            viewEmpty.root.visibility = GONE
        }
    }

    private fun onLoading(){
        binding.apply {
            pbFollowList.visibility = VISIBLE
            rvFollow.visibility = GONE
            viewError.root.visibility = GONE
            viewEmpty.root.visibility = GONE
        }
    }

    private fun onError(){
        binding.apply {
            pbFollowList.visibility = GONE
            rvFollow.visibility = GONE
            viewError.root.visibility = VISIBLE
            viewEmpty.root.visibility = GONE
        }
    }

    private fun onEmpty(){binding.apply {
        pbFollowList.visibility = GONE
        rvFollow.visibility = GONE
        viewError.root.visibility = GONE
        viewEmpty.root.visibility = VISIBLE
    }
    }

    private fun itemUserClicked(it: UserResponse) {
        Toast.makeText(requireActivity(), it.username, Toast.LENGTH_SHORT).show()
    }
}