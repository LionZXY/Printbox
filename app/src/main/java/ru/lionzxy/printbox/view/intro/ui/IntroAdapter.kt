package ru.lionzxy.printbox.view.intro.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class IntroAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val screenLists = Array(count, { i -> IntroFragment.newInstance(IntroEnum.values()[i]) })

    override fun getItem(position: Int): Fragment {
        return screenLists[position]
    }

    override fun getCount(): Int {
        return IntroEnum.values().size
    }
}