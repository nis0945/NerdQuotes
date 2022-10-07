package com.eposro.apps.nerdquotes.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ProgrammingQuotesAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://programming-quotes-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProgrammingQuotesAPI::class.java)
    }
}