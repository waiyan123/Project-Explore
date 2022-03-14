package com.itachi.explore.adapters.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class UserProfilePagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragmentList = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment : Fragment) {
        fragmentList.add(fragment)
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }

}



