package com.example.myelegantapp.network

import com.example.myelegantapp.model.BlockLog
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/BlockLog")
    suspend fun postBlockLog(@Body blockLog: BlockLog): Response<BlockLog>
}

