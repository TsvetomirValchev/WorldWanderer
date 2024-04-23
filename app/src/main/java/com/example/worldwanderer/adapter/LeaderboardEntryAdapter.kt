package com.example.worldwanderer.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.worldwanderer.databinding.LeaderboardEntryLayoutBinding
import com.example.worldwanderer.domain.LeaderboardEntry

class LeaderboardEntryAdapter(private val data: MutableList<LeaderboardEntry>) : RecyclerView.Adapter<LeaderboardEntryAdapter.ViewHolder>() {

    private lateinit var binding: LeaderboardEntryLayoutBinding

    class ViewHolder(private val binding: LeaderboardEntryLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    {
        private val userEmail: TextView = binding.userEmail
        private val userScore: TextView = binding.bestScore

        fun setData(email: String, score: Int){
            userEmail.text = email
            userScore.text = score.toString()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardEntryAdapter.ViewHolder {


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderboardEntryAdapter.ViewHolder, position: Int) {
        val entry = data[position]
        holder.setData(entry.email, entry.score)
    }

    override fun getItemCount(): Int {

        if(data.size > 10){
            return 10
        }

        return data.size
    }


}