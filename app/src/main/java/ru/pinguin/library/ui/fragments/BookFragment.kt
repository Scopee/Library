package ru.pinguin.library.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.pinguin.library.R
import ru.pinguin.library.models.BookInfoPage
import ru.pinguin.library.ui.adapters.BookInfoAdapter

class BookFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var bookInfoAdapter: BookInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        bookInfoAdapter = BookInfoAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = bookInfoAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = BookInfoPage.values()[position].toString()
        }.attach()
    }

    companion object {
        fun newInstance(position: Int): Fragment {
            return if (position == BookInfoPage.INFO.ordinal) {
                BookInfoFragment()
            } else AnalyticsFragment()
        }

    }

}