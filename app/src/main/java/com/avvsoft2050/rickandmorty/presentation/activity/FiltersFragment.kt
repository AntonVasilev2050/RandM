package com.avvsoft2050.rickandmorty.presentation.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.APP_SETTINGS
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.APP_SETTINGS_CHARACTER_PAGES
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.APP_SETTINGS_EPISODE_PAGES
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.APP_SETTINGS_LOCATION_PAGES
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.FILTER_CHARACTER_GENDER
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.FILTER_CHARACTER_NAME
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.FILTER_CHARACTER_SPECIES
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.FILTER_CHARACTER_STATUS
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.FILTER_CHARACTER_TYPE
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.FILTER_EPISODE_CODE
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.FILTER_EPISODE_NAME
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.FILTER_LOCATION_DIMENSION
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.FILTER_LOCATION_NAME
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.FILTER_LOCATION_TYPE
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterCharacterGender
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterCharacterName
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterCharacterSpecies
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterCharacterStatus
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterCharacterType
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterEpisodeCode
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterEpisodeName
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterLocationDimension
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterLocationName
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterLocationType
import com.avvsoft2050.rickandmorty.databinding.FragmentFiltersBinding
import com.avvsoft2050.rickandmorty.presentation.viewmodel.FiltersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FiltersFragment : Fragment() {

    companion object {
        fun newInstance() = FiltersFragment()
    }

    private lateinit var binding: FragmentFiltersBinding
    private lateinit var filtersViewModel: FiltersViewModel
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFiltersBinding.inflate(inflater, container, false)
        filtersViewModel = ViewModelProvider(this)[FiltersViewModel::class.java]
        preferences = requireActivity().getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
        binding.buttonFilter.setOnClickListener {
            saveFiltersIntoPreferences()
            filtersViewModel.viewModelScope.launch(Dispatchers.IO) {
                filtersViewModel.deleteCharacterResult()
                filtersViewModel.deleteLocationResult()
                filtersViewModel.deleteEpisodeResult()
            }
            filtersViewModel.loadCharacterData(
                characterPage = "1",
                characterName = filterCharacterName,
                characterStatus = filterCharacterStatus,
                characterSpecies = filterCharacterSpecies,
                characterType = filterCharacterType,
                characterGender = filterCharacterGender
            )
            filtersViewModel.loadLocationData(
                locationPage = "1",
                locationName = filterLocationName,
                locationType = filterLocationType,
                locationDimension = filterLocationDimension
            )
            filtersViewModel.loadEpisodeData(
                episodePage = "1",
                episodeName = filterEpisodeName,
                episodeCode = filterEpisodeCode
            )
            Thread.sleep(300)
            parentFragmentManager.popBackStack()
        }
        binding.buttonCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return binding.root
    }

    private fun saveFiltersIntoPreferences() {
        filterCharacterName = binding.editTextFilterCharacterName.text.toString()
        val characterSelectedStatus = binding.spinnerFilterCharacterStatus.selectedItem.toString()
        if (characterSelectedStatus == "All statuses"){
            filterCharacterStatus = ""
        }else{
            filterCharacterStatus = characterSelectedStatus
        }
        filterCharacterSpecies = binding.editTextFilterCharacterSpecies.text.toString()
        filterCharacterType = binding.editTextFilterCharacterType.text.toString()
        val characterSelectedGender = binding.spinnerFilterCharacterGender.selectedItem.toString()
        if (characterSelectedGender == "All genders"){
            filterCharacterGender = ""
        }else{
            filterCharacterGender = characterSelectedGender
        }
        filterLocationName = binding.editTextFilterLocationName.text.toString()
        filterLocationType = binding.editTextFilterLocationType.text.toString()
        filterLocationDimension = binding.editTextFilterLocationDimension.text.toString()
        filterEpisodeName = binding.editTextFilterEpisodeName.text.toString()
        filterEpisodeCode = binding.editTextFilterEpisodeCode.text.toString()

        preferences
            .edit()
            .putString(FILTER_CHARACTER_NAME, filterCharacterName)
            .putString(FILTER_CHARACTER_STATUS, filterCharacterStatus)
            .putString(FILTER_CHARACTER_SPECIES, filterCharacterSpecies)
            .putString(FILTER_CHARACTER_TYPE, filterCharacterType)
            .putString(FILTER_CHARACTER_GENDER, filterCharacterGender)
            .putString(FILTER_LOCATION_NAME, filterLocationName)
            .putString(FILTER_LOCATION_TYPE, filterLocationType)
            .putString(FILTER_LOCATION_DIMENSION, filterLocationDimension)
            .putString(FILTER_EPISODE_NAME, filterEpisodeName)
            .putString(FILTER_EPISODE_CODE, filterEpisodeCode)
            .putInt(APP_SETTINGS_CHARACTER_PAGES, 1)
            .putInt(APP_SETTINGS_EPISODE_PAGES, 1)
            .putInt(APP_SETTINGS_LOCATION_PAGES, 1)
            .apply()
    }
}