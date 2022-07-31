package com.furkan.beinConnectMovies.data.remote.repository

import com.furkan.beinConnectMovies.base.BaseRepository
import com.furkan.beinConnectMovies.data.remote.dto.ApiServices
import com.furkan.beinConnectMovies.utils.API_KEY
import com.furkan.beinConnectMovies.utils.LANGUAGE
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val apiService : ApiServices,
) : BaseRepository() {
    suspend fun getData(
    ) = safeApiRequest {
        apiService.getMovieGenres(API_KEY, LANGUAGE)
    }
}