package com.example.githubapps.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubapps.ui.follow.FollowFragment
import com.example.githubapps.ui.follow.FollowFragment.Companion.DATA_USERNAME
import com.example.githubapps.ui.follow.FollowFragment.Companion.TAB_POSITION

class SectionPagerAdapter (fragment : Fragment) : FragmentStateAdapter (fragment) {
    var userName = ""
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(TAB_POSITION, position + 1)
            putString(DATA_USERNAME, userName)
        }
        return fragment //if(position == 0 ) fragment else followerFragment
    }
}