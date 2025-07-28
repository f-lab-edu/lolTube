package com.flab.loltube.ui.main.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

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
