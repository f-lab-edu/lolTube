package com.flab.data.repository

import com.flab.data.response.youtube.LolTubeSearchResponse
import com.flab.data.response.youtube.ThumbnailDetail
import com.flab.data.response.youtube.Thumbnails
import com.flab.data.response.youtube.VideoId
import com.flab.data.response.youtube.VideoItem
import com.flab.data.response.youtube.VideoSnippet
import com.flab.data.service.LolTubeService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class LolTubeRepositoryTest {

    @Mock
    private lateinit var lolTubeService: LolTubeService
    private lateinit var repository: LolTubeRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = LolTubeRepositoryImpl(lolTubeService)
    }

    @Test
    fun `searchVideos 호출 시 LolTubeService의 searchVideos가 호출되고 올바른 VideoItem 리스트를 반환해야 한다`() =
        runTest {
            // Given
            val query = "리그오브레전드"
            val maxResults = 10
            val regionCode = "KR"
            val expectedVideoItems = listOf(
                VideoItem(
                    id = VideoId("videoId1"),
                    snippet = VideoSnippet(
                        title = "영상 제목 1",
                        channelTitle = "채널명 1",
                        thumbnails = Thumbnails(
                            high = ThumbnailDetail("http://example.com/thumb1.jpg")
                    )
                )
            ),
            VideoItem(
                id = VideoId("videoId2"),
                snippet = VideoSnippet(
                    title = "영상 제목 2",
                    channelTitle = "채널명 2",
                    thumbnails = Thumbnails(
                        high = ThumbnailDetail("http://example.com/thumb2.jpg")
                    )
                )
            )
        )
        val mockResponse = LolTubeSearchResponse(items = expectedVideoItems)

            whenever(
            lolTubeService.searchVideos(
                query = query,
                maxResults = maxResults,
                regionCode = regionCode
            )
        ).thenReturn(mockResponse)

        // When
        val result = repository.searchVideos(query, maxResults, regionCode)

        // Then
        assertEquals(expectedVideoItems, result)
            Mockito.verify(lolTubeService).searchVideos(
                query = query,
                maxResults = maxResults,
                regionCode = regionCode
            )
    }

    @Test(expected = Exception::class)
    fun `searchVideos 호출 시 LolTubeService에서 예외 발생 시 예외를 다시 던져야 한다`(): Unit = runTest {

        val query = "롤"
        val maxResults = 5
        val regionCode = "US"

        whenever(
            lolTubeService.searchVideos(
                query = query,
                maxResults = maxResults,
                regionCode = regionCode
            )
        ).thenThrow(RuntimeException("API 호출 실패"))

        repository.searchVideos(query, maxResults, regionCode)
    }
}
