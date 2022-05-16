package com.belloti.bored.Data.api

import com.belloti.bored.Data.model.Typeapi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestType {
    @GET("/api/activity")
    fun recuperarType(@Query("type") type: String?): Call<Typeapi?>?
}