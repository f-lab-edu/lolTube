package com.flab.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.domain.model.Video
import com.flab.domain.usecase.SearchVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LolTubeViewModel @Inject constructor(
    private val searchVideosUseCase: SearchVideosUseCase
) : ViewModel() {

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos = _videos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _hasMorePages = MutableStateFlow(true)
    val hasMorePages = _hasMorePages.asStateFlow()

    private var nextPageToken: String? = null

    fun fetchVideos() {
        if (_videos.value.isNotEmpty()) {
            return
        }
        loadVideos(isFirstLoad = true)
    }

    fun loadMoreVideos() {
        if (_isLoading.value || !_hasMorePages.value) {
            return
        }

        if (nextPageToken == null && _videos.value.isNotEmpty()) {
            return
        }

        loadVideos(isFirstLoad = false)
    }

    private fun loadVideos(isFirstLoad: Boolean) {
        if (_isLoading.value) return
        if (!isFirstLoad && !_hasMorePages.value) return

        viewModelScope.launch {
            try {
                _isLoading.value = true

                val pageToken = if (isFirstLoad) null else nextPageToken

                val searchResult = searchVideosUseCase.searchWithPaging(
                    query = "롤",
                    maxResults = 10,
                    regionCode = "KR",
                    pageToken = pageToken
                )

                val validNewVideos = searchResult.videos.filter { it.videoId.isNotBlank() }

                val updatedVideos = if (isFirstLoad) {
                    validNewVideos
                } else {
                    val currentVideoIds = _videos.value.map { it.videoId }.toSet()
                    val filteredNewVideos = validNewVideos.filter { it.videoId !in currentVideoIds }
                    _videos.value + filteredNewVideos
                }

                _videos.value = updatedVideos

                nextPageToken = searchResult.nextPageToken
                _hasMorePages.value = searchResult.hasMorePages

            } catch (e: Exception) {
                e.printStackTrace()
                _hasMorePages.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
