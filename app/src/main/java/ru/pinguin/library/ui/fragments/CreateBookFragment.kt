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
import ru.pinguin.library.models.CreateBookPage
import ru.pinguin.library.ui.adapters.BookInfoAdapter
import ru.pinguin.library.ui.adapters.CreateBookAdapter

class CreateBookFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var bookInfoAdapter: CreateBookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = view.findViewById<TabLayout>(R.id.create_tab_layout)
        bookInfoAdapter = CreateBookAdapter(this)
        viewPager = view.findViewById(R.id.create_pager)
        viewPager.adapter = bookInfoAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = CreateBookPage.values()[position].toString()
        }.attach()
    }

    fun switchTab(position: Int, smooth: Boolean) {
        viewPager.setCurrentItem(position, smooth)
    }

    companion object {
        fun newInstance(position: Int, createBookFragment: CreateBookFragment): Fragment {
            return if (position == CreateBookPage.ISBN.ordinal) {
                CreateByIsbnFragment(createBookFragment)
            } else BookFromScratchFragment()
        }
    }
}