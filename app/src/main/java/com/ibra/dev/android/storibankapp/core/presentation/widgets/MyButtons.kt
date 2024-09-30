package com.ibra.dev.android.storibankapp.core.presentation.widgets

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ibra.dev.android.storibankapp.ui.theme.mainBlue

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnable: Boolean = true,
    action: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = action,
        enabled = isEnable,
        colors = ButtonDefaults.buttonColors(
            containerColor = mainBlue,
            disabledContainerColor = Color.Gray
        )
    ) {
        Text(text = text)
    }
}