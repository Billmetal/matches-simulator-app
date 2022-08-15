package br.com.bill.dio.simulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.bill.dio.simulator.databinding.ActivityDetailBinding
import br.com.bill.dio.simulator.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}