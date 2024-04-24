package com.example.worldwanderer.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.worldwanderer.adapter.LeaderboardEntryAdapter
import com.example.worldwanderer.database.DatabaseManager
import com.example.worldwanderer.databinding.ActivityLeaderboardBinding
import com.example.worldwanderer.domain.LeaderboardEntry

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaderboardBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalScoresRecorded: TextView
    private lateinit var userImage: TextView
    private lateinit var userScore: TextView
    private lateinit var leaderboardEntries: MutableList<LeaderboardEntry>
    private lateinit var databaseManager: DatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        databaseManager = DatabaseManager(this)
        setContentView(binding.root)
        initViews()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        leaderboardEntries = mutableListOf()
        databaseManager.leaderboardAdapter = LeaderboardEntryAdapter(leaderboardEntries)
        recyclerView.adapter = databaseManager.leaderboardAdapter
        fetchDataFromDatabase()
    }

    private fun initViews(){
        recyclerView = binding.recyclerView
        totalScoresRecorded = binding.totalScores
        userImage = binding.userImage
        userScore = binding.bestScore
    }

    private fun fetchDataFromDatabase() {
        databaseManager.fetchDataFromDatabase(leaderboardEntries, binding)
    }

    private fun decodeEmail(encodedEmail: String): String {
        return encodedEmail.replace(',', '.')
    }

}

