package app.dev.mahmudul.hasan.smartai.features.course.home.domain

import androidx.compose.runtime.MutableState
import app.dev.mahmudul.hasan.smartai.features.course.home.data.MessageItemModel
import app.dev.smartacademicinfrastructure.CourseModel
import app.dev.smartacademicinfrastructure.StudentDataModel
import com.appdevmhr.dpi_sai.di.Resourse
import kotlinx.coroutines.flow.Flow

interface CourseHomeRepository {
    suspend fun getCourseData(courseID:String) : Flow<Resourse<CourseModel>>
    suspend fun getMessages(courseID: String) : Flow<Resourse<List<MessageItemModel>>>
    suspend fun StoreMessage(message:String, courseCode: String,) : Flow<Resourse<Boolean>>
}