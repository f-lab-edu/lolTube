package com.flab.shorts.webview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.flab.designsystem.R
import com.flab.shorts.model.ErrorType

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

@Composable
fun ErrorStateIndicator(
    errorType: ErrorType,
    onReload: () -> Unit,
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

    val errorMessage = when (errorType) {
        ErrorType.NETWORK -> stringResource(R.string.error_network)
        ErrorType.QUOTA_EXCEEDED -> stringResource(R.string.error_quota_exceeded)
        ErrorType.UNKNOWN -> stringResource(R.string.error_loading_videos)
    }

    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = errorMessage,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onReload,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.button_reload)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(stringResource(R.string.button_reload))
            }
        }
    }
}

@Preview
@Composable
private fun LoadingIndicatorPreview() {
    LoadingIndicator()
}

@Preview
@Composable
private fun ErrorStateIndicatorPreview() {
    ErrorStateIndicator(
        errorType = ErrorType.NETWORK,
        onReload = {}
    )
}
