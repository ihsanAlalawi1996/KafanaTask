package com.example.ihsanstask.data.endpoints

import com.example.ihsanstask.data.models.BaseModel
import retrofit2.Response
import retrofit2.http.*

interface Endpoints {

    @GET("gToH/{date}")
    suspend fun getScheduleMeeting(
        @Path("date") date: String,
    ): Response<BaseModel>
}
