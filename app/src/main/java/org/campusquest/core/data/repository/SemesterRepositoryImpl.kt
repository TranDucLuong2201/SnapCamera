package org.campusquest.core.data.repository

import org.campusquest.core.data.local.dao.SemesterDao
import org.campusquest.core.data.mapper.toDomain
import org.campusquest.core.data.mapper.toEntity
import org.campusquest.core.domain.model.Semester
import org.campusquest.core.domain.repository.SemesterRepository
import java.util.UUID

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class SemesterRepositoryImpl(
    private val semesterDao: SemesterDao
) : SemesterRepository {

    override suspend fun getAllSemesters(): List<Semester> {
        return semesterDao.getAllSemesters().map { it.toDomain() }
    }

    override suspend fun getActiveSemester(): Semester? {
        return semesterDao.getActiveSemester()?.toDomain()
    }

    override suspend fun insertSemester(semester: Semester) {
        semesterDao.insertSemester(semester.toEntity())
    }

    override suspend fun updateSemester(semester: Semester) {
        semesterDao.updateSemester(semester.toEntity())
    }

    override suspend fun activateSemester(semesterId: String) {
        semesterDao.deactivateAllSemesters()
        semesterDao.activateSemester(semesterId)
    }

    override suspend fun createNewSemester(name: String, startDate: Long, endDate: Long): Semester {
        val semester = Semester(
            id = UUID.randomUUID().toString(),
            name = name,
            startDate = startDate,
            endDate = endDate,
            active = false,
            createdAt = System.currentTimeMillis()
        )

        semesterDao.insertSemester(semester.toEntity())
        return semester
    }

    override suspend fun getSemesterById(id: String): Semester? {
        return semesterDao.getSemesterById(id)?.toDomain()
    }
}