package com.drexel.model.api

import com.drexel.comcast.ui.films.AddReviewDTO
import com.drexel.model.response.AddReviewResponse
import com.drexel.model.response.FlimReviews
import com.drexel.model.response.Token
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.*

class FilmWebService {

    private lateinit var api: FilmApi

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .writeTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS).build()
        val retrofit = Retrofit.Builder().client(okHttpClient)
            .baseUrl("http://10.0.2.2:8090/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(FilmApi::class.java)
    }

    suspend fun getFilms(): FlimReviews {

         return api.getFilms()
    }

    suspend fun login(usernamepassword: String): Token {

        return api.login(LoginDTO(usernamepassword))
    }

    suspend fun addReview(title:String,rating:String,authtoken:String): AddReviewResponse {
        val bearerToken = "Bearer $authtoken"
        return api.addReview(AddReviewDTO(title,rating),bearerToken)
    }

    suspend fun updateReview(title:String,rating:String,authtoken:String): AddReviewResponse {
        val bearerToken = "Bearer $authtoken"
        return api.updateReview(AddReviewDTO(title,rating),bearerToken)
    }

    interface FilmApi {
        @GET("films")
        suspend fun getFilms(): FlimReviews
        @POST("login")
        suspend fun login(@Body username: LoginDTO ) : Token

        @POST("films")
        suspend fun addReview(@Body username: AddReviewDTO, @Header(value = "Authorization")  authHeader:String) : AddReviewResponse

        @PUT("films")
        suspend fun updateReview(@Body username: AddReviewDTO, @Header(value = "Authorization")  authHeader:String) : AddReviewResponse

    }

}
