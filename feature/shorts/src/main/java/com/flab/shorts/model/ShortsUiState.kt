package com.flab.shorts.model

import com.flab.domain.model.Video

enum class ErrorType {
    NETWORK,
    QUOTA_EXCEEDED,
    UNKNOWN
}

sealed class ShortsUiState {
    data object Loading : ShortsUiState()
    data class Success(val videos: List<Video>) : ShortsUiState()
    data class Error(val errorType: ErrorType) : ShortsUiState()
}
