package br.com.bill.dio.simulator.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.bill.dio.simulator.DetailActivity
import br.com.bill.dio.simulator.databinding.MatchItemBinding
import br.com.bill.dio.simulator.domain.Match
import com.bumptech.glide.Glide

class MatchesAdapter(
    private val matches: List<Match>
): RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {

    class ViewHolder(binding: MatchItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding: MatchItemBinding

        init {
            this.binding = binding
        }
    }

    fun getMatches(): List<Match> {
        return matches
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding: MatchItemBinding = MatchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context: Context = holder.itemView.context
        val match: Match = matches[position]
        holder.binding.tvAwayTeamName.text = match.awayTeam.name
        Glide.with(context).load(match.awayTeam.image).circleCrop().into(holder.binding.ivAwayTeam)
        if(match.awayTeam.score != null) holder.binding.tvAwayTeamScore.text = match.awayTeam.score.toString()
        holder.binding.tvHomeTeamName.text = match.homeTeam.name
        Glide.with(context).load(match.homeTeam.image).circleCrop().into(holder.binding.ivHomeTeam)
        if(match.homeTeam.score != null) holder.binding.tvHomeTeamScore.text = match.homeTeam.score.toString()

        holder.itemView.setOnClickListener {
            val intent: Intent = Intent(context,DetailActivity::class.java)
            intent.putExtra("MATCH",match)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return matches.size
    }

}