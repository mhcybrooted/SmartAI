package app.dev.mahmudul.hasan.smartai.features.student.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.dev.mahmudul.hasan.smartai.features.student.home.domain.StudentHomeRepository
import app.dev.smartacademicinfrastructure.CourseModel
import app.dev.smartacademicinfrastructure.StudentDataModel
import com.appdevmhr.dpi_sai.di.Resourse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentHomeViewModel(
    private val studentHomeRepository: StudentHomeRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private var _studentProfile = MutableStateFlow<Resourse<StudentDataModel>>(Resourse.Loading)
    val studentProfileState = _studentProfile.asStateFlow()

    private var _studentCourses = MutableStateFlow<Resourse<List<CourseModel>>>(Resourse.Loading)
    val studentCoursesState = _studentCourses.asStateFlow()

    init {
        getStudentProfile(firebaseAuth.currentUser?.uid ?: "")

    }

    fun getStudentProfile(studentId: String) {
        viewModelScope.launch {
            _studentProfile.value = studentHomeRepository.getStudentProfile(studentId)
        }
    }

    fun getStudentCourses(studentDataModel: StudentDataModel) {
        viewModelScope.launch {
            _studentCourses.value = studentHomeRepository.getStudentCourses(studentDataModel)
        }
    }
    fun studentSignOut() {
        viewModelScope.launch {
            studentHomeRepository.studentSignOut()
        }
    }

}