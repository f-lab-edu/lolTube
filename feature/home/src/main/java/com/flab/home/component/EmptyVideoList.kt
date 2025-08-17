package com.flab.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.flab.designsystem.theme.LolTubeTheme
import com.flab.designsystem.R

@Composable
fun EmptyVideoList(
    modifier: Modifier = Modifier,
    message: String = stringResource(id = R.string.empty_video_list)
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
