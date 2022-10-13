package com.cassisi.book

import com.cassisi.reader.ReaderId
import java.time.LocalDate

sealed interface Loan

object AvailableForLoan : Loan

data class ActiveLoan (
    val loanId: LoanId,
    val readerId: ReaderId,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val extensions: Int
): Loan