package com.furkan.beinConnectMovies.data.remote.model

import com.google.gson.annotations.SerializedName

data class GenreModel(
    @SerializedName("genres") val data: ArrayList<GenreObject>? = null,
    val status_code: Int? = null,
    val status_message: String? = null,
    val success: Boolean? = null
)

data class GenreObject(
    val id: String? = null,
    val name: String? = null
)
