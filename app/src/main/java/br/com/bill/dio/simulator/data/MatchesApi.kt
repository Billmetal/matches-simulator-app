package br.com.bill.dio.simulator.data

import br.com.bill.dio.simulator.domain.Match
import retrofit2.http.GET
import retrofit2.Call

interface MatchesApi {

    @GET("matches.json")
    fun getMatches(): Call<List<Match>>
}