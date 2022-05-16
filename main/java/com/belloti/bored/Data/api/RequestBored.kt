package com.belloti.bored.Data.api

import com.belloti.bored.Data.model.Boredapi
import retrofit2.Call
import retrofit2.http.GET

interface RequestBored {
    @GET("/api/activity/")
    fun recuperarBored(): Call<Boredapi?>?
}