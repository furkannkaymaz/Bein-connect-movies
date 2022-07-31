package com.furkan.beinConnectMovies.ui.movies.genre.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkan.beinConnectMovies.utils.Resource
import com.furkan.beinConnectMovies.data.remote.model.GenreModel
import com.furkan.beinConnectMovies.data.remote.repository.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(private val genreRepository: GenreRepository) : ViewModel() {

    private val _getGenre = MutableLiveData<GenreModel>()
    val getGenre: LiveData<GenreModel>
        get() = _getGenre

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getGenreList() {
        viewModelScope.launch {
            when (val response = genreRepository.getData()) {
                is Resource.Success -> {
                    _getGenre.postValue(response.data)

                }
                is Resource.Error -> {
                    _error.postValue(response.message)
                }
            }
        }
    }
}