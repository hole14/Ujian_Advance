package com.example.ujian_advance.data.retrofit

import com.example.ujian_advance.data.respone.DetailResponse
import com.example.ujian_advance.data.respone.ListEventsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvents(@Query("active") active: Int): ListEventsResponse

    @GET("events/{id}")
    suspend fun getDetailEvent(@Path("id") id: String): DetailResponse
}