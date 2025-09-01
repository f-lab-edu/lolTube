package com.flab.data.response.youtube

import com.flab.network.response.youtube.LolTubeSearchResponse
import com.flab.network.response.youtube.ThumbnailDetail
import com.flab.network.response.youtube.Thumbnails
import com.flab.network.response.youtube.VideoId
import com.flab.network.response.youtube.VideoItem
import com.flab.network.response.youtube.VideoSnippet
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LolTubeSearchResponseTest {

    @Test
    fun `LolTubeSearchResponse should be created with items`() {
        // Given
        val videoItems = listOf(
            VideoItem(
                id = VideoId("testVideoId"),
                snippet = VideoSnippet(
                    title = "Test Video",
                    channelTitle = "Test Channel",
                    thumbnails = Thumbnails(high = ThumbnailDetail("https://test.com/image.jpg"))
                )
            )
        )

        // When
        val response = LolTubeSearchResponse(items = videoItems)

        // Then
        assertEquals(videoItems, response.items)
        assertEquals(1, response.items.size)
        assertEquals("testVideoId", response.items[0].id.videoId)
    }

    @Test
    fun `VideoItem should be created with id and snippet`() {
        // Given
        val videoId = VideoId("sampleVideoId")
        val snippet = VideoSnippet(
            title = "Sample Title",
            channelTitle = "Sample Channel",
            thumbnails = Thumbnails(high = ThumbnailDetail("https://sample.com/thumb.jpg"))
        )

        // When
        val videoItem = VideoItem(id = videoId, snippet = snippet)

        // Then
        assertEquals(videoId, videoItem.id)
        assertEquals(snippet, videoItem.snippet)
        assertEquals("sampleVideoId", videoItem.id.videoId)
        assertEquals("Sample Title", videoItem.snippet.title)
    }

    @Test
    fun `VideoId should handle null videoId`() {
        // When
        val videoId = VideoId(videoId = null)

        // Then
        assertNull(videoId.videoId)
    }

    @Test
    fun `VideoSnippet should be created with all properties`() {
        // Given
        val title = "Test Title"
        val channelTitle = "Test Channel"
        val thumbnails = Thumbnails(high = ThumbnailDetail("https://test.com/thumbnail.jpg"))

        // When
        val snippet = VideoSnippet(
            title = title,
            channelTitle = channelTitle,
            thumbnails = thumbnails
        )

        // Then
        assertEquals(title, snippet.title)
        assertEquals(channelTitle, snippet.channelTitle)
        assertEquals(thumbnails, snippet.thumbnails)
    }

    @Test
    fun `Thumbnails should handle null high thumbnail`() {
        // When
        val thumbnails = Thumbnails(high = null)

        // Then
        assertNull(thumbnails.high)
    }

    @Test
    fun `ThumbnailDetail should be created with url`() {
        // Given
        val url = "https://example.com/thumbnail.jpg"

        // When
        val thumbnailDetail = ThumbnailDetail(url = url)

        // Then
        assertEquals(url, thumbnailDetail.url)
    }

    @Test
    fun `data classes should support equality comparison`() {
        // Given
        val thumbnailDetail1 = ThumbnailDetail("https://test.com/image.jpg")
        val thumbnailDetail2 = ThumbnailDetail("https://test.com/image.jpg")
        val thumbnailDetail3 = ThumbnailDetail("https://different.com/image.jpg")

        // Then
        assertEquals(thumbnailDetail1, thumbnailDetail2)
        assertEquals(thumbnailDetail1.hashCode(), thumbnailDetail2.hashCode())
        assert(thumbnailDetail1 != thumbnailDetail3)
    }
}
