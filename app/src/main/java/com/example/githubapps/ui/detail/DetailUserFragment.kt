package com.example.githubapps.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.githubapps.R
import com.example.githubapps.util.Resource
import com.example.githubapps.ui.adapter.SectionPagerAdapter
import com.example.githubapps.data.response.DetailUserResponse
import com.example.githubapps.databinding.FragmentDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserFragment : Fragment() {

    private var _binding : FragmentDetailUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = DetailUserFragmentArgs.fromBundle(arguments as Bundle).user
        viewModel.getDetailUser(user?.username ?: "")
        viewModel.detailUser.observe(viewLifecycleOwner){
            when (it){
                is Resource.Success -> {
                    setDetailUser(it.data)
                    onSuccess()
                }
                is Resource.Loading ->{
                    onLoading()
                }
                is Resource.Error -> onError()
                is Resource.Empty -> onEmpty()
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.userName = user?.username.toString()
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabsFollow, binding.viewPager){tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


    }

    private fun onSuccess(){
        binding.apply {
            llFollow.visibility = VISIBLE
            pbDetailUser.visibility = GONE
            tvDetailUsername.visibility = VISIBLE
            cigUserProfile.visibility = VISIBLE
            viewError.root.visibility = GONE
            viewEmpty.root.visibility = GONE
        }
    }

    private fun onLoading(){
        binding.apply {
            llFollow.visibility = GONE
            pbDetailUser.visibility = VISIBLE
            tvDetailUsername.visibility = INVISIBLE
            cigUserProfile.visibility = INVISIBLE
            viewError.root.visibility = GONE
            viewEmpty.root.visibility = GONE
        }
    }

    private fun onError(){
        binding.apply {
            llFollow.visibility = GONE
            pbDetailUser.visibility = GONE
            tvDetailUsername.visibility = INVISIBLE
            cigUserProfile.visibility = INVISIBLE
            viewError.root.visibility = VISIBLE
            viewEmpty.root.visibility = GONE
        }
    }

    private fun onEmpty(){
        binding.apply {
            llFollow.visibility = GONE
            pbDetailUser.visibility = GONE
            tvDetailUsername.visibility = INVISIBLE
            cigUserProfile.visibility = INVISIBLE
            viewError.root.visibility = GONE
            viewEmpty.root.visibility = VISIBLE
        }
    }

    private fun setDetailUser(data: DetailUserResponse?) {
        binding.apply {
            tvDetailUsername.text = data?.username
            Glide.with(binding.cigUserProfile)
                .load(data?.avatarUrl)
                .centerCrop()
                .into(binding.cigUserProfile)
            val followersText = data?.followers + " Followers"
            val followingText = data?.following + " Following"
            tvTotalFollowers.text = followersText
            tvTotalFollowing.text = followingText
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        private val TAB_TITLES = intArrayOf(
            R.string.following_text,
            R.string.follower_text
        )
    }

}