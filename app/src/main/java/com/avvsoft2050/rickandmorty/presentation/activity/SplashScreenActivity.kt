package com.avvsoft2050.rickandmorty.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.avvsoft2050.rickandmorty.databinding.ActivitySplashScreenBinding
import com.avvsoft2050.rickandmorty.presentation.viewmodel.CharacterViewModel
import com.avvsoft2050.rickandmorty.presentation.viewmodel.EpisodeViewModel
import com.avvsoft2050.rickandmorty.presentation.viewmodel.LocationViewModel

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var characterViewModel: CharacterViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var episodeViewModel: EpisodeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageViewRickAndMorty.alpha = 0f
        binding.imageViewRickAndMorty.animate().setDuration(3000).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }



    }
}