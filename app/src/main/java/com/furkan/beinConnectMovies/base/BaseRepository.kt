package com.furkan.beinConnectMovies.base

import com.furkan.beinConnectMovies.utils.Resource
import com.furkan.beinConnectMovies.utils.model.ErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiRequest(
        apiRequest : suspend () -> T) : Resource<T> {
        return withContext(Dispatchers.IO){
            try {
                Resource.Success(apiRequest.invoke())
            }catch (throwable : Throwable){
                when(throwable){
                    is HttpException ->{
                        Resource.Error("Server Error",null,false)
                    }
                    else -> {
                        Resource.Error("Network Error",null,true)
                    }
                }
            }
        }
    }
}