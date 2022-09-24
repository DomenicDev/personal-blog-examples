package com.cassisi.book

import com.cassisi.reader.ReaderId
import java.time.LocalDate

sealed interface Reservation

object NoReservation : Reservation

data class ActiveReservation(
    val readerId: ReaderId,
    val reservedAt: LocalDate,
    val expiresAt: LocalDate
) : Reservation