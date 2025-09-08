package com.flab.shorts

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
class ShortsViewModel @Inject constructor(
    private val searchVideosUseCase: SearchVideosUseCase
) : ViewModel() {

    private val _shortsVideoIds = MutableStateFlow<List<Video>>(emptyList())
    val shortsVideoIds = _shortsVideoIds.asStateFlow()

    fun fetchShortsVideos() {
        viewModelScope.launch {
            try {
                val result = searchVideosUseCase.invoke(
                    query = "롤 Shorts",
                    maxResults = 10,
                    regionCode = "KR"
                )
                _shortsVideoIds.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
