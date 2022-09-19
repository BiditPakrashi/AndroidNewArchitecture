package com.drexel.comcast.ui.login.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonSubmit(
    onclick: () -> Unit,
    enabled: Boolean,
    buttonText :String = "Login"
) {
    Button(
        onClick = onclick,
        modifier = Modifier.width(150.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 5.dp),
        enabled = enabled,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Blue
        )
    ) {
        Text(
            text = buttonText,
            fontSize = 25.sp,
            color = Color.White
        )
    }
}
