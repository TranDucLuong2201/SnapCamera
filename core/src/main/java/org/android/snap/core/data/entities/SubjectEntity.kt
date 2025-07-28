package org.android.snap.core.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.android.snap.core.utils.DbConstants.Subject.FK_CHILD
import org.android.snap.core.utils.DbConstants.Subject.FK_PARENT
import org.android.snap.core.utils.DbConstants.Subject.TABLE_NAME
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Entity(
	tableName = TABLE_NAME,
	foreignKeys = [
		ForeignKey(
			entity = SemesterEntity::class,
			parentColumns = [FK_PARENT],
			childColumns = [FK_CHILD],
			onDelete = ForeignKey.CASCADE,
		),
	],
	indices = [
		Index("semesterId"),
		Index("isCompleted"),
		Index("code"),
		Index("createdAt"),
		Index("updatedAt"),
	],
)
data class SubjectEntity(
	@PrimaryKey val id: String,
	val semesterId: String,
	val name: String,
	// Mã môn học
	val code: String?,
	// Màu hiển thị
	val color: String?,
	val teacher: String?,
	val room: String?,
	val credits: Int?,
	// Đánh dấu hoàn thành
	val isCompleted: Boolean = false,
	val completedAt: LocalDateTime? = null,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
