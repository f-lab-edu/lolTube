package com.flab.data.mapper

import com.flab.domain.model.Video
import com.flab.network.response.youtube.ThumbnailDetail
import com.flab.network.response.youtube.Thumbnails
import com.flab.network.response.youtube.VideoId
import com.flab.network.response.youtube.VideoItem
import com.flab.network.response.youtube.VideoSnippet
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class VideoMapperTest {

    @Test
    fun `VideoItem을 Video로 매핑할 때 모든 필드가 올바르게 변환된다`() {
        // Given
        val videoItem = VideoItem(
            id = VideoId("test_video_id_123"),
            snippet = VideoSnippet(
                title = "테스트 비디오 제목",
                channelTitle = "테스트 채널",
                thumbnails = Thumbnails(
                    high = ThumbnailDetail("https://example.com/thumbnail.jpg")
                )
            )
        )

        // When
        val video = videoItem.toDomain()

        // Then
        assertEquals("test_video_id_123", video.videoId)
        assertEquals("테스트 비디오 제목", video.title)
        assertEquals("테스트 채널", video.channelTitle)
        assertEquals("https://example.com/thumbnail.jpg", video.thumbnailUrl)
    }

    @Test
    fun `VideoId가 null일 때 빈 문자열로 변환된다`() {
        // Given
        val videoItem = VideoItem(
            id = VideoId(null), // null videoId
            snippet = VideoSnippet(
                title = "제목",
                channelTitle = "채널",
                thumbnails = Thumbnails(
                    high = ThumbnailDetail("https://example.com/thumb.jpg")
                )
            )
        )

        // When
        val video = videoItem.toDomain()

        // Then
        assertEquals("", video.videoId) // null이 빈 문자열로 변환
        assertEquals("제목", video.title)
        assertEquals("채널", video.channelTitle)
        assertEquals("https://example.com/thumb.jpg", video.thumbnailUrl)
    }

    @Test
    fun `high 썸네일이 null일 때 thumbnailUrl이 null로 설정된다`() {
        // Given
        val videoItem = VideoItem(
            id = VideoId("video_with_no_thumbnail"),
            snippet = VideoSnippet(
                title = "썸네일 없는 비디오",
                channelTitle = "노썸네일 채널",
                thumbnails = Thumbnails(high = null) // high thumbnail이 null
            )
        )

        // When
        val video = videoItem.toDomain()

        // Then
        assertEquals("video_with_no_thumbnail", video.videoId)
        assertEquals("썸네일 없는 비디오", video.title)
        assertEquals("노썸네일 채널", video.channelTitle)
        assertNull(video.thumbnailUrl) // null로 설정됨
    }

    @Test
    fun `VideoId와 high 썸네일이 모두 null인 경우`() {
        // Given
        val videoItem = VideoItem(
            id = VideoId(null), // null videoId
            snippet = VideoSnippet(
                title = "모든 것이 null",
                channelTitle = "NULL 채널",
                thumbnails = Thumbnails(high = null) // high thumbnail도 null
            )
        )

        // When
        val video = videoItem.toDomain()

        // Then
        assertEquals("", video.videoId) // null이 빈 문자열로 변환
        assertEquals("모든 것이 null", video.title)
        assertEquals("NULL 채널", video.channelTitle)
        assertNull(video.thumbnailUrl) // null로 유지
    }

    @Test
    fun `특수문자가 포함된 제목과 채널명이 올바르게 매핑된다`() {
        // Given
        val videoItem = VideoItem(
            id = VideoId("special_chars_video"),
            snippet = VideoSnippet(
                title = "🎮 LOL 하이라이트 #1 - 대박!! (꿀팁)",
                channelTitle = "게임마스터 TV ⭐",
                thumbnails = Thumbnails(
                    high = ThumbnailDetail("https://example.com/special.jpg")
                )
            )
        )

        // When
        val video = videoItem.toDomain()

        // Then
        assertEquals("special_chars_video", video.videoId)
        assertEquals("🎮 LOL 하이라이트 #1 - 대박!! (꿀팁)", video.title)
        assertEquals("게임마스터 TV ⭐", video.channelTitle)
        assertEquals("https://example.com/special.jpg", video.thumbnailUrl)
    }

    @Test
    fun `빈 문자열 제목과 채널명도 올바르게 처리된다`() {
        // Given
        val videoItem = VideoItem(
            id = VideoId("empty_strings_video"),
            snippet = VideoSnippet(
                title = "", // 빈 제목
                channelTitle = "", // 빈 채널명
                thumbnails = Thumbnails(
                    high = ThumbnailDetail("")
                )
            )
        )

        // When
        val video = videoItem.toDomain()

        // Then
        assertEquals("empty_strings_video", video.videoId)
        assertEquals("", video.title) // 빈 문자열 유지
        assertEquals("", video.channelTitle) // 빈 문자열 유지
        assertEquals("", video.thumbnailUrl) // 빈 URL 유지
    }

    @Test
    fun `매우 긴 URL과 텍스트도 올바르게 처리된다`() {
        // Given
        val longTitle = "A".repeat(1000) // 1000자 제목
        val longChannelTitle = "B".repeat(500) // 500자 채널명
        val longUrl = "https://example.com/" + "c".repeat(2000) + ".jpg" // 긴 URL

        val videoItem = VideoItem(
            id = VideoId("long_content_video"),
            snippet = VideoSnippet(
                title = longTitle,
                channelTitle = longChannelTitle,
                thumbnails = Thumbnails(
                    high = ThumbnailDetail(longUrl)
                )
            )
        )

        // When
        val video = videoItem.toDomain()

        // Then
        assertEquals("long_content_video", video.videoId)
        assertEquals(longTitle, video.title)
        assertEquals(longChannelTitle, video.channelTitle)
        assertEquals(longUrl, video.thumbnailUrl)

        // 길이 검증
        assertEquals(1000, video.title.length)
        assertEquals(500, video.channelTitle.length)
        assertTrue("URL이 충분히 길어야 함", video.thumbnailUrl!!.length > 2000)
    }
}
