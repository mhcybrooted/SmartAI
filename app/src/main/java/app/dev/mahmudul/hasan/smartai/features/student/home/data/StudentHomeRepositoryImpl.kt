package app.dev.mahmudul.hasan.smartai.features.student.home.data

import app.dev.mahmudul.hasan.smartai.features.student.home.domain.StudentHomeRepository
import app.dev.smartacademicinfrastructure.CourseModel
import app.dev.smartacademicinfrastructure.StudentDataModel
import com.appdevmhr.dpi_sai.di.Resourse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class StudentHomeRepositoryImpl(private val firestore: FirebaseFirestore,private val firebaseAuth: FirebaseAuth) : StudentHomeRepository {
    override suspend fun getStudentCourses(studentDataModel: StudentDataModel): Resourse<List<CourseModel>> {
        return try {
            val result = firestore.collection("CourseData").document(studentDataModel.session).collection(studentDataModel.department)
                .document(studentDataModel.semester).collection(studentDataModel.shift).document(studentDataModel.group).collection("Courses").get()
                .await()
            if (result.isEmpty) {
                return Resourse.Failure(Exception("No Data Found"))
            }
            val store = result.toObjects(CourseModel::class.java)
            return Resourse.Success(store)
        } catch (e: Exception) {
            Resourse.Failure(e)
        }
    }

    override suspend fun getStudentProfile(studentId: String): Resourse<StudentDataModel> {
        return try {
            val data = firestore.collection("StudentList").document(studentId).get().await()
            val student = data.toObject(StudentDataModel::class.java)
            if (student != null) {
                return Resourse.Success(student)
            } else {
                return Resourse.Failure(Exception("No Data Found"))
            }
        } catch (e: Exception) {
            Resourse.Failure(e)
        }
    }

    override suspend fun studentSignOut() {
        firebaseAuth.signOut()
    }
}