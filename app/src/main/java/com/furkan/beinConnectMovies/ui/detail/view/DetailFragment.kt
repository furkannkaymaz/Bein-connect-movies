package com.furkan.beinConnectMovies.ui.detail.view

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.furkan.beinConnectMovies.base.BaseFragment
import com.furkan.beinConnectMovies.databinding.FragmentDetailBinding
import com.furkan.beinConnectMovies.utils.VIDEO_URL
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.MimeTypes

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailFragmentViewModel>(),
    Player.Listener {

    override val viewModel by viewModels<DetailFragmentViewModel>()
    private lateinit var simpleExoPlayer: ExoPlayer
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun hideTopMenu() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(
            requireActivity().window,
            binding!!.container
        ).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

    }

    override fun onCreateFinished() {

        binding?.titleTrailers?.text = args.detailData

        hideTopMenu()

        simpleExoPlayer = SimpleExoPlayer.Builder(requireContext()).build()
        binding?.player?.player = simpleExoPlayer
        simpleExoPlayer


        val media: MediaItem = MediaItem.Builder()
            .setUri(Uri.parse(VIDEO_URL))
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()


        simpleExoPlayer.addMediaItem(media)
        simpleExoPlayer.prepare()
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.addListener(this)


        binding?.player?.setControllerVisibilityListener { visibility ->
            if (visibility == View.VISIBLE) {
                binding?.titleTrailers?.visibility = View.VISIBLE
            } else {
                binding?.titleTrailers?.visibility = View.GONE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        hideTopMenu()
    }

    override fun onDestroy() {
        simpleExoPlayer.stop()
        simpleExoPlayer.clearMediaItems()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        hideTopMenu()
        simpleExoPlayer.release()

    }

    override fun onResume() {
        super.onResume()
        binding?.player?.useController = true
        hideTopMenu()
    }

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }


}