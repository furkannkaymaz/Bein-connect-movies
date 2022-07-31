package com.furkan.beinConnectMovies.ui.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.furkan.beinConnectMovies.base.BaseAdapter
import com.furkan.beinConnectMovies.data.remote.model.MoviesResult
import com.furkan.beinConnectMovies.databinding.AdapterMovieItemBinding
import com.furkan.beinConnectMovies.utils.IMAGE_BASE_URL
import com.furkan.beinConnectMovies.utils.extensions.loadImage

class MoviesAdapter(private val itemClick: ((MoviesResult) -> Unit)) : BaseAdapter<MoviesResult, MoviesAdapter.ViewHolder>() {

    override fun bindView(holder: ViewHolder, position: Int, item: MoviesResult) {

        holder.binding.movieIcon.loadImage(IMAGE_BASE_URL + item.posterPath)

        holder.binding.movieName.text = item.title
        holder.binding.movieIcon.setOnClickListener {
            itemClick?.let { it1 -> it1(item) }
        }

    }

    override fun createView(
        context: Context,
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(AdapterMovieItemBinding.inflate(inflater, parent, false))
    }

    class ViewHolder(val binding: AdapterMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}

