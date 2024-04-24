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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LeaderboardActivity : AppCompatActivity() {

    // Views and UI elements
    private lateinit var binding: ActivityLeaderboardBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalScoresRecorded: TextView
    private lateinit var userImage: TextView
    private lateinit var userScore: TextView
    private lateinit var userEmail: TextView
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    private var currentUserEmail = "${currentUser?.email}"
    // Data and Firebase
    private lateinit var leaderboardEntries: MutableList<LeaderboardEntry>
    private lateinit var databaseManager: DatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up view binding
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        databaseManager = DatabaseManager(this)
        setContentView(binding.root)

        // Initialize views and layout manager
        initViews()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Initialize leaderboard entries and adapter
        leaderboardEntries = mutableListOf()
        databaseManager.leaderboardAdapter = LeaderboardEntryAdapter(leaderboardEntries)
        recyclerView.adapter = databaseManager.leaderboardAdapter
        fetchDataFromDatabase()
    }

    private fun initViews(){
        // Initialize views from layout
        recyclerView = binding.recyclerView
        totalScoresRecorded = binding.totalScores
        userImage = binding.userImage
        userScore = binding.bestScore
        userEmail = binding.yourEmail
        binding.yourEmail.text = currentUserEmail
        //We encode the email here because the database saves the emails with , sign instead of . sign
        getCurrentUserHighscore { highscore ->
            // Update the UI with the highscore value
            binding.bestScore.text = highscore
        }
    }

    private fun fetchDataFromDatabase() {
        databaseManager.fetchDataFromDatabase(leaderboardEntries, binding)
    }

    private fun getCurrentUserHighscore(callback: (String) -> Unit) {
        databaseManager.fetchUserHighscoreFromDatabase(currentUserEmail).observe(this) { highscore ->
            val scoreText = highscore?.toString() ?: "0"
            callback(scoreText)
        }
    }


}

