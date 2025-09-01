package com.flab.data.repository

import com.flab.network.response.youtube.LolTubeSearchResponse
import com.flab.network.response.youtube.ThumbnailDetail
import com.flab.network.response.youtube.Thumbnails
import com.flab.network.response.youtube.VideoId
import com.flab.network.response.youtube.VideoItem
import com.flab.network.response.youtube.VideoSnippet
import com.flab.network.service.LolTubeService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class LolTubeRepositoryTest {

    @Mock
    private lateinit var lolTubeService: LolTubeService
    private lateinit var repository: VideoRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = VideoRepositoryImpl(lolTubeService)
    }

    @Test
    fun `searchVideos should return mapped domain objects`() = runTest {
        // Given
        val query = "test"
        val maxResults = 10
        val regionCode = "KR"

        val mockResponse = LolTubeSearchResponse(
            items = listOf(
                VideoItem(
                    id = VideoId("testId"),
                    snippet = VideoSnippet(
                        title = "Test Title",
                        channelTitle = "Test Channel",
                        thumbnails = Thumbnails(
                            high = ThumbnailDetail("http://test.com/thumb.jpg")
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
        ).thenReturn(mockResponse)

        // When
        val result = repository.searchVideos(query, maxResults, regionCode)

        // Then
        assertEquals(1, result.size)
        assertEquals("testId", result[0].videoId)
        assertEquals("Test Title", result[0].title)
        assertEquals("Test Channel", result[0].channelTitle)
        assertEquals("http://test.com/thumb.jpg", result[0].thumbnailUrl)
    }

    @Test(expected = Exception::class)
    fun `searchVideos should propagate service exceptions`() = runTest {
        // Given
        whenever(
            lolTubeService.searchVideos(
                apiKey = any(),
                part = any(),
                query = any(),
                type = any(),
                maxResults = any(),
                regionCode = any()
            )
        ).thenThrow(RuntimeException("API Error"))

        // When & Then
        repository.searchVideos("test", 10, "KR")
    }
}
