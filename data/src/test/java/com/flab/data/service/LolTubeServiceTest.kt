package com.flab.data.service

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LolTubeServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var lolTubeService: LolTubeService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        lolTubeService = retrofit.create(LolTubeService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `searchVideos should make correct API request and parse response`() = runTest {
        // Given
        val mockResponseJson = """
            {
                "items": [
                    {
                        "id": {
                            "videoId": "testVideoId"
                        },
                        "snippet": {
                            "title": "Test Video Title",
                            "channelTitle": "Test Channel",
                            "thumbnails": {
                                "high": {
                                    "url": "https://test.com/thumbnail.jpg"
                                }
                            }
                        }
                    }
                ]
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockResponseJson)
        )

        // When
        val result = lolTubeService.searchVideos(
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

        // Verify request
        val request = mockWebServer.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/search", request.path?.substringBefore('?'))

        val requestUrl = request.requestUrl
        assertEquals("snippet", requestUrl?.queryParameter("part"))
        assertEquals("test query", requestUrl?.queryParameter("q"))
        assertEquals("video", requestUrl?.queryParameter("type"))
        assertEquals("10", requestUrl?.queryParameter("maxResults"))
        assertEquals("KR", requestUrl?.queryParameter("regionCode"))
    }

    @Test
    fun `searchVideos should handle empty response`() = runTest {
        // Given
        val mockResponseJson = """
            {
                "items": []
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockResponseJson)
        )

        // When
        val result = lolTubeService.searchVideos(query = "empty query")

        // Then
        assertNotNull(result)
        assertEquals(0, result.items.size)
    }

    @Test
    fun `searchVideos should use default parameters when not provided`() = runTest {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{"items": []}""")
        )

        // When
        lolTubeService.searchVideos(query = "test")

        // Then
        val request = mockWebServer.takeRequest()
        val requestUrl = request.requestUrl

        assertEquals("snippet", requestUrl?.queryParameter("part"))
        assertEquals("video", requestUrl?.queryParameter("type"))
        assertEquals("10", requestUrl?.queryParameter("maxResults"))
        assertEquals("KR", requestUrl?.queryParameter("regionCode"))
    }
}
