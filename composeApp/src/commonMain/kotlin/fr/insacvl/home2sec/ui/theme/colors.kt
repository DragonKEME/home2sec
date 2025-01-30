import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

private val DarkBlueBackgroundTheme = Color(0xFF100D22)
private val DarkPurpleTheme = Color(0xFF211A44)
private val PurpleBlueTheme = Color(0xFF5C5792)
private val LightPurpleTheme = Color(0xFF9F8DC3)
private val LightPurplePinkTheme = Color(0xFFBE9CC7)
private val LightPinkTheme = Color(0xFFE3BAD5)

val Home2SecColorScheme = darkColorScheme(
    background = DarkBlueBackgroundTheme,
    onBackground = Color.White,

    surface = DarkBlueBackgroundTheme,
    surfaceVariant = DarkPurpleTheme,
    primary = LightPurplePinkTheme,
    secondary = LightPinkTheme,
    tertiary = LightPurpleTheme,



    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)