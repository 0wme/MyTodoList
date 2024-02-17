package fr.iut.mytodolist.android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import fr.iut.mytodolist.android.R
import fr.iut.mytodolist.android.TodoPagerAdapter

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        val pagerAdapter = TodoPagerAdapter(childFragmentManager)
        viewPager.adapter = pagerAdapter

        viewPager.setCurrentItem(1)

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)


        return view
    }
}