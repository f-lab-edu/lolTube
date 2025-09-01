package com.flab.home

import com.flab.domain.model.Video
import com.flab.domain.usecase.SearchVideosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LolTubeViewModelTest {

    @Mock
    private lateinit var searchVideosUseCase: SearchVideosUseCase

    private lateinit var viewModel: LolTubeViewModel
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = LolTubeViewModel(searchVideosUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `초기 상태에서 videos는 빈 리스트여야 한다`() {
        // Given & When
        val initialState = viewModel.videos.value

        // Then
        assertTrue("초기 상태는 빈 리스트여야 함", initialState.isEmpty())
    }

    @Test
    fun `fetchVideos 성공 시 videos 상태가 업데이트된다`() = runTest {
        // Given
        val expectedVideos = listOf(
            Video(
                videoId = "test1",
                title = "롤 하이라이트 1",
                channelTitle = "게임채널",
                thumbnailUrl = "https://example.com/thumb1.jpg"
            ),
            Video(
                videoId = "test2",
                title = "롤 하이라이트 2",
                channelTitle = "프로채널",
                thumbnailUrl = "https://example.com/thumb2.jpg"
            )
        )

        whenever(
            searchVideosUseCase.invoke(
                query = "롤",
                maxResults = 10,
                regionCode = "KR"
            )
        ).thenReturn(expectedVideos)

        // When
        viewModel.fetchVideos()

        // Then
        assertEquals("반환된 비디오 리스트가 상태에 반영되어야 함", expectedVideos, viewModel.videos.value)
        verify(searchVideosUseCase).invoke(
            query = "롤",
            maxResults = 10,
            regionCode = "KR"
        )
    }

    @Test
    fun `fetchVideos 호출 시 빈 결과를 받으면 빈 리스트 상태가 된다`() = runTest {
        // Given
        val emptyVideos = emptyList<Video>()

        whenever(
            searchVideosUseCase.invoke(
                query = "롤",
                maxResults = 10,
                regionCode = "KR"
            )
        ).thenReturn(emptyVideos)

        // When
        viewModel.fetchVideos()

        // Then
        assertTrue("빈 결과는 빈 리스트 상태여야 함", viewModel.videos.value.isEmpty())
    }

    @Test
    fun `videos가 이미 있는 상태에서 fetchVideos 호출하면 UseCase를 호출하지 않는다`() = runTest {
        // Given
        val initialVideos = listOf(
            Video("existing", "기존 비디오", "채널", null)
        )

        whenever(
            searchVideosUseCase.invoke(
                query = "롤",
                maxResults = 10,
                regionCode = "KR"
            )
        ).thenReturn(initialVideos)

        // When - 첫 번째 호출
        viewModel.fetchVideos()

        // 상태 확인
        assertEquals("첫 번째 호출로 비디오 로드됨", initialVideos, viewModel.videos.value)

        // When - 두 번째 호출
        viewModel.fetchVideos()

        // Then - UseCase는 한 번만 호출되어야 함
        verify(searchVideosUseCase).invoke(
            query = "롤",
            maxResults = 10,
            regionCode = "KR"
        )
        assertEquals("상태는 변경되지 않음", initialVideos, viewModel.videos.value)
    }

    @Test
    fun `fetchVideos 실행 중 예외 발생 시 videos 상태는 빈 리스트로 유지된다`() = runTest {
        // Given
        whenever(
            searchVideosUseCase.invoke(
                query = "롤",
                maxResults = 10,
                regionCode = "KR"
            )
        ).thenThrow(RuntimeException("네트워크 오류"))

        // When
        viewModel.fetchVideos()

        // Then
        assertTrue("예외 발생 시 빈 리스트 유지", viewModel.videos.value.isEmpty())
        verify(searchVideosUseCase).invoke(
            query = "롤",
            maxResults = 10,
            regionCode = "KR"
        )
    }

    @Test
    fun `ViewModel은 SearchVideosUseCase를 올바른 파라미터로 호출한다`() = runTest {
        // Given
        val expectedVideos = listOf(
            Video("param_test", "파라미터 테스트", "테스트채널", null)
        )

        whenever(
            searchVideosUseCase.invoke(
                query = "롤",
                maxResults = 10,
                regionCode = "KR"
            )
        ).thenReturn(expectedVideos)

        // When
        viewModel.fetchVideos()

        // Then
        verify(searchVideosUseCase).invoke(
            query = "롤",
            maxResults = 10,
            regionCode = "KR"
        )
    }
}
