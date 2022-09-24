package com.cassisi.book.register

import com.cassisi.book.Book

interface RegisterBookRepository {

    fun save(book: Book)

}