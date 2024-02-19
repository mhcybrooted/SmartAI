package app.dev.mahmudul.hasan.smartai.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spr.jetpack_loading.components.indicators.PacmanIndicator

@Composable
fun ResultShowLoading(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxWidth(0.8F),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        PacmanIndicator(color = Color.Black, ballDiameter = 60f, canvasSize = 60.dp, animationDuration =100000)
        CircularProgressIndicator()
    }
}