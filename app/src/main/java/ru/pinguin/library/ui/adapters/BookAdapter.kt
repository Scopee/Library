package ru.pinguin.library.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.pinguin.library.R
import ru.pinguin.library.models.Book

class BookAdapter(private val callback: (String) -> Unit) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var books: List<Book> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater =
            LayoutInflater.from(parent.context)
        return BookViewHolder(
            inflater.inflate(R.layout.book_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
        holder.itemView.setOnClickListener {
            callback(books[position].isbn)
        }
    }

    override fun getItemCount() = books.size


    fun setList(books: List<Book>) {
        this.books = books
        notifyDataSetChanged()
    }

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bookTitle: TextView = view.findViewById(R.id.book_title)
        private val bookRate: TextView = view.findViewById(R.id.book_rate)

        fun bind(book: Book) {
            bookTitle.text = book.title
            bookRate.text = book.rate.toString()
        }
    }
}