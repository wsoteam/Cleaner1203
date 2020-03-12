package dev.mem.rocket.sanya.Volume

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created by intag pc on 2/12/2017.
 */

class MyPagerAdapter(fm: FragmentManager, internal var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        when (position) {
            0 -> {
                return PhoneBoosterFrag()
            }
            1 -> {
                return BatterySaverFrag()
            }
            2 -> {
                return CPUCoolerFrag()
            }
            3 -> {
                return JunkCleanerFrag()
            }
            else -> {
                return NoAdsFrag.MyFragment()
            }
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}
