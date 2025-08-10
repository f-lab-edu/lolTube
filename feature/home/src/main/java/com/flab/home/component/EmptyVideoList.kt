package com.flab.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.flab.designsystem.theme.LolTubeTheme

@Composable
fun EmptyVideoList(
    modifier: Modifier = Modifier,
    message: String = "비디오 목록이 비어있습니다"
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyVideoListPreview() {
    LolTubeTheme {
        EmptyVideoList(
            message = "No videos available"
        )
    }
}
