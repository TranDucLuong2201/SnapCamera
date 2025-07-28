package org.android.snap.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.domain.model.ScheduleItem
import java.time.DayOfWeek

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface ScheduleRepository {
	fun getScheduleBySubject(subjectId: String): Flow<Result<List<ScheduleItem>>>
	suspend fun getScheduleByDay(dayOfWeek: DayOfWeek): Result<List<ScheduleItem>>
	suspend fun getTodaySchedule(): Result<List<ScheduleItem>>
	suspend fun createScheduleItem(item: ScheduleItem): Result<Unit>
	suspend fun updateScheduleItem(item: ScheduleItem): Result<Unit>
	suspend fun deleteScheduleItem(itemId: String): Result<Unit>
	suspend fun deleteSchedulesBySubject(subjectId: String): Result<Unit>
	suspend fun syncSchedule(): Result<SyncResult>
}
