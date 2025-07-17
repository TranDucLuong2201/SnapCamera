package org.campusquest.core.data.mapper

import org.campusquest.core.data.local.entities.ExamEntity
import org.campusquest.core.data.local.entities.NotificationEntity
import org.campusquest.core.data.local.entities.PhotoEntity
import org.campusquest.core.data.local.entities.ScheduleEntity
import org.campusquest.core.data.local.entities.SemesterEntity
import org.campusquest.core.domain.model.Exam
import org.campusquest.core.domain.model.Notification
import org.campusquest.core.domain.model.Photo
import org.campusquest.core.domain.model.Schedule
import org.campusquest.core.domain.model.Semester

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

fun ScheduleEntity.toDomain(): Schedule {
    return Schedule(
        id = id,
        subject = subject,
        dayOfWeek = dayOfWeek,
        startTime = startTime,
        endTime = endTime,
        room = room,
        active = active,
        semesterId = semesterId,
        createdAt = createdAt
    )
}

fun Schedule.toEntity(): ScheduleEntity {
    return ScheduleEntity(
        id = id,
        subject = subject,
        dayOfWeek = dayOfWeek,
        startTime = startTime,
        endTime = endTime,
        room = room,
        active = active,
        semesterId = semesterId,
        createdAt = createdAt
    )
}

fun PhotoEntity.toDomain(): Photo {
    return Photo(
        id = id,
        path = path,
        subject = subject,
        takenAt = takenAt,
        day = day,
        note = note,
        scheduleId = scheduleId,
        semesterId = semesterId,
        fileSize = fileSize,
        isUploaded = isUploaded
    )
}

fun Photo.toEntity(): PhotoEntity {
    return PhotoEntity(
        id = id,
        path = path,
        subject = subject,
        takenAt = takenAt,
        day = day,
        note = note,
        scheduleId = scheduleId,
        semesterId = semesterId,
        fileSize = fileSize,
        isUploaded = isUploaded
    )
}

fun ExamEntity.toDomain(): Exam {
    return Exam(
        id = id,
        subject = subject,
        examDate = examDate,
        type = type,
        room = room,
        note = note,
        scheduleId = scheduleId,
        semesterId = semesterId,
        isCompleted = isCompleted,
        createdAt = createdAt
    )
}

fun Exam.toEntity(): ExamEntity {
    return ExamEntity(
        id = id,
        subject = subject,
        examDate = examDate,
        type = type,
        room = room,
        note = note,
        scheduleId = scheduleId,
        semesterId = semesterId,
        isCompleted = isCompleted,
        createdAt = createdAt
    )
}

fun SemesterEntity.toDomain(): Semester {
    return Semester(
        id = id,
        name = name,
        startDate = startDate,
        endDate = endDate,
        active = active,
        createdAt = createdAt
    )
}

fun Semester.toEntity(): SemesterEntity {
    return SemesterEntity(
        id = id,
        name = name,
        startDate = startDate,
        endDate = endDate,
        active = active,
        createdAt = createdAt
    )
}

fun NotificationEntity.toDomain(): Notification {
    return Notification(
        id = id,
        title = title,
        message = message,
        type = type,
        relatedId = relatedId,
        scheduledTime = scheduledTime,
        isRead = isRead,
        createdAt = createdAt
    )
}

fun Notification.toEntity(): NotificationEntity {
    return NotificationEntity(
        id = id,
        title = title,
        message = message,
        type = type,
        relatedId = relatedId,
        scheduledTime = scheduledTime,
        isRead = isRead,
        createdAt = createdAt
    )
}