package com.furkan.beinConnectMovies.utils.model

import com.furkan.beinConnectMovies.R
import com.furkan.beinConnectMovies.utils.extensions.string

enum class ErrorModel(val code: String) {
    FAIL(string(R.string.error)),
}

enum class ErrorType(val code: String) {
    NETWORK(string(R.string.networkError)),
    API(string(R.string.serverError)),
}