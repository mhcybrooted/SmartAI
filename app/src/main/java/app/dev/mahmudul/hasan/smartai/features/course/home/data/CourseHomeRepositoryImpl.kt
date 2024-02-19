package app.dev.mahmudul.hasan.smartai.features.course.home.data

import app.dev.mahmudul.hasan.smartai.features.course.home.domain.CourseHomeRepository
import app.dev.smartacademicinfrastructure.CourseModel
import app.dev.smartacademicinfrastructure.StudentDataModel
import com.appdevmhr.dpi_sai.di.Resourse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class CourseHomeRepositoryImpl(private val firestore: FirebaseFirestore) : CourseHomeRepository {
    override suspend fun getCourseData(studentID: String,courseID:String): Resourse<CourseModel> {

        return try {
            val data = firestore.collection("StudentList").document(studentID).get().await()
            val student = data.toObject(StudentDataModel::class.java)
            val result = firestore.collection("CourseData").document(student!!.session).collection(student.department)
                .document(student.semester).collection(student.shift).document(student.group).collection("Courses").document(courseID).get()
                .await()
            return Resourse.Success(result.toObject(CourseModel::class.java)!! )
        } catch (e: Exception) {
            Resourse.Failure(e)
        }
    }



}