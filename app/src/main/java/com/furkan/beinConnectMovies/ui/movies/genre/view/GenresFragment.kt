package com.furkan.beinConnectMovies.ui.movies.genre.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.furkan.beinConnectMovies.base.BaseFragment
import com.furkan.beinConnectMovies.data.remote.model.GenreObject
import com.furkan.beinConnectMovies.databinding.FragmentGenresBinding
import com.furkan.beinConnectMovies.ui.movies.genre.adapter.GenresPagerAdapter
import com.furkan.beinConnectMovies.ui.movies.genre.viewmodels.GenresViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class GenresFragment : BaseFragment<FragmentGenresBinding, GenresViewModel>() {

    override val viewModel by viewModels<GenresViewModel>()
    lateinit var adapter: GenresPagerAdapter
    var list: List<GenreObject>? = null
    private var genreId: String? = null

    override fun onCreateFinished() {

        adapter = GenresPagerAdapter(childFragmentManager)
        genreId = "28"
       getData()
    }

    private fun getData() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getGenreList()
        }

        viewModel.getGenre.observe(viewLifecycleOwner, {

            list = it.data

            if (!list.isNullOrEmpty()) {
                adapter.setItems(list!!)
                setupViewPager()
            }

        })
    }

    private fun setupViewPager() {
        binding!!.apply {
            fragmenGenresViewpager.adapter = adapter

            val genres = adapter.genres
            val position = 0

            tvHeader.text = genres[position].name
            fragmentGenresTablayout.setupWithViewPager(fragmenGenresViewpager)
            fragmenGenresViewpager.currentItem = position
            fragmenGenresViewpager.addOnPageChangeListener(
                TabLayout.TabLayoutOnPageChangeListener(
                    fragmentGenresTablayout
                )
            )
        }

        binding?.fragmenGenresViewpager?.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding?.tvHeader?.text = list?.get(position)?.name
            }
        })
    }

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGenresBinding {
        return FragmentGenresBinding.inflate(inflater,container,false)
    }


}