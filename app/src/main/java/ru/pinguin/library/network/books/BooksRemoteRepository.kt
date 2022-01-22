package ru.pinguin.library.network.books

import ru.pinguin.library.models.Book
import ru.pinguin.library.models.UpdateBookRequest
import ru.pinguin.library.utils.retry

class BooksRemoteRepository(private val booksApi: BooksApi) {

    suspend fun getBookInfo(isbn: String): Book = retry(times = 3) { booksApi.getBookInfo(isbn) }

    suspend fun createBook(book: Book) = retry(times = 3) { booksApi.createBook(book) }

    suspend fun updateBook(isbn: String, updateBookRequest: UpdateBookRequest) = retry(times = 3) { booksApi.updateBook(isbn, updateBookRequest) }

    suspend fun createBookByIsbn(isbn: String) = retry(times = 3) { booksApi.createBookByIsbn(isbn) }
}