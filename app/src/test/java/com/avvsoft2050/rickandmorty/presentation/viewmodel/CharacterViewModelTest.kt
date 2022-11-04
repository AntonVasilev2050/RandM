package com.avvsoft2050.rickandmorty.presentation.viewmodel

import com.avvsoft2050.rickandmorty.api.ApiFactory
import com.avvsoft2050.rickandmorty.database.AppDatabase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class CharacterViewModelTest : BehaviorSpec({
    val databaseDatasource = mockk<AppDatabase>()
    val networkDatasource = mockk<ApiFactory>()
})
