package com.flab.loltube.ui.main

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

    fun fetchVideos() {
        viewModelScope.launch {
            try {
                val result = searchVideosUseCase.invoke(
                    query = "롤",
                    maxResults = 10,
                    regionCode = "KR"
                )
                _videos.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
