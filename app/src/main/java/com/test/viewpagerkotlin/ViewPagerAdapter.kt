package com.test.viewpagerkotlin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter  (
    fa: FragmentManager
) : FragmentStatePagerAdapter(fa) {
    private var fragments = mutableListOf<Fragment>()


    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    fun removeFragment(fragment: Fragment) {
        fragments.remove(fragment)
    }

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment {
       return fragments.get(position)
    }
}