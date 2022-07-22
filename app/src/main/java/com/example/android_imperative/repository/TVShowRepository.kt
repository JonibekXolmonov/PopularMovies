package com.example.android_imperative.repository

import com.example.android_imperative.networking.TVShowService
import javax.inject.Inject

class TVShowRepository @Inject constructor(private val tvShowService: TVShowService) {

    /*
    Retrofit related
     */
    suspend fun apiTVShowPopular(page: Int) = tvShowService.apiTVShowPopular(page)
    suspend fun apiTVShowDetails(q: Int) = tvShowService.apiTVShowDetails(q)


    /*
    Room related
     */
}