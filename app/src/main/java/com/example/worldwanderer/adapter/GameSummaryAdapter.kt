package com.example.worldwanderer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.worldwanderer.R
import com.example.worldwanderer.domain.PlaceModel

// Adapter class for populating a list of place summaries in a RecyclerView
class GameSummaryAdapter(private val dataList: ArrayList<PlaceModel>) :
    RecyclerView.Adapter<GameSummaryAdapter.ViewHolder>() {

    // ViewHolder class holds references to the views for each item in the RecyclerView
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRound: TextView = view.findViewById(R.id.tv_summary_round) // TextView for round number
        val tvDistance: TextView = view.findViewById(R.id.tv_summary_distance) // TextView for distance
        val tvScore: TextView = view.findViewById(R.id.tv_summary_score) // TextView for score
    }

    // Creates ViewHolders for the items in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_summary_list, parent, false) // Inflate item layout
        return ViewHolder(view) // Return a new ViewHolder
    }

    // Returns the total number of items in the data set held by the adapter
    override fun getItemCount(): Int {
        return dataList.size
    }

    // Called by RecyclerView to display the data at the specified position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val distance = dataList[position].distance // Get distance for the current place
        val score = dataList[position].score // Get score for the current place
        holder.tvRound.text = (position + 1).toString() // Display round number
        holder.tvDistance.text = "$distance km" // Display distance
        holder.tvScore.text = "$score points" // Display score
    }
}