package com.furkan.beinConnectMovies.ui.movies.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkan.beinConnectMovies.R
import com.furkan.beinConnectMovies.base.BaseFragment
import com.furkan.beinConnectMovies.data.remote.model.GenreObject
import com.furkan.beinConnectMovies.data.remote.model.MoviesResult
import com.furkan.beinConnectMovies.databinding.FragmentMoviesBinding
import com.furkan.beinConnectMovies.ui.detail.view.DetailFragmentArgs
import com.furkan.beinConnectMovies.ui.movies.adapter.MoviesAdapter
import com.furkan.beinConnectMovies.ui.movies.viewmodels.MoviesViewModel
import com.furkan.beinConnectMovies.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MoviesFragment(private val genreObject: GenreObject?) :
    BaseFragment<FragmentMoviesBinding, MoviesViewModel>() {

    override val viewModel by viewModels<MoviesViewModel>()
    private lateinit var adapter: MoviesAdapter
    private var list: List<MoviesResult>? = null
    private var start = 1
    private var isLoading = false
    private var searchItem: ArrayList<MoviesResult>? = null

    override fun onCreateFinished() {
        sendAdapterData()

        binding?.moviesRv?.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        binding?.moviesRv?.adapter = adapter

        getData()
        pagingRecyclerView()
    }

    override fun observerData() {
        super.observerData()
        viewModel.getMovies.observe(viewLifecycleOwner, {
            if (searchItem.isNullOrEmpty()) {
                searchItem = it.results
            } else {
                it.results?.let { it1 -> searchItem?.addAll(it1) }
            }

            setRecycleViewData(searchItem)
            isLoading = false

            list = it.results

        })

        viewModel.error.observe(viewLifecycleOwner, {
            context?.toast(it.toString())
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            // page status
        })

    }

    private fun getData() {

        CoroutineScope(Dispatchers.Main).launch {
            genreObject?.id?.toInt()?.let {
                viewModel.getMovieList(it, start)
            }

        }
    }

    private fun getSearchItem() {

        binding?.svSearch?.getEditText()?.addTextChangedListener { text ->

            CoroutineScope(Dispatchers.IO).launch {
                context?.let { viewModel.getFilterText(text.toString(), searchItem) }
            }

            viewModel.filterText.observe(viewLifecycleOwner, {
                setRecycleViewData(it)
            })
        }

    }

    private suspend fun showProgress(show: Boolean) {
        delay(100)
        if (show) {
            binding?.progress?.visibility = View.VISIBLE
        } else {
            binding?.progress?.visibility = View.GONE
        }
    }

    private fun setRecycleViewData(list: List<MoviesResult>?) {

        adapter.set(list)
        adapter.notifyDataSetChanged()

        GlobalScope.launch(Dispatchers.Main) {
            showProgress(false)
        }
    }

    private fun pagingRecyclerView() {
        binding?.moviesRv?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        isLoading = false
                        start += 1
                        binding?.progress?.visibility = View.VISIBLE
                        viewModel.getMovieList(28, start)

                    }
                }
            }
        })
    }

    private fun sendAdapterData() {
        adapter = MoviesAdapter {
            it.title?.let { it1 -> goDetailPage(it1) }
        }
    }

    private fun goDetailPage(title: String) {
        val navController = Navigation.findNavController(requireActivity(), R.id.main)
        navController.navigate(
            R.id.action_moviesFragment_to_detailFragment,
            DetailFragmentArgs(title.toString()).toBundle()
        )
    }

    override fun onResume() {
        super.onResume()
        getSearchItem()

    }

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMoviesBinding {
        return FragmentMoviesBinding.inflate(inflater, container, false)
    }


}