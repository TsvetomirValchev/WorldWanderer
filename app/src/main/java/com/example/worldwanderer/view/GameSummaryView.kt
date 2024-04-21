package com.example.worldwanderer.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.worldwanderer.R
import com.example.worldwanderer.domain.PlaceModel

class GameSummaryView(private val dataList:ArrayList<PlaceModel>):
    RecyclerView.Adapter<GameSummaryView.ViewHolder>()
{
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val tvRound:TextView = view.findViewById(R.id.tv_summary_round)
        val tvDistance:TextView = view.findViewById(R.id.tv_summary_distance)
        val tvScore:TextView = view.findViewById(R.id.tv_summary_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_summary_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val distance = dataList[position].distance
        val score = dataList[position].score
        holder.tvRound.text = (position+1).toString()
        holder.tvDistance.text = "$distance miles"
        holder.tvScore.text = "$score points"
    }
}