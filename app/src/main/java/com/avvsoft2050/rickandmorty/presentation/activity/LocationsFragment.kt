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
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.APP_SETTINGS_LOCATION_PAGES
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterLocationDimension
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterLocationName
import com.avvsoft2050.rickandmorty.presentation.activity.MainActivity.Companion.filterLocationType
import com.avvsoft2050.rickandmorty.adapters.LocationsAdapter
import com.avvsoft2050.rickandmorty.databinding.FragmentLocationsBinding
import com.avvsoft2050.rickandmorty.pojo.LocationResult
import com.avvsoft2050.rickandmorty.presentation.viewmodel.LocationViewModel

class LocationsFragment : Fragment() {

    companion object {
        fun newInstance() = LocationsFragment()
        const val LOCATION_ID = "locationId"
    }

    private lateinit var binding: FragmentLocationsBinding
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var preferences: SharedPreferences
    private var locationPage = 1
    private var locationLastPage = 7
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: LocationsAdapter
    private var dataLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        preferences =
            this.requireActivity().getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
        if (preferences.contains(APP_SETTINGS_LOCATION_PAGES)) {
            locationPage = preferences.getInt(APP_SETTINGS_LOCATION_PAGES, 1)
        }
        progressBar = binding.progressBarLocations
        swipeRefresh = binding.swipeRefreshLocations
        adapter = LocationsAdapter(
            onClickAction = {
                showLocationDetailsFragment(it)
            }
        )
        binding.recyclerViewLocations.adapter = adapter
        setupActions()
        setupObservers()
        return binding.root
    }

    private fun setupActions() {
        if (!dataLoaded) {
            locationViewModel.loadLocationData(
                locationPage = locationPage.toString(),
                locationName = filterLocationName,
                locationType = filterLocationType,
                locationDimension = filterLocationDimension
            )
            dataLoaded = true
        }
        binding.scrollViewLocations.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    scrollReachedBottom()
                }
            })
        swipeRefresh.setOnRefreshListener {
            locationViewModel.loadLocationData(
                locationPage = locationPage.toString(),
                locationName = filterLocationName,
                locationType = filterLocationType,
                locationDimension = filterLocationDimension
            )
            swipeRefresh.isRefreshing = false
        }
    }

    private fun showLocationDetailsFragment(locationResult: LocationResult) {
        val locationDetailsFragment = LocationDetailsFragment.newInstance()
        val args = Bundle()
        args.putInt(LOCATION_ID, locationResult.id)
        locationDetailsFragment.arguments = args
        parentFragmentManager.beginTransaction().apply {
            setReorderingAllowed(true)
            replace(R.id.container, locationDetailsFragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun scrollReachedBottom() {
        if (locationPage < locationLastPage) {
            locationPage++
            preferences
                .edit()
                .putInt(APP_SETTINGS_LOCATION_PAGES, locationPage)
                .apply()
            progressBar.visibility = View.VISIBLE
            locationViewModel.loadLocationData(
                locationPage = locationPage.toString(),
                locationName = filterLocationName,
                locationType = filterLocationType,
                locationDimension = filterLocationDimension
            )
        } else {
            progressBar.visibility = View.GONE
            Toast.makeText(
                context,
                getString(R.string.no_more_locations_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setupObservers() {
        locationViewModel.location.observe(viewLifecycleOwner) {
            it?.let {
                locationLastPage = it.locationInfo?.pages!!
            }
        }
        locationViewModel.locationResult.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }
}