package com.flab.shorts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.domain.usecase.SearchVideosUseCase
import com.flab.shorts.model.ErrorType
import com.flab.shorts.model.ShortsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShortsViewModel @Inject constructor(
    private val searchVideosUseCase: SearchVideosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ShortsUiState>(ShortsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchShortsVideos() {
        viewModelScope.launch {
            _uiState.value = ShortsUiState.Loading
            try {
                val result = searchVideosUseCase.invoke(
                    query = "롤 Shorts",
                    maxResults = 10,
                    regionCode = "KR"
                )
                _uiState.value = ShortsUiState.Success(result)
            } catch (e: Exception) {
                val errorType = when {
                    e.message?.contains("network", ignoreCase = true) == true ->
                        ErrorType.NETWORK
                    e.message?.contains("quota", ignoreCase = true) == true ->
                        ErrorType.QUOTA_EXCEEDED
                    else ->
                        ErrorType.UNKNOWN
                }
                _uiState.value = ShortsUiState.Error(errorType)
                e.printStackTrace()
            }
        }
    }

    fun reloadShortsVideos() {
        fetchShortsVideos()
    }
}
