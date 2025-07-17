package org.campusquest.core.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    tertiary = DarkTertiary,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onPrimary = DarkOnPrimary,
    onSecondary = DarkOnSecondary,
    onTertiary = DarkOnTertiary,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkOnSurface.copy(alpha = 0.7f),
    outline = DarkOnSurface.copy(alpha = 0.2f),
    surfaceTint = DarkPrimary,
    inverseSurface = DarkSurface,
    inverseOnSurface = DarkOnSurface,
    inversePrimary = DarkPrimary,
    error = ErrorColor,
    onError = DarkOnPrimary,
    errorContainer = ErrorColor.copy(alpha = 0.2f),
    onErrorContainer = ErrorColor
)
private val FocusLightColorScheme = lightColorScheme(
    primary = FocusLightPrimary,
    secondary = FocusLightSecondary,
    background = FocusLightBackground,
    surface = FocusLightSurface,
    onPrimary = FocusLightOnPrimary,
    onBackground = FocusLightOnBackground,
    onSurface = FocusLightOnSurface,
    error = FocusLightError,
    onError = Color.White
)

private val FocusDarkColorScheme = darkColorScheme(
    primary = FocusDarkPrimary,
    secondary = FocusDarkSecondary,
    background = FocusDarkBackground,
    surface = FocusDarkSurface,
    onPrimary = FocusDarkOnPrimary,
    onBackground = FocusDarkOnBackground,
    onSurface = FocusDarkOnSurface,
    error = FocusDarkError,
    onError = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    tertiary = LightTertiary,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurfaceVariant,
    onPrimary = LightOnPrimary,
    onSecondary = LightOnSecondary,
    onTertiary = LightOnTertiary,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface,
    onSurfaceVariant = LightOnSurface.copy(alpha = 0.7f),
    outline = LightOnSurface.copy(alpha = 0.2f),
    surfaceTint = LightPrimary,
    inverseSurface = DarkSurface,
    inverseOnSurface = DarkOnSurface,
    inversePrimary = DarkPrimary,
    error = ErrorColor,
    onError = LightOnPrimary,
    errorContainer = ErrorColor.copy(alpha = 0.1f),
    onErrorContainer = ErrorColor
)

@Composable
fun CampusQuestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val controller = WindowCompat.getInsetsController(window, view)
            controller.isAppearanceLightStatusBars = !darkTheme
            controller.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Extension functions for custom colors
@Composable fun successColor() = SuccessColor
@Composable fun warningColor() = WarningColor
@Composable fun errorColor() = ErrorColor
@Composable fun infoColor() = InfoColor
@Composable fun vocabularyColor() = VocabularyColor
@Composable fun grammarColor() = GrammarColor
@Composable fun listeningColor() = ListeningColor
@Composable fun speakingColor() = SpeakingColor
@Composable fun readingColor() = ReadingColor
@Composable fun writingColor() = WritingColor
@Composable fun aiAccentColor() = AIAccentColor
@Composable fun friendColor() = FriendColor
