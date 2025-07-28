package org.android.snap.snapcamera.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.android.snap.core.common.SyncResult
import org.android.snap.core.data.dao.ScheduleItemDao
import org.android.snap.core.data.mapper.toDomain
import org.android.snap.core.data.mapper.toEntity
import org.android.snap.core.domain.model.ScheduleItem
import org.android.snap.core.domain.repository.ScheduleRepository
import java.time.DayOfWeek
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class ScheduleRepositoryImpl @Inject constructor(
	private val scheduleItemDao: ScheduleItemDao,
) : ScheduleRepository {

	override fun getScheduleBySubject(subjectId: String): Flow<Result<List<ScheduleItem>>> =
		scheduleItemDao.getScheduleBySubject(subjectId).map { scheduleEntities ->
			Result.success(scheduleEntities.map { it.toDomain() })
		}.catch { e ->
			e.printStackTrace()
			emit(Result.failure(e))
		}

	override suspend fun getScheduleByDay(dayOfWeek: DayOfWeek): Result<List<ScheduleItem>> =
		try {
			val scheduleItems = scheduleItemDao.getScheduleByDay(dayOfWeek)
			Result.success(scheduleItems.map { it.toDomain() })
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun getTodaySchedule(): Result<List<ScheduleItem>> =
		try {
			val today = java.time.LocalDate.now()
			val dayOfWeek = today.dayOfWeek
			val scheduleItems = scheduleItemDao.getTodaySchedule(dayOfWeek)
			Result.success(scheduleItems.map { it.toDomain() })
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun createScheduleItem(item: ScheduleItem): Result<Unit> =
		try {
			val scheduleEntity = item.toEntity()
			scheduleItemDao.insertScheduleItem(scheduleEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun updateScheduleItem(item: ScheduleItem): Result<Unit> =
		try {
			val scheduleEntity = item.toEntity()
			scheduleItemDao.updateScheduleItem(scheduleEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun deleteScheduleItem(itemId: String): Result<Unit> =
		try {
			val scheduleItem = scheduleItemDao.getScheduleItemById(itemId)
			scheduleItem?.let { scheduleItemDao.deleteScheduleItem(it) }
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun deleteSchedulesBySubject(subjectId: String): Result<Unit> =
		try {
			scheduleItemDao.deleteSchedulesBySubject(subjectId)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun syncSchedule(): Result<SyncResult> =
		try {
			// TODO: Implement sync logic with Firebase
			Result.success(SyncResult(true, 0, 0))
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}
}
