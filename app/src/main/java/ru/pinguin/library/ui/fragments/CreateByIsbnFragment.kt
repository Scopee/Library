package ru.pinguin.library.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.pinguin.library.R
import ru.pinguin.library.databinding.FragmentCreateByIsbnBinding
import ru.pinguin.library.network.books.BooksApiService
import ru.pinguin.library.network.books.BooksRemoteRepository
import androidx.viewpager2.widget.ViewPager2
import ru.pinguin.library.models.Book


class CreateByIsbnFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var isbn: EditText
    private lateinit var bookRepository: BooksRemoteRepository

    private var viewBinding: FragmentCreateByIsbnBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentCreateByIsbnBinding.inflate(inflater, container, false)
        bookRepository = BooksRemoteRepository(BooksApiService.service)
        return inflater.inflate(R.layout.fragment_create_by_isbn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding = FragmentCreateByIsbnBinding.inflate(layoutInflater)
        viewPager = view.findViewById(R.id.create_pager)
        floatingActionButton = view.findViewById(R.id.create_isbn_button)
        isbn = view.findViewById(R.id.create_isbn_text)
        floatingActionButton.setOnClickListener {tryCreateByIsbn(isbn.text.toString())}
    }

    fun tryCreateByIsbn(isbn: String) {
        val call: Call<Book> = BooksApiService.service.getBookInfo(isbn)
        call.enqueue(object : Callback<Book?>() {
            fun onResponse(call: Call<Book?>?, response: Response<Book?>) {
                val bookResponse: Book = response.body()
                val responseCode: String = bookResponse.getResponseCode()
                if (responseCode == "404") {
                    // а если я просто оставлю так, то заработает?
                    viewPager.setCurrentItem(1, false)
                }
                else {
                    // тут наверное всякие поляшки bookResponse нужно куда-то передавать
                }
            }
        })
    }
}