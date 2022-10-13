package com.cassisi.book.borrow

import com.cassisi.book.Book
import com.cassisi.book.BookBorrowed
import com.cassisi.book.BookId

interface BorrowBookRepository {

    fun get(bookId: BookId): Book

    fun save(bookId: BookId, changes: BookBorrowed)

}