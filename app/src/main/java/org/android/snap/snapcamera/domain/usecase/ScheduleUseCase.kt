package org.android.snap.snapcamera.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import org.android.snap.core.common.SyncResult
import org.android.snap.core.domain.model.ScheduleItem
import org.android.snap.snapcamera.data.repository.ScheduleRepositoryImpl
import java.time.DayOfWeek
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class ScheduleUseCase @Inject constructor(
	private val scheduleRepository: ScheduleRepositoryImpl,
) {

	fun getScheduleBySubject(subjectId: String): Flow<Result<List<ScheduleItem>>> =
		scheduleRepository.getScheduleBySubject(subjectId)

	suspend fun getScheduleByDay(dayOfWeek: DayOfWeek): Result<List<ScheduleItem>> =
		scheduleRepository.getScheduleByDay(dayOfWeek)

	suspend fun getTodaySchedule(): Result<List<ScheduleItem>> =
		scheduleRepository.getTodaySchedule()

	suspend fun createScheduleItem(item: ScheduleItem): Result<Unit> =
		scheduleRepository.createScheduleItem(item)

	suspend fun updateScheduleItem(item: ScheduleItem): Result<Unit> =
		scheduleRepository.updateScheduleItem(item)

	suspend fun deleteScheduleItem(itemId: String): Result<Unit> =
		scheduleRepository.deleteScheduleItem(itemId)

	suspend fun deleteSchedulesBySubject(subjectId: String): Result<Unit> =
		scheduleRepository.deleteSchedulesBySubject(subjectId)

	suspend fun syncSchedule(): Result<SyncResult> =
		scheduleRepository.syncSchedule()
}
