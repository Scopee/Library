package ru.pinguin.library.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.pinguin.library.MainActivity
import ru.pinguin.library.database.UsersDatabase
import ru.pinguin.library.database.UsersRepository
import ru.pinguin.library.databinding.FragmentBookInfoBinding
import ru.pinguin.library.network.analytics.AnalyticsApiService
import ru.pinguin.library.network.analytics.AnalyticsRemoteRepository
import ru.pinguin.library.network.books.BooksApiService
import ru.pinguin.library.network.books.BooksRemoteRepository
import ru.pinguin.library.viewmodel.BookInfoViewModel
import kotlin.math.roundToInt

class BookInfoFragment(private val isbn: String) : Fragment() {

    private var viewBinding: FragmentBookInfoBinding? = null
    private val binding get() = viewBinding!!

    private lateinit var analyticsRepository: AnalyticsRemoteRepository
    private lateinit var booksRepository: BooksRemoteRepository
    private lateinit var usersRepository: UsersRepository

    private lateinit var viewModel: BookInfoViewModel
    private var username: String? = "Anonymous"
    private var rated = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentBookInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userDao = UsersDatabase.getDatabase((activity as MainActivity)).userDao()
        analyticsRepository = AnalyticsRemoteRepository(AnalyticsApiService.service)
        usersRepository = UsersRepository(userDao)
        username = usersRepository.getUser()
        booksRepository = BooksRemoteRepository(BooksApiService.service)
        viewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return BookInfoViewModel(isbn, activity as MainActivity) as T
                }
            }).get(BookInfoViewModel::class.java)

        viewModel.liveData.observe(viewLifecycleOwner, {
            binding.infoTitle.setText(it.title)
            binding.infoAuthor.setText(it.authors)
            binding.infoDescription.setText(it.description)
        })

        CoroutineScope(Dispatchers.IO).launch {
            val rate = booksRepository.isRated(username ?: "Anonymous", isbn)
            if (rate.rated) {
                rated = true
                CoroutineScope(Dispatchers.Main).launch {
                    binding.infoRatingBar.setIsIndicator(true)
                    binding.infoRatingBar.rating = rate.rate.toFloat()
                }
            }
        }

        binding.infoRatingBar.setOnRatingBarChangeListener { bar, rating, fromUser ->
            rateBook(isbn, rating)
        }

    }

    private fun rateBook(isbn: String, rating: Float) {
        if (!rated) {
            CoroutineScope(Dispatchers.IO).launch {
                analyticsRepository.rateBook(isbn, username ?: "Anonymous", rating.roundToInt())
                CoroutineScope(Dispatchers.Main).launch {
                    binding.infoRatingBar.setIsIndicator(true)
                }
            }
        }
    }

}