package app.dev.mahmudul.hasan.smartai.features.student.home.domain

import app.dev.smartacademicinfrastructure.CourseModel
import app.dev.smartacademicinfrastructure.StudentDataModel
import com.appdevmhr.dpi_sai.di.Resourse

interface StudentHomeRepository {
    suspend fun getStudentCourses (studentDataModel: StudentDataModel): Resourse<List<CourseModel>>
    suspend fun getStudentProfile (studentId: String): Resourse<StudentDataModel>
    suspend fun studentSignOut()
}