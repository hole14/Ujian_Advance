package com.example.ujian_advance.data

import android.content.Context
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ujian_advance.EventsViewmodel
import com.example.ujian_advance.data.di.Injection
import com.example.ujian_advance.data.retrofit.ApiConfig
import com.example.ujian_advance.ui.detail.DetailViewModel
import com.example.ujian_advance.ui.favorite.FavoriteViewModel
import com.example.ujian_advance.ui.setting.switchmode.SettingViewModel

object ViewModelFactory{
    fun getInstance(context: Context) = viewModelFactory {
        val repository = Injection.provideRepository(context)
        val pref = Injection.provideSettingPreferences(context)
        val apiService = ApiConfig.getApiService()

        initializer {
            DetailViewModel(repository)
        }

        initializer {
            FavoriteViewModel(repository)
        }

        initializer {
            SettingViewModel(pref)
        }

        initializer {
            EventsViewmodel(apiService)
        }
    }
}