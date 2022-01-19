package ru.pinguin.library.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.pinguin.library.MainActivity
import ru.pinguin.library.R
import ru.pinguin.library.databinding.FragmentMainBinding
import ru.pinguin.library.models.Book
import ru.pinguin.library.ui.adapters.BookAdapter
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.round
import kotlin.random.Random

class MainFragment : Fragment() {

    private lateinit var floatingActionButton: FloatingActionButton

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter

    private var viewBinding: FragmentMainBinding? = null
    private val binding get() = viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding = FragmentMainBinding.inflate(layoutInflater)
        initRecyclerView(view)
        val books = mutableListOf<Book>()
        for (i in 1..10) {
            books.add(Book("", "Title of Lorem Ipsum", BigDecimal(Random(123).nextDouble() * 5).setScale(2, RoundingMode.CEILING).toDouble()))
        }
        bookAdapter.setList(books)

        floatingActionButton = view.findViewById(R.id.main_btn_create)
        floatingActionButton.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_mainFragment_to_createBookFragment,
                Bundle().apply
                { putString("id", "-1") })
        }
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        bookAdapter =
            BookAdapter {
                (activity as MainActivity).navController.navigate(
                    R.id.action_mainFragment_to_bookFragment
                )
            }
        val manager = LinearLayoutManager(activity)
        recyclerView.layoutManager = manager
        recyclerView.adapter = bookAdapter

        val refreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_container)
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false
        }
    }

}