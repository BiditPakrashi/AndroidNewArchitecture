package com.drexel.model

import com.drexel.model.api.FilmWebService
import com.drexel.model.response.AddReviewResponse
import com.drexel.model.response.FlimReviews
import com.drexel.model.response.Token

class FilmsRepository(private val webService: FilmWebService = FilmWebService()) {
    suspend fun getFilms(): FlimReviews {
        return webService.getFilms()
    }

    suspend fun login(userpassword:String): Token {
        return webService.login(userpassword)
    }

    suspend fun addReview(title:String,rating:String,authtoken:String): AddReviewResponse {
        return webService.addReview(title,rating,authtoken)
    }

    suspend fun updateReview(title:String,rating:String,authtoken:String): AddReviewResponse {
        return webService.updateReview(title,rating,authtoken)
    }


}
