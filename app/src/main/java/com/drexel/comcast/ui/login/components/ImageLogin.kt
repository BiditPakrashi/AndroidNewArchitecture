package com.drexel.comcast.ui.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.drexel.R

@Composable
fun ImageLogin(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.drexel),
        contentDescription = "Image Login",
        modifier = modifier
            .fillMaxWidth()
            .size(200.dp),
        alignment = Alignment.Center
    )
}
