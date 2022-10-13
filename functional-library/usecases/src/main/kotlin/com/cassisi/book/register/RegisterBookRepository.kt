package com.cassisi.book.register

import com.cassisi.book.BookId
import com.cassisi.book.BookRegistered

interface RegisterBookRepository {

    fun save(bookId: BookId, book: BookRegistered)

}