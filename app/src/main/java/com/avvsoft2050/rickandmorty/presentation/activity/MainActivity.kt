package com.avvsoft2050.rickandmorty.presentation.activity

//import com.avvsoft2050.rickandmorty.databinding.DialogFilterBinding
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avvsoft2050.rickandmorty.R
import com.avvsoft2050.rickandmorty.databinding.ActivityMainBinding
import com.avvsoft2050.rickandmorty.presentation.viewmodel.CharacterViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        const val APP_SETTINGS = "appSettings"
        const val APP_SETTINGS_CHARACTER_PAGES = "appSettingsCharacterPages"
        const val APP_SETTINGS_EPISODE_PAGES = "appSettingsEpisodePages"
        const val APP_SETTINGS_LOCATION_PAGES = "appSettingsLocationPages"
        const val FILTER_CHARACTER_NAME = "filter character name"
        const val FILTER_CHARACTER_STATUS = "filter character status"
        const val FILTER_CHARACTER_SPECIES = "filter character species"
        const val FILTER_CHARACTER_TYPE = "filter character type"
        const val FILTER_CHARACTER_GENDER = "filter character gender"
        const val FILTER_LOCATION_NAME = "filter location name"
        const val FILTER_LOCATION_TYPE = "filter location type"
        const val FILTER_LOCATION_DIMENSION = "filter location dimension"
        const val FILTER_EPISODE_NAME = "filter episode name"
        const val FILTER_EPISODE_CODE = "filter episode code"

        var filterCharacterName = ""
        var filterCharacterStatus = ""
        var filterCharacterSpecies = ""
        var filterCharacterType = ""
        var filterCharacterGender = ""
        var filterLocationName = ""
        var filterLocationType = ""
        var filterLocationDimension = ""
        var filterEpisodeName = ""
        var filterEpisodeCode = ""
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var characterViewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        characterViewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
        val fragmentCharacters = CharactersFragment.newInstance()
        val fragmentLocations = LocationsFragment.newInstance()
        val fragmentEpisodes = EpisodesFragment.newInstance()

        binding.bottomNav.selectedItemId = R.id.characters
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.characters -> {
                    replaceContainer(fragmentCharacters)
                }
                R.id.locations -> {
                    replaceContainer(fragmentLocations)
                }
                R.id.episodes -> {
                    replaceContainer(fragmentEpisodes)
                }
            }
            true
        }
    }

    private fun replaceContainer(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(R.id.container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemThatWasClickedId = item.itemId
        if (itemThatWasClickedId == R.id.action_filter) {
            setupFromPreferences()
            val fragmentFilters = FiltersFragment.newInstance()
            replaceContainer(fragmentFilters)
        }
        if (itemThatWasClickedId == android.R.id.home){
            supportFragmentManager.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupFromPreferences() {
        preferences = getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
        with(preferences) {
            if (contains(FILTER_CHARACTER_NAME)) {
                filterCharacterName = getString(FILTER_CHARACTER_NAME, "").toString()
            }
            if (contains(FILTER_CHARACTER_STATUS)) {
                filterCharacterStatus = getString(FILTER_CHARACTER_STATUS, "").toString()
            }
            if (contains(FILTER_CHARACTER_SPECIES)) {
                filterCharacterSpecies = getString(FILTER_CHARACTER_SPECIES, "").toString()
            }
            if (contains(FILTER_CHARACTER_TYPE)) {
                filterCharacterType = getString(FILTER_CHARACTER_TYPE, "").toString()
            }
            if (contains(FILTER_CHARACTER_GENDER)) {
                filterCharacterGender = getString(FILTER_CHARACTER_GENDER, "").toString()
            }
            if (contains(FILTER_LOCATION_NAME)) {
                filterLocationName = getString(FILTER_LOCATION_NAME, "").toString()
            }
            if (contains(FILTER_LOCATION_TYPE)) {
                filterLocationType = getString(FILTER_LOCATION_TYPE, "").toString()
            }
            if (contains(FILTER_LOCATION_DIMENSION)) {
                filterLocationDimension = getString(FILTER_LOCATION_DIMENSION, "").toString()
            }
            if (contains(FILTER_EPISODE_NAME)) {
                filterEpisodeName = getString(FILTER_EPISODE_NAME, "").toString()
            }
            if (contains(FILTER_EPISODE_CODE)) {
                filterEpisodeCode = getString(FILTER_EPISODE_CODE, "").toString()
            }
        }
    }
}