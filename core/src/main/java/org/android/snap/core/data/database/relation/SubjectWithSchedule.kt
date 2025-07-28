package org.android.snap.core.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.android.snap.core.data.entities.ScheduleItemEntity
import org.android.snap.core.data.entities.SubjectEntity

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class SubjectWithSchedule(
	@Embedded val subject: SubjectEntity,
	@Relation(
		parentColumn = "id",
		entityColumn = "subjectId",
	)
	val scheduleItems: List<ScheduleItemEntity>,
)
