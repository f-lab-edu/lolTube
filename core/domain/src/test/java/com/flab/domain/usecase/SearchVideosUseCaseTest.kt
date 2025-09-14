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
    fun `invoke는 기본 매개변수로 repository에서 비디오 리스트를 반환해야 한다`() = runTest {
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
    fun `invoke는 사용자 정의 매개변수로 비디오 리스트를 반환해야 한다`() = runTest {
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
    fun `invoke는 비디오를 찾을 수 없을 때 빈 리스트를 반환해야 한다`() = runTest {
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
    fun `invoke는 repository 예외를 전파해야 한다`() = runTest {
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
    fun `invoke는 쿼리의 특수 문자를 처리해야 한다`() = runTest {
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
