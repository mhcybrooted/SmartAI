package app.dev.mahmudul.hasan.smartai.features.student.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.dev.mahmudul.hasan.smartai.features.student.home.domain.StudentHomeRepository
import app.dev.mahmudul.hasan.smartai.features.student.profile.domain.StudentProfileRepository
import app.dev.smartacademicinfrastructure.StudentDataModel
import com.appdevmhr.dpi_sai.di.Resourse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentProfileViewModel(
    private val studentProfileRepository: StudentProfileRepository,
    private val studentHomeRepository: StudentHomeRepository,
    firebaseAuth: FirebaseAuth
) : ViewModel() {

    private var _studentProfile = MutableStateFlow<Resourse<StudentDataModel>>(Resourse.Loading)
    val studentProfileState = _studentProfile.asStateFlow()
    private var _updateStudentProfile = MutableStateFlow<Resourse<Boolean>>(Resourse.Loading)
    val updateStudentProfileState = _updateStudentProfile.asStateFlow()

    init {
        getStudentProfile(firebaseAuth.currentUser?.uid ?: "")
    }

    fun getStudentProfile(studentId: String) {
        viewModelScope.launch {
            _studentProfile.value = studentHomeRepository.getStudentProfile(studentId)
        }
    }

    fun updateStudentProfile(updateStudentMap: Map<String,String>) {
        viewModelScope.launch {
            _updateStudentProfile.value = studentProfileRepository.updateStudentProfile(updateStudentMap)
        }
    }
}