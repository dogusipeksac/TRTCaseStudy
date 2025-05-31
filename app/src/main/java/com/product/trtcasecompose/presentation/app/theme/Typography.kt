package com.product.trtcasecompose.presentation.app.theme


import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.product.trtcasecompose.R

val Roboto = FontFamily(
    Font(R.font.roboto),
)

val AppTypography = Typography(
    h6 = androidx.compose.ui.text.TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    body1 = androidx.compose.ui.text.TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)
