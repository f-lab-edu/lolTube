package com.flab.domain.usecase

import com.flab.domain.model.Video
import com.flab.domain.repository.VideoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class SearchVideosUseCaseTest {

    @Mock
    private lateinit var videoRepository: VideoRepository

    private lateinit var searchVideosUseCase: SearchVideosUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        searchVideosUseCase = SearchVideosUseCase(videoRepository)
    }

    @Test
    fun `invoke should return video list from repository with default parameters`() = runTest {
        // Given
        val query = "리그오브레전드"
        val expectedVideos = listOf(
            Video(
                videoId = "video1",
                title = "LOL 게임플레이",
                channelTitle = "게임채널",
                thumbnailUrl = "https://example.com/thumb1.jpg"
            ),
            Video(
                videoId = "video2",
                title = "LOL 하이라이트",
                channelTitle = "하이라이트채널",
                thumbnailUrl = "https://example.com/thumb2.jpg"
            )
        )

        whenever(
            videoRepository.searchVideos(
                query = query,
                maxResults = 10,
                regionCode = "KR"
            )
        ).thenReturn(expectedVideos)

        // When
        val result = searchVideosUseCase(query)

        // Then
        assertEquals(expectedVideos, result)
    }

    @Test
    fun `invoke should return video list with custom parameters`() = runTest {
        // Given
        val query = "롤드컵"
        val maxResults = 5
        val regionCode = "US"
        val expectedVideos = listOf(
            Video(
                videoId = "worlds1",
                title = "Worlds Championship",
                channelTitle = "Riot Games",
                thumbnailUrl = "https://example.com/worlds.jpg"
            )
        )

        whenever(
            videoRepository.searchVideos(
                query = query,
                maxResults = maxResults,
                regionCode = regionCode
            )
        ).thenReturn(expectedVideos)

        // When
        val result = searchVideosUseCase(query, maxResults, regionCode)

        // Then
        assertEquals(expectedVideos, result)
    }

    @Test
    fun `invoke should return empty list when no videos found`() = runTest {
        // Given
        val query = "존재하지않는검색어"
        val emptyList = emptyList<Video>()

        whenever(
            videoRepository.searchVideos(
                query = query,
                maxResults = 10,
                regionCode = "KR"
            )
        ).thenReturn(emptyList)

        // When
        val result = searchVideosUseCase(query)

        // Then
        assertEquals(emptyList, result)
    }

    @Test(expected = Exception::class)
    fun `invoke should propagate repository exceptions`() = runTest {
        // Given
        val query = "테스트"
        whenever(
            videoRepository.searchVideos(
                query = query,
                maxResults = 10,
                regionCode = "KR"
            )
        ).thenThrow(RuntimeException("Network error"))

        // When & Then
        searchVideosUseCase(query)
    }

    @Test
    fun `invoke should handle special characters in query`() = runTest {
        // Given
        val query = "LOL 2024 챔피언쉽 🏆"
        val expectedVideos = listOf(
            Video(
                videoId = "special1",
                title = "Special Championship",
                channelTitle = "Gaming",
                thumbnailUrl = null
            )
        )

        whenever(
            videoRepository.searchVideos(
                query = query,
                maxResults = 10,
                regionCode = "KR"
            )
        ).thenReturn(expectedVideos)

        // When
        val result = searchVideosUseCase(query)

        // Then
        assertEquals(expectedVideos, result)
        assertEquals(1, result.size)
        assertEquals("special1", result[0].videoId)
        assertEquals(null, result[0].thumbnailUrl)
    }
}
