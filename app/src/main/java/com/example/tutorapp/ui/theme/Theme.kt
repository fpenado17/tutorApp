package com.example.tutorapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = RojoUESDark,
    onPrimary = Color.Black,
    background = Negro,
    onBackground = Color.White,
    surface = GrisOscuro,
    onSurface = Color.White,
    secondary = PrincipalAquaDark,
    onSecondary = Color.Black,
    surfaceVariant = Color.White,
    secondaryContainer= Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = RojoUES,
    onPrimary = Color.White,
    background = Blanco,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    secondary = PrincipalAqua,
    onSecondary = Color.Black,
    surfaceVariant = Color.White,
    secondaryContainer= Color.White
)

@Composable
fun TutorAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}