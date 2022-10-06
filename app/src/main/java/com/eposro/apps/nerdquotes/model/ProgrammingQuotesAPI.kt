package com.eposro.apps.nerdquotes.model

import com.eposro.apps.nerdquotes.model.entities.ProgrammingQuote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProgrammingQuotesAPI {

    @GET("/Quotes")
    suspend fun getQuotes(
        @Query("count") count: Int = 10
    ): Response<List<ProgrammingQuote>>

    @GET("/Quotes/random")
    suspend fun getRandomQuote(): Response<ProgrammingQuote>
}