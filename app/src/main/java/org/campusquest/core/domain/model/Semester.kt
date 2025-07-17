package org.campusquest.core.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class Semester(
    val id: String,
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val active: Boolean,
    val createdAt: Long
) {
    fun getFormattedStartDate(): String =
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(startDate))

    fun getFormattedEndDate(): String =
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(endDate))

    fun isActive(): Boolean = active && System.currentTimeMillis() in startDate..endDate

    fun getDuration(): String {
        val duration = endDate - startDate
        val days = duration / (24 * 60 * 60 * 1000)
        return "$days ngày"
    }
}