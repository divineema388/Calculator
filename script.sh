#!/bin/bash

# Define the base directory for Kotlin source files
BASE_JAVA_DIR="app/src/main/java"
PACKAGE_DIR="mt/ui/theme"
FULL_PATH="$BASE_JAVA_DIR/$PACKAGE_DIR"

# Ensure the directory exists
echo "Creating directory: $FULL_PATH"
mkdir -p "$FULL_PATH"

# Define package name
PACKAGE_NAME="mt.ui.theme"

# --- Create Color.kt ---
COLOR_FILE="$FULL_PATH/Color.kt"
echo "Creating $COLOR_FILE"
cat << EOF > "$COLOR_FILE"
package $PACKAGE_NAME

import androidx.compose.ui.graphics.Color

// Define your app's color palette here

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// You can add more custom colors here
val DarkBlue = Color(0xFF00008B)
val LightGray = Color(0xFFD3D3D3)
EOF

# --- Create Type.kt ---
TYPE_FILE="$FULL_PATH/Type.kt"
echo "Creating $TYPE_FILE"
cat << EOF > "$TYPE_FILE"
package $PACKAGE_NAME

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
EOF

# --- Create Shape.kt ---
SHAPE_FILE="$FULL_PATH/Shape.kt"
echo "Creating $SHAPE_FILE"
cat << EOF > "$SHAPE_FILE"
package $PACKAGE_NAME

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(0.dp) // Typically no corner radius for large
)
EOF

# --- Create Theme.kt ---
THEME_FILE="$FULL_PATH/Theme.kt"
echo "Creating $THEME_FILE"
cat << EOF > "$THEME_FILE"
package $PACKAGE_NAME

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    // Add more colors if needed for dark theme
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    // Add more colors if needed for light theme
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

@Composable
fun CalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes, // Use the Shapes object from Shape.kt
        content = content
    )
}
EOF

echo "Kotlin styling files created successfully in $FULL_PATH!"
echo "Remember to update your MainActivity.kt to use CalculatorTheme."
