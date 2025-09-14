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
    fun `LolTubeSearchResponse는 아이템과 함께 생성되어야 한다`() {
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
    fun `VideoItem은 id와 snippet으로 생성되어야 한다`() {
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
    fun `VideoId는 null videoId를 처리해야 한다`() {
        // When
        val videoId = VideoId(videoId = null)

        // Then
        assertNull(videoId.videoId)
    }

    @Test
    fun `VideoSnippet은 모든 속성과 함께 생성되어야 한다`() {
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
    fun `Thumbnails는 null high thumbnail을 처리해야 한다`() {
        // When
        val thumbnails = Thumbnails(high = null)

        // Then
        assertNull(thumbnails.high)
    }

    @Test
    fun `ThumbnailDetail은 url과 함께 생성되어야 한다`() {
        // Given
        val url = "https://example.com/thumbnail.jpg"

        // When
        val thumbnailDetail = ThumbnailDetail(url = url)

        // Then
        assertEquals(url, thumbnailDetail.url)
    }

    @Test
    fun `data class들은 동등성 비교를 지원해야 한다`() {
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
