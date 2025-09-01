package com.flab.data.service

import com.flab.network.response.youtube.LolTubeSearchResponse
import com.flab.network.response.youtube.ThumbnailDetail
import com.flab.network.response.youtube.Thumbnails
import com.flab.network.response.youtube.VideoId
import com.flab.network.response.youtube.VideoItem
import com.flab.network.response.youtube.VideoSnippet
import com.flab.network.service.LolTubeService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class LolTubeServiceTest {

    @Mock
    private lateinit var lolTubeService: LolTubeService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `searchVideos should return LolTubeSearchResponse with video items`() = runTest {
        // Given
        val expectedResponse = LolTubeSearchResponse(
            items = listOf(
                VideoItem(
                    id = VideoId("testVideoId"),
                    snippet = VideoSnippet(
                        title = "Test Video Title",
                        channelTitle = "Test Channel",
                        thumbnails = Thumbnails(
                            high = ThumbnailDetail("https://test.com/thumbnail.jpg")
                        )
                    )
                )
            )
        )

        whenever(
            lolTubeService.searchVideos(
                apiKey = any(),
                part = any(),
                query = any(),
                type = any(),
                maxResults = any(),
                regionCode = any()
            )
        ).thenReturn(expectedResponse)

        // When
        val result = lolTubeService.searchVideos(
            apiKey = "test-api-key",
            query = "test query",
            maxResults = 10,
            regionCode = "KR"
        )

        // Then
        assertNotNull(result)
        assertEquals(1, result.items.size)

        val videoItem = result.items[0]
        assertEquals("testVideoId", videoItem.id.videoId)
        assertEquals("Test Video Title", videoItem.snippet.title)
        assertEquals("Test Channel", videoItem.snippet.channelTitle)
        assertEquals("https://test.com/thumbnail.jpg", videoItem.snippet.thumbnails.high?.url)
    }

    @Test
    fun `searchVideos should handle empty response`() = runTest {
        // Given
        val expectedResponse = LolTubeSearchResponse(items = emptyList())

        whenever(
            lolTubeService.searchVideos(
                apiKey = any(),
                part = any(),
                query = any(),
                type = any(),
                maxResults = any(),
                regionCode = any()
            )
        ).thenReturn(expectedResponse)

        // When
        val result = lolTubeService.searchVideos(
            apiKey = "test-api-key",
            query = "empty query"
        )

        // Then
        assertNotNull(result)
        assertEquals(0, result.items.size)
    }

    @Test
    fun `searchVideos should handle null videoId`() = runTest {
        // Given
        val expectedResponse = LolTubeSearchResponse(
            items = listOf(
                VideoItem(
                    id = VideoId(null), // null videoId case
                    snippet = VideoSnippet(
                        title = "Test Video",
                        channelTitle = "Test Channel",
                        thumbnails = Thumbnails(high = null) // null thumbnail case
                    )
                )
            )
        )

        whenever(
            lolTubeService.searchVideos(
                apiKey = any(),
                part = any(),
                query = any(),
                type = any(),
                maxResults = any(),
                regionCode = any()
            )
        ).thenReturn(expectedResponse)

        // When
        val result = lolTubeService.searchVideos(
            apiKey = "test-api-key",
            query = "test"
        )

        // Then
        assertNotNull(result)
        assertEquals(1, result.items.size)
        assertEquals(null, result.items[0].id.videoId)
        assertEquals(null, result.items[0].snippet.thumbnails.high)
    }
}
