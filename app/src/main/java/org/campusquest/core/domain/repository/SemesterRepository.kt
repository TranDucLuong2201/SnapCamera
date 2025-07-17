package org.campusquest.core.domain.repository

import org.campusquest.core.domain.model.Semester

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface SemesterRepository {
    suspend fun getAllSemesters(): List<Semester>
    suspend fun getActiveSemester(): Semester?
    suspend fun insertSemester(semester: Semester)
    suspend fun updateSemester(semester: Semester)
    suspend fun activateSemester(semesterId: String)
    suspend fun createNewSemester(name: String, startDate: Long, endDate: Long): Semester
    suspend fun getSemesterById(id: String): Semester?
}