package app.dev.mahmudul.hasan.smartai.features.course.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.dev.mahmudul.hasan.smartai.features.course.home.domain.CourseHomeRepository
import app.dev.smartacademicinfrastructure.CourseModel
import app.dev.smartacademicinfrastructure.StudentDataModel
import com.appdevmhr.dpi_sai.di.Resourse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CourseHomeViewModel(
    private val courseHomeRepository: CourseHomeRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private var _courseData = MutableStateFlow<Resourse<CourseModel>>(Resourse.Loading)
    val courseDataState = _courseData.asStateFlow()
    private var _currentStudent = MutableStateFlow<String>("")
    val currentStudent = _currentStudent.asStateFlow()

    init {
        _currentStudent.value = firebaseAuth.currentUser?.uid ?: ""
    }


    fun getCourseData(courseID: String) {
        viewModelScope.launch {
            _courseData.value = courseHomeRepository.getCourseData(_currentStudent.value, courseID)

        }
    }

}