package ru.pinguin.library.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.pinguin.library.MainActivity
import ru.pinguin.library.R
import ru.pinguin.library.databinding.FragmentBookFromScratchBinding
import ru.pinguin.library.databinding.FragmentMainBinding
import ru.pinguin.library.models.Book
import ru.pinguin.library.network.books.BooksApiService
import ru.pinguin.library.network.books.BooksRemoteRepository

class BookFromScratchFragment : Fragment() {

    private var viewBinding: FragmentBookFromScratchBinding? = null
    private val binding get() = viewBinding!!
    private lateinit var bookRepository: BooksRemoteRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentBookFromScratchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bookRepository = BooksRemoteRepository(BooksApiService.service)
        binding.createScratchButton.setOnClickListener { createBook() }
    }

    private fun createBook() {
        val book = Book(
            binding.createIsbn.text.toString(),
            binding.createTitle.text.toString(),
            binding.createAuthor.text.toString(),
            binding.createDescription.text.toString(),
            binding.createYear.text.toString().toInt()
        )
        CoroutineScope(Dispatchers.IO).launch {
            bookRepository.createBook(book)
            CoroutineScope(Dispatchers.Main).launch {
                (activity as MainActivity).navController.popBackStack()
            }
        }
    }


}