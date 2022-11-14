package com.avvsoft2050.rickandmorty.presentation.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.avvsoft2050.rickandmorty.R
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.APP_SETTINGS
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.APP_SETTINGS_EPISODE_PAGES
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterEpisodeCode
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterEpisodeName
import com.avvsoft2050.rickandmorty.adapters.EpisodesAdapter
import com.avvsoft2050.rickandmorty.databinding.FragmentEpisodesBinding
import com.avvsoft2050.rickandmorty.pojo.EpisodeResult
import com.avvsoft2050.rickandmorty.presentation.viewmodel.EpisodeViewModel

class EpisodesFragment : Fragment() {

    companion object {
        fun newInstance() = EpisodesFragment()
        const val EPISODE_ID = "episodeId"
    }

    private lateinit var binding: FragmentEpisodesBinding
    private lateinit var episodeViewModel: EpisodeViewModel
    private lateinit var preferences: SharedPreferences
    private var episodePage = 1
    private var episodeLastPage = 3
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: EpisodesAdapter
    private var dataLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        episodeViewModel = ViewModelProvider(this)[EpisodeViewModel::class.java]
        preferences =
            this.requireActivity().getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
        if (preferences.contains(APP_SETTINGS_EPISODE_PAGES)) {
            episodePage = preferences.getInt(APP_SETTINGS_EPISODE_PAGES, 1)
        }
        progressBar = binding.progressBarEpisodes
        swipeRefresh = binding.swipeRefreshEpisodes
        adapter = EpisodesAdapter(
            onClickAction = {
                showEpisodeDetailsFragment(it)
            }
        )
        binding.recyclerViewEpisodes.adapter = adapter
        setupActions()
        setupObservers()
        return binding.root
    }

    private fun setupActions() {
        if (!dataLoaded) {
            episodeViewModel.loadEpisodeData(
                episodePage = episodePage.toString(),
                episodeName = filterEpisodeName,
                episodeCode = filterEpisodeCode
            )
            dataLoaded = true
        }
        binding.scrollViewEpisodes.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    scrollReachedBottom()
                }
            })
        swipeRefresh.setOnRefreshListener {
            episodeViewModel.loadEpisodeData(
                episodePage.toString(),
                episodeName = filterEpisodeName,
                episodeCode = filterEpisodeCode
            )
            swipeRefresh.isRefreshing = false
        }
    }

    private fun showEpisodeDetailsFragment(episodeResult: EpisodeResult) {
        val episodeDetailsFragment = EpisodeDetailsFragment.newInstance()
        val args = Bundle()
        args.putInt(EPISODE_ID, episodeResult.id)
        episodeDetailsFragment.arguments = args
        parentFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(R.id.container, episodeDetailsFragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun scrollReachedBottom() {
        if (episodePage < episodeLastPage) {
            episodePage++
            preferences
                .edit()
                .putInt(APP_SETTINGS_EPISODE_PAGES, episodePage)
                .apply()
            progressBar.visibility = View.VISIBLE
            episodeViewModel.loadEpisodeData(
                episodePage.toString(),
                episodeName = filterEpisodeName,
                episodeCode = filterEpisodeCode
            )
        } else {
            progressBar.visibility = View.GONE
            Toast.makeText(
                context,
                getString(R.string.no_more_episodes_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setupObservers() {
        episodeViewModel.episode.observe(viewLifecycleOwner) {
            it?.let {
                episodeLastPage = it.episodeInfo?.pages!!
            }
        }
        episodeViewModel.episodeResults.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }
}