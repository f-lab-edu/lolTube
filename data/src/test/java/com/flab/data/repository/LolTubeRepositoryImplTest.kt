package com.flab.data.repository

import com.flab.data.response.youtube.LolTubeSearchResponse
import com.flab.data.response.youtube.ThumbnailDetail
import com.flab.data.response.youtube.Thumbnails
import com.flab.data.response.youtube.VideoId
import com.flab.data.response.youtube.VideoItem
import com.flab.data.response.youtube.VideoSnippet
import com.flab.data.service.LolTubeService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class LolTubeRepositoryImplTest {

    @Mock
    private lateinit var mockLolTubeService: LolTubeService

    private lateinit var lolTubeRepository: LolTubeRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        lolTubeRepository = LolTubeRepositoryImpl(mockLolTubeService)
    }

    @Test
    fun `searchVideos 호출 시 LolTubeService의 searchVideos가 호출되고 올바른 VideoItem 리스트를 반환해야 한다`() =
        runBlocking {
            val query = "리그오브레전드"
            val maxResults = 10
            val regionCode = "KR"

            val expectedVideoItems = listOf(
                VideoItem(
                    id = VideoId(videoId = "videoId1"),
                    snippet = VideoSnippet(
                        title = "영상 제목 1",
                        channelTitle = "채널명 1",
                        thumbnails = Thumbnails(
                            high = ThumbnailDetail(url = "http://example.com/thumb1.jpg")
                        )
                    )
                ),
                VideoItem(
                    id = VideoId(videoId = "videoId2"),
                    snippet = VideoSnippet(
                        title = "영상 제목 2",
                        channelTitle = "채널명 2",
                        thumbnails = Thumbnails(
                            high = ThumbnailDetail(url = "http://example.com/thumb2.jpg")
                        )
                    )
                )
            )
            val mockLolTubeSearchResponse = LolTubeSearchResponse(items = expectedVideoItems)

            whenever(
                mockLolTubeService.searchVideos(
                    query = query,
                    maxResults = maxResults,
                    regionCode = regionCode
                )
            ).thenReturn(mockLolTubeSearchResponse)


            val result = lolTubeRepository.searchVideos(query, maxResults, regionCode)

            verify(mockLolTubeService).searchVideos(
                query = query,
                maxResults = maxResults,
                regionCode = regionCode
            )
            assertEquals(expectedVideoItems, result)
        }

    @Test(expected = Exception::class)
    fun `searchVideos 호출 시 LolTubeService에서 예외 발생 시 예외를 다시 던져야 한다`(): Unit = runBlocking {

        val query = "롤"
        val maxResults = 5
        val regionCode = "US"

        whenever(
            mockLolTubeService.searchVideos(
                query = query,
                maxResults = maxResults,
                regionCode = regionCode
            )
        )
            .thenThrow(RuntimeException("API 호출 실패"))

        lolTubeRepository.searchVideos(query, maxResults, regionCode)
    }
}
