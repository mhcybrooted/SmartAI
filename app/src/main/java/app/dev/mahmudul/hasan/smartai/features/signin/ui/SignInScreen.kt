package app.dev.mahmudul.hasan.smartai.features.signin.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.dev.dpi_sai.component.InputField
import app.dev.mahmudul.hasan.smartai.component.ResultShowInfo
import app.dev.mahmudul.hasan.smartai.features.destinations.SignInScreenDestination
import app.dev.mahmudul.hasan.smartai.features.destinations.SignUpScreenDestination
import app.dev.mahmudul.hasan.smartai.features.destinations.StudentHomeScreenDestination
import com.google.firebase.auth.FirebaseAuth
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
@Destination
@RootNavGraph(start = true)
fun SignInScreen(destination: DestinationsNavigator, vm: SignInViewModel = koinViewModel()) {
    val signInState by vm.signInState.collectAsStateWithLifecycle()
    val checkRoleState by vm.checkRoleState.collectAsState()
    val firebaseAuth = koinInject<FirebaseAuth>()
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    var isInfo by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = signInState.isLoading) {
        if (firebaseAuth.currentUser != null) {
            vm.checkRole(firebaseAuth.currentUser!!.uid)
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Sign In",
            fontSize = 40.sp,
            fontWeight = FontWeight.W900,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        InputField("Email", email) {
            email = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        InputField(name = "Password", value = password) {
            password = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(onClick = {
            isLoading = true
            isInfo = false
            vm.signIn(email, password)
        }, modifier = Modifier.fillMaxWidth(0.8F)) {
            Text(
                text = "Login",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.4F)
            )
        }


        Spacer(modifier = Modifier.height(30.dp))
        ResultShowInfo(visible = signInState.data.isNotEmpty(), data = "Login Successful")
        Spacer(modifier = Modifier.height(30.dp))
        ResultShowInfo(data = signInState.error, visible = signInState.error.isNotEmpty())
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "No account? Sign up",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable {
                destination.navigate(SignUpScreenDestination)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Admin ? Please click here",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable {
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        if (signInState.isLoading || checkRoleState.isLoading) {
            Spacer(modifier = Modifier.height(10.dp))
            CircularProgressIndicator()
        }

    }
    when(checkRoleState.data){
        "Student" -> {
            destination.navigate(StudentHomeScreenDestination){
                popUpTo(SignInScreenDestination){
                    inclusive = true
                }
            }
        }
        "Teacher" -> {
            println("Teacher")
        }
    }
}

