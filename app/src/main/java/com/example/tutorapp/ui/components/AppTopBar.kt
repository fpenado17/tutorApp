import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutorapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(darkTheme: Boolean = isSystemInDarkTheme()) {
    val logo = if (darkTheme) R.drawable.tutor_app_logo_blanco else R.drawable.tutor_app_logo
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = "Logo UES",
                    modifier = Modifier
                        .size(width = 60.dp, height = 55.dp)
                        .padding(end = 10.dp)
                )
                Text(
                    text = stringResource(R.string.nav_info_universidad),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 17.sp
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}