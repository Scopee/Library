package ru.pinguin.library.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.pinguin.library.ui.fragments.BookFragment

class BookInfoAdapter(private val fragment: Fragment, private val isbn: String) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = BookFragment.newInstance(position, isbn)

}