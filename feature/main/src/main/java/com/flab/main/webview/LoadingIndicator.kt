package com.flab.main.webview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    withZIndex: Boolean = false
) {
    val boxModifier = if (withZIndex) {
        modifier
            .fillMaxSize()
            .background(Color.Black)
            .zIndex(1f)
    } else {
        modifier
            .fillMaxSize()
            .background(Color.Black)
    }

    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun LoadingIndicatorPreview() {
    LoadingIndicator()
}
