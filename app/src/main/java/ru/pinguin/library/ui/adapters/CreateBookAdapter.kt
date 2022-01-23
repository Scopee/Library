package ru.pinguin.library.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.pinguin.library.ui.fragments.CreateBookFragment

class CreateBookAdapter(private val fragment: CreateBookFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = CreateBookFragment.newInstance(position, fragment)
}