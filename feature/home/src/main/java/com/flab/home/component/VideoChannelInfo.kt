package com.flab.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.flab.designsystem.theme.LolTubeTheme

@Composable
fun VideoChannelInfo(
    channelTitle: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ChannelName(channelTitle = channelTitle)
    }
}

@Composable
fun ChannelName(
    channelTitle: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = channelTitle,
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
private fun VideoChannelInfoPreview() {
    LolTubeTheme {
        VideoChannelInfo(channelTitle = "프레이 TV")
    }
}
