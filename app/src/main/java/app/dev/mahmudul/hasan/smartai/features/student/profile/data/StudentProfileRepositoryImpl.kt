package app.dev.mahmudul.hasan.smartai.features.student.profile.data

import app.dev.mahmudul.hasan.smartai.features.student.profile.domain.StudentProfileRepository
import app.dev.smartacademicinfrastructure.StudentDataModel
import com.appdevmhr.dpi_sai.di.Resourse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class StudentProfileRepositoryImpl(private val firestore: FirebaseFirestore,private val firebaseAuth: FirebaseAuth) : StudentProfileRepository {

    override suspend fun updateStudentProfile(updateStudentMap: Map<String, String>): Resourse<Boolean> {
        return try {
            val studentID = firebaseAuth.currentUser!!.uid
            firestore.collection("StudentList").document(studentID).update(updateStudentMap).await()
            Resourse.Success(true)
        } catch (e: Exception) {
            Resourse.Failure(e)
        }

    }


}