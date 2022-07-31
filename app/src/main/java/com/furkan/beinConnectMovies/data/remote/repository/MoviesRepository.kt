package com.furkan.beinConnectMovies.data.remote.repository

import com.furkan.beinConnectMovies.base.BaseRepository
import com.furkan.beinConnectMovies.data.remote.dto.ApiServices
import com.furkan.beinConnectMovies.utils.API_KEY
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val apiService: ApiServices,
) : BaseRepository() {
    suspend fun getData(id: Int,page :Int
    ) = safeApiRequest {
        apiService.getMovies(
            API_KEY,
            "popularity.desc",
            false,
            include_video = false,
            page = page,
            withGenres = id,
        )
    }
}
