package org.campusquest.android.ui.theme

import androidx.compose.material3.Typography
import org.campusquest.android.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val fontaineFont = FontFamily(
    Font(R.font.fontaine_regular, FontWeight.Bold),
    Font(R.font.fontaine_extrude),
    Font(R.font.fontaine_rough),
)


val InterFontFamily = FontFamily(
    Font(R.font.inter_28pt_regular, FontWeight.Normal),
    Font(R.font.inter_24pt_medium, FontWeight.Medium),
    Font(R.font.inter_24pt_bold, FontWeight.Bold)
)

// Typography
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
)