package com.avvsoft2050.rickandmorty.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avvsoft2050.rickandmorty.R
import com.avvsoft2050.rickandmorty.presentation.activity.LocationsFragment.Companion.LOCATION_ID
import com.avvsoft2050.rickandmorty.adapters.CharactersAdapter
import com.avvsoft2050.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.avvsoft2050.rickandmorty.pojo.CharacterResult
import com.avvsoft2050.rickandmorty.pojo.LocationResult
import com.avvsoft2050.rickandmorty.presentation.viewmodel.LocationDetailsViewModel

class LocationDetailsFragment : Fragment() {
    companion object {
        fun newInstance() = LocationDetailsFragment()
    }

    private lateinit var binding: FragmentLocationDetailsBinding
    private lateinit var locationDetailsViewModel: LocationDetailsViewModel
    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        locationDetailsViewModel = ViewModelProvider(this)[LocationDetailsViewModel::class.java]
        adapter = CharactersAdapter(
            onClickAction = {
                showCharacterDetailsFragment(it)
            }
        )
        binding.recyclerViewLocationDetailsResidents.adapter = adapter
        val args = arguments
        args?.let { bundle ->
            locationDetailsViewModel.getLocationDetails(bundle.getInt(LOCATION_ID))
                .observe(viewLifecycleOwner) {
                    if (it != null) {
                        binding.apply {
                            textViewLocationDetailsName.text = it.name
                            textViewLocationDetailsType.text = it.type
                            textViewLocationDetailsDimension.text = it.dimension
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

    private fun setupRecyclerView(locationResult: LocationResult) {
        val locationListOfCharacterResultsIds = mutableListOf<String>()
        locationResult.residents?.forEach { e ->
            val characterResultId = e.substringAfterLast('/')
            locationListOfCharacterResultsIds.add(characterResultId)
        }
        locationDetailsViewModel.getMultipleCharacters(locationListOfCharacterResultsIds)
            .observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
    }
}