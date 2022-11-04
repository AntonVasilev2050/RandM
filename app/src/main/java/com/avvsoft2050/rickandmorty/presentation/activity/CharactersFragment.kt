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
import com.avvsoft2050.rickandmorty.R
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.APP_SETTINGS
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.APP_SETTINGS_CHARACTER_PAGES
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterCharacterGender
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterCharacterName
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterCharacterSpecies
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterCharacterStatus
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterCharacterType
import com.avvsoft2050.rickandmorty.adapters.CharactersAdapter
import com.avvsoft2050.rickandmorty.databinding.FragmentCharactersBinding
import com.avvsoft2050.rickandmorty.pojo.CharacterResult
import com.avvsoft2050.rickandmorty.presentation.viewmodel.CharacterViewModel

class CharactersFragment : Fragment() {

    companion object {
        fun newInstance() = CharactersFragment()
        const val CHARACTER_ID = "characterId"
    }

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var characterViewModel: CharacterViewModel
    private lateinit var preferences: SharedPreferences
    private var characterPage = 1
    private var characterLastPage = 42
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarTop: ProgressBar
    private lateinit var adapter: CharactersAdapter
    private var dataLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        characterViewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
        preferences =
            this.requireActivity().getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
        if (preferences.contains(APP_SETTINGS_CHARACTER_PAGES)) {
            characterPage = preferences.getInt(APP_SETTINGS_CHARACTER_PAGES, 1)
        }
        progressBar = binding.progressBarCharacters
        progressBarTop = binding.progressBarCharactersTop
        adapter = CharactersAdapter(
            onClickAction = {
                showCharacterDetailsFragment(it)
            }

        )
        binding.recyclerViewCharacters.adapter = adapter
        setupActions()
        setupObservers()
        return binding.root
    }

    private fun setupActions() {
        if (!dataLoaded) {
            characterViewModel.loadCharacterData(
                characterPage.toString(),
                filterCharacterName,
                filterCharacterStatus,
                filterCharacterSpecies,
                filterCharacterType,
                filterCharacterGender)
            dataLoaded = true
        }
        binding.scrollViewCharacters.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    loadNextPageOfCharacters()
                }
                if (scrollY == 0) {
                    progressBarTop.visibility = View.VISIBLE
                    characterViewModel.loadCharacterData(
                        characterPage.toString(),
                        filterCharacterName,
                        filterCharacterStatus,
                        filterCharacterSpecies,
                        filterCharacterType,
                        filterCharacterGender)
                }
            })
    }

    private fun showCharacterDetailsFragment(characterResult: CharacterResult) {
        val characterDetailsFragment = CharacterDetailsFragment.newInstance()
        val args = Bundle()
        args.putInt(CHARACTER_ID, characterResult.id)
        characterDetailsFragment.arguments = args
        parentFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(R.id.container, characterDetailsFragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun loadNextPageOfCharacters() {
        if (characterPage < characterLastPage) {
            characterPage++
            preferences
                .edit()
                .putInt(APP_SETTINGS_CHARACTER_PAGES, characterPage)
                .apply()
            progressBar.visibility = View.VISIBLE
            characterViewModel.loadCharacterData(
                characterPage.toString(),
                filterCharacterName,
                filterCharacterStatus,
                filterCharacterSpecies,
                filterCharacterType,
                filterCharacterGender
            )
        } else {
            progressBar.visibility = View.GONE
            Toast.makeText(
                context,
                "No more characters available",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setupObservers() {

        characterViewModel.character.observe(viewLifecycleOwner) {
            it?.let {
                characterLastPage = it.characterInfo?.pages!!
            }
        }

        characterViewModel.characterResults.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
                progressBarTop.visibility = View.GONE
            }
        }
    }
}
