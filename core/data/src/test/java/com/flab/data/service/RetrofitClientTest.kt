package com.flab.data.service

import com.flab.network.response.youtube.LolTubeSearchResponse
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

class RetrofitClientTest {

    @Mock
    private lateinit var lolTubeService: LolTubeService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `LolTubeService mock이 성공적으로 생성되어야 한다`() {
        // Then
        assertNotNull("LolTubeService mock should be created", lolTubeService)
    }

    @Test
    fun `LolTubeService는 LolTubeSearchResponse를 반환하는 searchVideos 메소드를 가져야 한다`() =
        runTest {
        // Given
        val mockResponse = LolTubeSearchResponse(items = emptyList())
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
        val result = lolTubeService.searchVideos(
            apiKey = "test-key",
            part = "snippet",
            query = "test",
            type = "video",
            maxResults = 10,
            regionCode = "KR"
        )

        // Then
        assertNotNull(result)
        assertEquals(0, result.items.size)
    }

    @Test
    fun `LolTubeService 인터페이스는 data 모듈에서 접근 가능해야 한다`() {
        // Given & When
        val isInterface = LolTubeService::class.java.isInterface
        val className = LolTubeService::class.simpleName

        // Then
        assertEquals("Should be an interface", true, isInterface)
        assertEquals("Should have correct name", "LolTubeService", className)
    }
}
