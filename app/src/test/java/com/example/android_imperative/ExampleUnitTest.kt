package com.example.android_imperative

import android.util.Log
import com.example.android_imperative.di.AppModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkStatusCode() = runTest {
        val response = AppModel().tvShowService().apiTVShowPopular(1)
        assertEquals(response.code(), 200)
    }

    @Test
    fun responseIsSuccessful() = runTest {
        val response = AppModel().tvShowService().apiTVShowPopular(1)
        assertTrue(response.isSuccessful)
    }

    @Test
    fun checkTvShowListNotNull() = runTest {
        val response = AppModel().tvShowService().apiTVShowPopular(1)
        assertNotNull(response.body())
        assertNotNull(response.body()!!.tv_shows)
    }

    @Test
    fun checkTvShowListSize() = runTest {
        val response = AppModel().tvShowService().apiTVShowPopular(1)
        val tvShowPopular = response.body()
        assertEquals(tvShowPopular!!.tv_shows.size, 20)
    }

    @Test
    fun checkFirstTVShowStatus() = runTest {
        val response = AppModel().tvShowService().apiTVShowPopular(1)
        val tvShowPopular = response.body()
        val tvShows = tvShowPopular!!.tv_shows
        val tvShow = tvShows[0]
        assertEquals(tvShow.status, "Running")
    }

    @Test
    fun checkDetailStatusCode() = runTest {
        val response = AppModel().tvShowService().apiTVShowDetails(35624)
        assertEquals(response.code(), 200)
    }

    @Test
    fun detailResponseIsSuccess() = runTest {
        val response = AppModel().tvShowService().apiTVShowDetails(35624)
        assertTrue(response.isSuccessful)
    }

    @Test
    fun detailIsNotNull() = runTest {
        val response = AppModel().tvShowService().apiTVShowDetails(35624)
        assertNotNull(response.body())
    }

    @Test
    fun checkCountryField() = runTest {
        val response = AppModel().tvShowService().apiTVShowDetails(35624)
        val show = response.body()!!.tvShow
        assertEquals(show.country, "US")
    }
}