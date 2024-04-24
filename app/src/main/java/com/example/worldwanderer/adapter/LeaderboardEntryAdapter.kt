package com.example.worldwanderer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.worldwanderer.databinding.LeaderboardEntryLayoutBinding
import com.example.worldwanderer.domain.LeaderboardEntry

class LeaderboardEntryAdapter(private val data: MutableList<LeaderboardEntry>) : RecyclerView.Adapter<LeaderboardEntryAdapter.ViewHolder>() {

    // ViewHolder class to hold the views of each item in the RecyclerView. In this case that would be the entries for users' highscores
    class ViewHolder(binding: LeaderboardEntryLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        // Views in the layout
        private val userEmail: TextView = binding.userEmail
        private val userScore: TextView = binding.bestScore

        // Function to set data to views
        fun setData(email: String, score: Int) {
            userEmail.text = email
            userScore.text = score.toString()
        }
    }

    // Called when RecyclerView needs a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout for each item in the RecyclerView
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LeaderboardEntryLayoutBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    // Called by RecyclerView to display data at the specified position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = data[position]
        holder.setData(entry.email, entry.score)
    }

    // Returns the total number of items in the data set held by the adapter
    override fun getItemCount(): Int {
        // Limit the number of items shown in the leaderboard to 10
        return if (data.size > 10) {
            10
        } else {
            data.size
        }
    }
}