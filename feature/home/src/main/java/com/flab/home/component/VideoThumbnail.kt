package com.flab.home.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.flab.designsystem.theme.LolTubeTheme

@Composable
fun VideoThumbnail(
    thumbnailUrl: String?,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = thumbnailUrl,
        contentDescription = contentDescription,
        modifier = modifier
            .size(width = 120.dp, height = 90.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
private fun VideoThumbnailPreview() {
    LolTubeTheme {
        VideoThumbnail(
            thumbnailUrl = "https://i.ytimg.com/vi/TpFNF9chDHc/hqdefault.jpg",
            contentDescription = "프레이 TV 썸네일"
        )
    }
}
