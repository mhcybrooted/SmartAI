package app.dev.mahmudul.hasan.smartai.features.course.home.domain

import app.dev.smartacademicinfrastructure.CourseModel
import app.dev.smartacademicinfrastructure.StudentDataModel
import com.appdevmhr.dpi_sai.di.Resourse

interface CourseHomeRepository {
    suspend fun getCourseData(studentID: String,courseID:String) : Resourse<CourseModel>
}