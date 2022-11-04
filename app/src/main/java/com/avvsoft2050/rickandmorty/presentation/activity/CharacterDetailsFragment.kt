package com.avvsoft2050.rickandmorty.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avvsoft2050.rickandmorty.R
import com.avvsoft2050.rickandmorty.presentation.activity.CharactersFragment.Companion.CHARACTER_ID
import com.avvsoft2050.rickandmorty.presentation.activity.LocationsFragment.Companion.LOCATION_ID
import com.avvsoft2050.rickandmorty.adapters.EpisodesAdapter
import com.avvsoft2050.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.avvsoft2050.rickandmorty.pojo.CharacterResult
import com.avvsoft2050.rickandmorty.pojo.EpisodeResult
import com.avvsoft2050.rickandmorty.presentation.viewmodel.CharacterDetailsViewModel
import com.squareup.picasso.Picasso

class CharacterDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CharacterDetailsFragment()
    }

    private lateinit var binding: FragmentCharacterDetailsBinding
    private lateinit var characterDetailsViewModel: CharacterDetailsViewModel
    private lateinit var adapter: EpisodesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        characterDetailsViewModel = ViewModelProvider(this)[CharacterDetailsViewModel::class.java]
        adapter = EpisodesAdapter(
            onClickAction = {
                showEpisodeDetailsFragment(it)
            }
        )
        binding.recyclerViewCharacterDetailsEpisodes.adapter = adapter
        val args = arguments
        args?.let { bundle ->
            characterDetailsViewModel.getCharacterDetails(bundle.getInt(CHARACTER_ID))
                .observe(viewLifecycleOwner) {
                    if (it != null) {
                        setupCharacterDetailsView(it)
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

    private fun setupCharacterDetailsView(characterResult: CharacterResult) {
        Picasso.get()
            .load(characterResult.image)
            .into(binding.imageViewCharacterDetails)
        binding.apply {
            textViewCharacterDetailsName.text = characterResult.name
            textViewCharacterDetailsStatus.text = characterResult.status
            textViewCharacterDetailsSpecies.text = characterResult.species
            textViewCharacterDetailsType.text = characterResult.type
            textViewCharacterDetailsGender.text = characterResult.gender
            textViewCharacterDetailsOrigin.text = characterResult.characterOrigin.name
            textViewCharacterDetailsLocation.text = characterResult.characterLocation.name
            val characterOriginUrl = characterResult.characterOrigin.url
            val characterLocationUrl = characterResult.characterLocation.url
            textViewCharacterDetailsOrigin.setOnClickListener {
                setupListener(characterOriginUrl)
            }
            textViewCharacterDetailsLocation.setOnClickListener {
                setupListener(characterLocationUrl)
            }
        }
    }

    private fun setupRecyclerView(characterResult: CharacterResult) {
        val characterListOfEpisodeResultsIds = mutableListOf<String>()
        characterResult.episode?.forEach { e ->
            val episodeResultId = e.substringAfterLast('/')
            characterListOfEpisodeResultsIds.add(episodeResultId)
        }
        characterDetailsViewModel.getMultipleEpisode(characterListOfEpisodeResultsIds)
            .observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
    }

    private fun setupListener(characterOriginUrl: String) {
        val locationResultId = characterOriginUrl
            .substringAfterLast('/', "0")
            .toInt()
        if (locationResultId == 0) {
            Toast.makeText(context, "Can't find location", Toast.LENGTH_LONG)
                .show()
        } else {
            showLocationDetailsFragment(locationResultId)
        }
    }

    private fun showLocationDetailsFragment(locationResultId: Int) {
        val locationDetailsFragment = LocationDetailsFragment.newInstance()
        val args = Bundle()
        args.putInt(LOCATION_ID, locationResultId)
        locationDetailsFragment.arguments = args
        parentFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(R.id.container, locationDetailsFragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun showEpisodeDetailsFragment(episodeResult: EpisodeResult) {
        val episodeDetailsFragment = EpisodeDetailsFragment.newInstance()
        val args = Bundle()
        args.putInt(EpisodesFragment.EPISODE_ID, episodeResult.id)
        episodeDetailsFragment.arguments = args
        parentFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(R.id.container, episodeDetailsFragment)
            addToBackStack(null)
            commit()
        }
    }
}