package com.furkan.beinConnectMovies.ui.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkan.beinConnectMovies.utils.Resource
import com.furkan.beinConnectMovies.data.remote.model.MoviesModel
import com.furkan.beinConnectMovies.data.remote.model.MoviesResult
import com.furkan.beinConnectMovies.data.remote.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel  @Inject constructor(private val movieRepository: MoviesRepository): ViewModel() {

    private val _getMovies = MutableLiveData<MoviesModel>()
    val getMovies: LiveData<MoviesModel>
        get() = _getMovies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _filterText = MutableLiveData<List<MoviesResult>>()
    val filterText: LiveData<List<MoviesResult>>
        get() = _filterText

    fun getMovieList(id: Int,page: Int) {

        viewModelScope.launch {
            when (val response = movieRepository.getData(id,page)) {
                is Resource.Success -> {
                    _getMovies.postValue(response.data)

                }
                is Resource.Error -> {
                    _error.postValue(response.message)
                }
            }
        }
    }

    fun getFilterText(keyword: String, searchItem: List<MoviesResult>?) {

        viewModelScope.launch {

            val filter = searchItem?.filter { it.originalTitle?.contains(keyword, true) == true }
            _filterText.value = filter

        }

    }

}