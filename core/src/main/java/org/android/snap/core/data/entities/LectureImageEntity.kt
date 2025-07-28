package org.android.snap.core.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.android.snap.core.utils.DbConstants.LectureImage.FK_CHILD
import org.android.snap.core.utils.DbConstants.LectureImage.FK_PARENT
import org.android.snap.core.utils.DbConstants.LectureImage.TABLE_NAME
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Entity(
	tableName = TABLE_NAME,
	foreignKeys = [
		ForeignKey(
			entity = StudySessionEntity::class,
			parentColumns = [FK_PARENT],
			childColumns = [FK_CHILD],
			onDelete = ForeignKey.CASCADE,
		),
	],
	indices = [
		Index("sessionId"),
		Index("orderIndex"),
		Index("isUploaded"),
		Index("capturedAt"),
		Index("createdAt"),
		Index("updatedAt"),
	],
)
data class LectureImageEntity(
	@PrimaryKey val id: String,
	val sessionId: String,
	val fileName: String,
	// Local path
	val filePath: String,
	// Firebase Storage URL
	val firebaseUrl: String?,
	// Thumbnail local path
	val thumbnailPath: String?,
	// Thứ tự ảnh trong buổi học
	val orderIndex: Int,
	val capturedAt: LocalDateTime,
	val width: Int?,
	val height: Int?,
	val fileSize: Long?,
	val isUploaded: Boolean = false,
	val uploadedAt: LocalDateTime? = null,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
