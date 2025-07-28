package org.android.snap.core.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.android.snap.core.data.entities.SemesterEntity
import org.android.snap.core.data.entities.SubjectEntity

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class SemesterWithSubjects(
	@Embedded val semester: SemesterEntity,
	@Relation(
		parentColumn = "id",
		entityColumn = "semesterId",
	)
	val subjects: List<SubjectEntity>,
)
