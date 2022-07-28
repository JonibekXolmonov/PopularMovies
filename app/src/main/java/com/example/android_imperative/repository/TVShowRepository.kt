package com.example.android_imperative.repository

import com.example.android_imperative.db.dao.TvShowDao
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.networking.TVShowService
import javax.inject.Inject

class TVShowRepository @Inject constructor(
    private val tvShowService: TVShowService,
    private val tvShowDao: TvShowDao
) {

    /*
    Retrofit related
     */
    suspend fun apiTVShowPopular(page: Int) = tvShowService.apiTVShowPopular(page)
    suspend fun apiTVShowDetails(q: Int) = tvShowService.apiTVShowDetails(q)


    /*
    Room related
     */

    suspend fun getTVShowsFromDB() = tvShowDao.getTVShowsFromDB()
    suspend fun insertTVShowToDB(tvShow: TVShow) = tvShowDao.insertTVShowToDB(tvShow)
    suspend fun deleteTvShowsFromDB() = tvShowDao.deleteTvShowsFromDB()
}