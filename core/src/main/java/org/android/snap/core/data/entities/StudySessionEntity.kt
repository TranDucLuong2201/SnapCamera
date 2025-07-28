package org.android.snap.core.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.android.snap.core.utils.DbConstants.StudySession.FK_CHILD
import org.android.snap.core.utils.DbConstants.StudySession.FK_PARENT
import org.android.snap.core.utils.DbConstants.StudySession.TABLE_NAME
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Entity(
	tableName = TABLE_NAME,
	foreignKeys = [
		ForeignKey(
			entity = SubjectEntity::class,
			parentColumns = [FK_PARENT],
			childColumns = [FK_CHILD],
			onDelete = ForeignKey.CASCADE,
		),
	],
	indices = [
		Index("subjectId"),
		Index("date"),
		Index("sessionNumber"),
		Index("isLiveMode"),
		Index("startTime"),
		Index("endTime"),
		Index("createdAt"),
		Index("updatedAt"),
	],
)
data class StudySessionEntity(
	@PrimaryKey val id: String,
	val subjectId: String,
	// Ngày học
	val date: LocalDate,
	// Buổi thứ mấy trong ngày (1,2,3...)
	val sessionNumber: Int,
	// Tiêu đề buổi học
	val title: String?,
	val description: String?,
	// Chế độ ghi chép trực tiếp
	val isLiveMode: Boolean = false,
	val startTime: LocalDateTime? = null,
	val endTime: LocalDateTime? = null,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
