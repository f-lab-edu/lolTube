package com.flab.home.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.flab.designsystem.theme.LolTubeTheme

@Composable
fun VideoTitle(
    title: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 2
) {
    Text(
        text = title,
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Medium
        ),
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
private fun VideoTitlePreview() {
    LolTubeTheme {
        VideoTitle(title = "[LOL] 프레이 유나라 : 유나라는 이 맛이지~")
    }
}
