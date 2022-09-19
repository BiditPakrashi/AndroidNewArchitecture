package com.drexel.comcast.ui.films

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drexel.model.FilmsRepository
import com.drexel.model.response.AddReviewResponse
import com.drexel.model.response.FlimReviewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FilmReviewViewModel (private val repository: FilmsRepository = FilmsRepository()): ViewModel() {
    val filmsState: MutableState<List<FlimReviewsItem>> = mutableStateOf(emptyList<FlimReviewsItem>())
    val progressBar = mutableStateOf(value = false)
    val reviewState:MutableState<AddReviewResponse> = mutableStateOf(value = AddReviewResponse("",""))
    fun getFilmslaunch() {
        viewModelScope.launch(Dispatchers.IO) {
            val reviewsItems = getFilms()
           filmsState.value = reviewsItems
        }
    }
    fun addFilmrating(title:String,rating:String, authtoken:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                progressBar.value = true
                val review = addReview(title,rating,authtoken)

                delay(1500L)

                progressBar.value = false
                reviewState.value = review
            }catch (ex:Exception){
                progressBar.value = false
                Log.d("AddReview", "Error adding Reviews", ex)
            }
        }
    }

    fun updateFilmrating(title:String,rating:String, authtoken:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                progressBar.value = true
                val review = updateReview(title,rating,authtoken)

                delay(1500L)

                progressBar.value = false
                reviewState.value = review
            }catch (ex:Exception){
                progressBar.value = false
                Log.d("AddReview", "Error adding Reviews", ex)
            }
        }
    }

    private suspend fun getFilms(): List<FlimReviewsItem> {
     return  repository.getFilms()
    }

    private suspend fun addReview(title:String,rating:String, authtoken:String): AddReviewResponse {
        return repository.addReview(title,rating,authtoken)
    }

    private suspend fun updateReview(title:String,rating:String, authtoken:String): AddReviewResponse {
        return repository.updateReview(title,rating,authtoken)
    }
}
