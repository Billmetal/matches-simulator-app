package br.com.bill.dio.simulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.bill.dio.simulator.databinding.ActivityDetailBinding
import br.com.bill.dio.simulator.databinding.ActivityMainBinding
import br.com.bill.dio.simulator.domain.Match
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMatchFromExtra()
    }

    private fun loadMatchFromExtra() {
        intent?.extras?.getParcelable<Match>("MATCH")?.let {
            Glide.with(this).load(it.place.image).into(binding.ivPlace)
            supportActionBar?.title = it.place.name

            binding.tvDescription.text = it.description
            Glide.with(this).load(it.homeTeam.image).into(binding.ivHomeTeam)
            binding.tvHomeTeamName.text = it.homeTeam.name
            binding.rbHomeTeamStars.rating = it.homeTeam.stars.toFloat()
            if(it.homeTeam.score != null) binding.tvHomeTeamScore.text = it.homeTeam.score.toString()
            Glide.with(this).load(it.awayTeam.image).into(binding.ivAwayTeam)
            binding.tvAwayTeamName.text = it.awayTeam.name
            binding.rbAwayTeamStars.rating = it.awayTeam.stars.toFloat()
            if(it.awayTeam.score != null) binding.tvAwayTeamScore.text = it.awayTeam.score.toString()
        }
    }
}