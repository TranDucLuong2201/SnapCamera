package org.android.snap.core.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.android.snap.core.data.entities.LectureImageEntity
import org.android.snap.core.data.entities.StudySessionEntity

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class StudySessionWithImages(
	@Embedded val session: StudySessionEntity,
	@Relation(
		parentColumn = "id",
		entityColumn = "sessionId",
	)
	val images: List<LectureImageEntity>,
)
