package com.example.githubapps.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapps.util.Resource
import com.example.githubapps.ui.adapter.UserAdapter
import com.example.githubapps.data.response.UserResponse
import com.example.githubapps.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var adapter: UserAdapter? = null
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setSearchView()

        homeViewModel.listUser.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    adapter?.submitList(it.data)
                    onSuccess()
                }
                is Resource.Loading -> onLoading()
                is Resource.Error -> onError()
                is Resource.Empty -> onEmpty()
            }
        }
    }

    private fun setUpRecyclerView(){
        adapter = UserAdapter {
            itemUserClicked(it)
        }
        binding.rvUsers.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUsers.adapter = adapter
    }

    private fun setSearchView(){
        with(binding){
            searchView.setupWithSearchBar(searchBar)

            searchView.editText
                .setOnEditorActionListener{ _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    homeViewModel.getListUser(searchView.text.toString())
                    false
                }
        }
    }

    private fun onSuccess(){
        binding.apply {
            pbUserList.visibility = GONE
            rvUsers.visibility = VISIBLE
            viewEmpty.root.visibility = GONE
            viewError.root.visibility = GONE
        }
    }

    private fun onLoading (){
        binding.apply {
            pbUserList.visibility = VISIBLE
            rvUsers.visibility = GONE
            viewEmpty.root.visibility = GONE
            viewError.root.visibility = GONE
        }
    }

    private fun onError() {
        binding.apply {
            pbUserList.visibility = GONE
            rvUsers.visibility = GONE
            viewEmpty.root.visibility = GONE
            viewError.root.visibility = VISIBLE
        }
    }

    private fun onEmpty(){
        binding.apply {
            pbUserList.visibility = GONE
            rvUsers.visibility = GONE
            viewEmpty.root.visibility = VISIBLE
            viewError.root.visibility = GONE
        }
    }

    private fun itemUserClicked(it: UserResponse) {
        val actionClicked = HomeFragmentDirections.actionHomeFragmentToDetailUserFragment()
        actionClicked.user = it
        view?.findNavController()?.navigate(actionClicked)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}