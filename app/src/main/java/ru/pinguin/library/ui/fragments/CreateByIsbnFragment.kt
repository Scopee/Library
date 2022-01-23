package ru.pinguin.library.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.pinguin.library.R
import ru.pinguin.library.databinding.FragmentCreateByIsbnBinding
import ru.pinguin.library.network.books.BooksApiService
import ru.pinguin.library.network.books.BooksRemoteRepository
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.*
import retrofit2.HttpException
import ru.pinguin.library.MainActivity
import ru.pinguin.library.databinding.FragmentMainBinding
import ru.pinguin.library.models.Book
import java.lang.Exception
import kotlin.math.log


class CreateByIsbnFragment(private val createBookFragment: CreateBookFragment) : Fragment() {
    private lateinit var isbn: EditText
    private lateinit var bookRepository: BooksRemoteRepository

    private var viewBinding: FragmentCreateByIsbnBinding? = null
    private val binding get() = viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentCreateByIsbnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bookRepository = BooksRemoteRepository(BooksApiService.service)
        isbn = binding.createIsbnText
        binding.createIsbnButton.setOnClickListener { tryCreateByIsbn(isbn.text.toString()) }
    }

    private fun tryCreateByIsbn(isbn: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val createBookByIsbn = bookRepository.createBookByIsbn(isbn)
                CoroutineScope(Dispatchers.Main).launch {
                    (activity as MainActivity).navController.popBackStack()
                }
            } catch (e: HttpException) {
                Log.e(TAG, "tryCreateByIsbn: ", e)
                CoroutineScope(Dispatchers.Main).launch {
                    createBookFragment.switchTab(1, false)
                }
            }
        }

    }

    companion object {
        private const val TAG = "CreateByIsbnFragment"
    }
}