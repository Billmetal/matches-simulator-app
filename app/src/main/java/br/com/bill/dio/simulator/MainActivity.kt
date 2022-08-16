package br.com.bill.dio.simulator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.bill.dio.simulator.adapters.MatchesAdapter
import br.com.bill.dio.simulator.data.MatchesApi
import br.com.bill.dio.simulator.databinding.ActivityMainBinding
import br.com.bill.dio.simulator.domain.Match
import com.google.android.material.snackbar.Snackbar
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var matchesApi: MatchesApi
    private lateinit var matchesAdapter: MatchesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHttpClient()
        setupMatchesList()
        setupMatchesRefresh()
        setupFloatingActionButton()
    }

    private fun setupHttpClient(){
       val retrofit: Retrofit = Retrofit.Builder()
           .baseUrl("https://billmetal.github.io/matches-simulator-API/")
           .addConverterFactory(GsonConverterFactory.create())
           .build()

       matchesApi = retrofit.create(MatchesApi::class.java)
    }

    private fun setupMatchesList(){
        binding.rvMatches.setHasFixedSize(true)
        binding.rvMatches.layoutManager = LinearLayoutManager(this)
        loadMatchesFromApi()
    }

    private fun loadMatchesFromApi(){
        binding.srlMatches.isRefreshing = true
        matchesApi.getMatches().enqueue(object: Callback<List<Match>> {
            override fun onResponse(call: Call<List<Match>>, response: Response<List<Match>>) {
                if(response.isSuccessful){
                    val matches: List<Match>? = response.body()
                    matchesAdapter = matches?.let { MatchesAdapter(it) }!!
                    binding.rvMatches.adapter = matchesAdapter
                } else {
                    showErrorMessage()
                }
                binding.srlMatches.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Match>>, t: Throwable) {
                showErrorMessage()
                binding.srlMatches.isRefreshing = false
            }

        })
    }

    private fun setupMatchesRefresh(){
        binding.srlMatches.setOnRefreshListener(this::loadMatchesFromApi)
    }

    private fun setupFloatingActionButton(){
        binding.fabSimulate.setOnClickListener( View.OnClickListener{
            it.animate().rotationBy(360f).setDuration(400).setListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    val r = Random
                    for (match in matchesAdapter.getMatches()){
                        match.homeTeam.score = r.nextInt(match.homeTeam.stars + 1)
                        match.awayTeam.score = r.nextInt(match.awayTeam.stars + 1)
                        matchesAdapter.notifyItemChanged(matchesAdapter.getMatches().indexOf(match))
                    }
                }
            })
        })
    }

    private fun showErrorMessage() {
        Snackbar.make(binding.fabSimulate,R.string.error_api,Snackbar.LENGTH_LONG).show()
    }
}