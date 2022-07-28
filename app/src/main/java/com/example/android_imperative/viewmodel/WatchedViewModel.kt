package com.example.android_imperative.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.repository.TVShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchedViewModel @Inject constructor(private val tvShowRepository: TVShowRepository) :
    ViewModel() {

    val tvShowsFromDB = MutableLiveData<List<TVShow>>()

    fun getTVShowsFromDB() {
        viewModelScope.launch {
            val tvShows = tvShowRepository.getTVShowsFromDB()
            tvShowsFromDB.postValue(tvShows)
        }
    }

    fun deleteTVShowsFromDB() {
        viewModelScope.launch {
            tvShowRepository.deleteTvShowsFromDB()
        }
    }
}