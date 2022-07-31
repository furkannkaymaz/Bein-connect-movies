package com.furkan.beinConnectMovies.ui.movies.genre.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.furkan.beinConnectMovies.data.remote.model.GenreObject
import com.furkan.beinConnectMovies.ui.movies.view.MoviesFragment

class GenresPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val genres = ArrayList<GenreObject>()

    override fun getCount(): Int {
        return genres.size
    }

    override fun getItem(position: Int): Fragment {
        return MoviesFragment(genres[position])
    }

    override fun getPageTitle(position: Int): String? {
        return genres[position].name
    }

    fun setItems(list: List<GenreObject>) {
        genres.clear()
        genres.addAll(list)
        notifyDataSetChanged()
    }

}