package com.avvsoft2050.rickandmorty.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avvsoft2050.rickandmorty.R
import com.avvsoft2050.rickandmorty.presentation.activity.EpisodesFragment.Companion.EPISODE_ID
import com.avvsoft2050.rickandmorty.adapters.CharactersAdapter
import com.avvsoft2050.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.avvsoft2050.rickandmorty.pojo.CharacterResult
import com.avvsoft2050.rickandmorty.pojo.EpisodeResult
import com.avvsoft2050.rickandmorty.presentation.viewmodel.EpisodeDetailsViewModel

class EpisodeDetailsFragment : Fragment() {
    companion object {
        fun newInstance() = EpisodeDetailsFragment()
    }

    private lateinit var binding: FragmentEpisodeDetailsBinding
    private lateinit var episodeDetailsViewModel: EpisodeDetailsViewModel
    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        episodeDetailsViewModel = ViewModelProvider(this)[EpisodeDetailsViewModel::class.java]
        adapter = CharactersAdapter(
            onClickAction = {
                showCharacterDetailsFragment(it)
            }
        )
        binding.recyclerViewEpisodeDetailsCharacters.adapter = adapter
        val args = arguments
        args?.let { bundle ->
            episodeDetailsViewModel.getEpisodeDetails(bundle.getInt(EPISODE_ID))
                .observe(viewLifecycleOwner) {
                    if (it != null) {
                        binding.apply {
                            textViewEpisodeDetailsName.text = it.name
                            textViewEpisodeDetailsEpisode.text = it.episode
                            textViewEpisodeDetailsAirDate.text = it.airDate
                        }
                        setupRecyclerView(it)
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.not_enough_data_downloaded),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
        return binding.root
    }

    private fun showCharacterDetailsFragment(characterResult: CharacterResult) {
        val characterDetailsFragment = CharacterDetailsFragment.newInstance()
        val args = Bundle()
        args.putInt(CharactersFragment.CHARACTER_ID, characterResult.id)
        characterDetailsFragment.arguments = args
        parentFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(R.id.container, characterDetailsFragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun setupRecyclerView(episodeResult: EpisodeResult) {
        val episodeListOfCharacterResultsIds = mutableListOf<String>()
        episodeResult.characters?.forEach { e ->
            val characterResultId = e.substringAfterLast('/')
            episodeListOfCharacterResultsIds.add(characterResultId)
        }
        episodeDetailsViewModel.getMultipleCharacters(episodeListOfCharacterResultsIds)
            .observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
    }
}