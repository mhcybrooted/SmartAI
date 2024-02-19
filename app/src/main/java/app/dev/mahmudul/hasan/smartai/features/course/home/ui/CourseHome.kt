package app.dev.mahmudul.hasan.smartai.features.course.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import app.dev.smartacademicinfrastructure.CourseModel
import com.appdevmhr.dpi_sai.di.Resourse
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun CourseHome(
    vm: CourseHomeViewModel = koinViewModel(),
    courseID: String
) {
    val courseDataState by vm.courseDataState.collectAsState()

    LaunchedEffect(courseID) {
        vm.getCourseData(courseID)
    }




}