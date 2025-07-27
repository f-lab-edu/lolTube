package com.flab.loltube.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.data.repository.LolTubeRepository
import com.flab.network.response.youtube.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LolTubeViewModel @Inject constructor(
    private val lolTubeRepository: LolTubeRepository
) : ViewModel() {

    private val _videos = MutableStateFlow<List<VideoItem>>(emptyList())
    val videos = _videos.asStateFlow()

    fun fetchVideos() {
        viewModelScope.launch {
            try {
                val result = lolTubeRepository.searchVideos(
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
