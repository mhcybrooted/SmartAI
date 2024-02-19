package app.dev.mahmudul.hasan.smartai.features.student.profile.domain

import app.dev.smartacademicinfrastructure.StudentDataModel
import com.appdevmhr.dpi_sai.di.Resourse

interface StudentProfileRepository {
    suspend fun updateStudentProfile(updateStudentMap: Map<String,String>): Resourse<Boolean>
}