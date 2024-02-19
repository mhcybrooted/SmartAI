package app.dev.mahmudul.hasan.smartai.features.student.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.dev.dpi_sai.component.SpacerHeight
import app.dev.mahmudul.hasan.smartai.R
import app.dev.mahmudul.hasan.smartai.features.destinations.CourseHomeDestination
import app.dev.mahmudul.hasan.smartai.features.destinations.SignInScreenDestination
import app.dev.mahmudul.hasan.smartai.features.destinations.StudentHomeScreenDestination
import app.dev.mahmudul.hasan.smartai.features.destinations.StudentProfileDestination
import app.dev.smartacademicinfrastructure.CourseModel
import com.appdevmhr.dpi_sai.di.Resourse
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun StudentHomeScreen(
    destination: DestinationsNavigator,
    vm: StudentHomeViewModel = koinViewModel()
) {
    val studentProfileState = vm.studentProfileState.collectAsState()
    val studentCoursesState = vm.studentCoursesState.collectAsState()



    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            SpacerHeight(height = 20)
            TopAppBar(title = { }, actions = {
                IconButton(onClick = {
                    vm.studentSignOut()
                    destination.navigate(SignInScreenDestination) {
                        popUpTo(StudentHomeScreenDestination) {
                            inclusive = true
                        }
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "logout",
                    )
                }
                IconButton(onClick = {
                    destination.navigate(StudentProfileDestination) {
                        popUpTo(StudentHomeScreenDestination) {
                            inclusive = true
                        }
                    }
                }) {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Profile",
                    )
                }
            })

            when (studentCoursesState.value) {
                is Resourse.Failure -> {
                    val e = studentCoursesState.value as Resourse.Failure
                    println("error : $e")
                }

                Resourse.Loading -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Loading...")
                    }
                }

                is Resourse.Success -> {
                    val result = studentCoursesState.value as Resourse.Success
                    ItemList(result.result, destination)
                }
            }

        }

    }
    when (studentProfileState.value) {
        is Resourse.Failure -> {
            destination.navigate(SignInScreenDestination) {
                popUpTo(StudentHomeScreenDestination) {
                    inclusive = true
                }
            }
        }

        Resourse.Loading -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Loading...")
            }
        }

        is Resourse.Success -> {
            val result = studentProfileState.value as Resourse.Success
            vm.getStudentCourses(result.result)
        }
    }
}

@Composable
fun ItemList(items: List<CourseModel>, destination: DestinationsNavigator) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items) { item ->
            ItemRow(item, destination)
        }
    }
}

@Composable
fun ItemRow(item: CourseModel, destination: DestinationsNavigator) {

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9F),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SpacerHeight(height = 40)
            Text(
                text = "SUBJECT NAME :  ${item.courseName.toUpperCase(Locale.ROOT)}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.W900
            )
            SpacerHeight(height = 20)
            Text(
                text = "DEPARTMENT :  ${item.courseDepartment.toUpperCase(Locale.ROOT)}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            SpacerHeight(height = 10)
            Text(
                text = "SESSION :  ${item.courseSession.toUpperCase(Locale.ROOT)}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            SpacerHeight(height = 10)
            Text(
                text = "SHIFT :  ${item.courseShift.toUpperCase(Locale.ROOT)}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            SpacerHeight(height = 10)
            Text(
                text = "GROUP :  ${item.courseGroup.toUpperCase(Locale.ROOT)}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            SpacerHeight(height = 20)
            OutlinedButton(
                onClick = {
                          destination.navigate(CourseHomeDestination(item.courseCode))
                },
                modifier = Modifier
                    .fillMaxWidth(0.8F),
            ) {
                Text(
                    text = "Let's Go To  ${item.courseName.toUpperCase()} ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(30.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = "Enter to Course")
            }
            SpacerHeight(height = 40)
        }
    }
    SpacerHeight(height = 20)

}
