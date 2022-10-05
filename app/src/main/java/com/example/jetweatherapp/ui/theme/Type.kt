package com.example.jetweatherapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.jetweatherapp.R

val fonts = FontFamily(
    Font(R.font.lato_regular),
    Font(R.font.lato_bold, weight = FontWeight.Bold),
    Font(R.font.lato_thin, weight = FontWeight.Thin),
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    )
)

val TypographyLight = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = 18.sp
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        color = Color.White,
        fontSize = 12.sp
    ),
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 100.sp,
        color = Color.White
    ),
    h2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Thin,
        color = Color.White,
        fontSize = 20.sp
    )
)
