package com.cassisi.book

import com.cassisi.reader.ReaderId

class BookAlreadyLoan(val bookId: BookId) : Throwable()

class BookReservedByOtherReader(
    val requestedReader: ReaderId,
    val currentReader: ReaderId
) : Throwable()

class BookNotReserved(val bookId: BookId): Throwable()

class BookNotLent(val bookId: BookId): Throwable()

class BookAlreadyReserved(val bookId: BookId): Throwable()