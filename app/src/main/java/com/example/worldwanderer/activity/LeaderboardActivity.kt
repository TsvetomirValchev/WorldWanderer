package com.example.worldwanderer.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.worldwanderer.adapter.LeaderboardEntryAdapter
import com.example.worldwanderer.databinding.ActivityLeaderboardBinding
import com.example.worldwanderer.domain.LeaderboardEntry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LeaderboardActivity : AppCompatActivity() {

    // Views and UI elements
    private lateinit var binding: ActivityLeaderboardBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalScoresRecorded: TextView
    private lateinit var userImage: TextView
    private lateinit var userScore: TextView

    // Data and Firebase
    private lateinit var leaderboardEntries: MutableList<LeaderboardEntry>
    private lateinit var leaderboardAdapter: LeaderboardEntryAdapter
    private val database = Firebase.database("https://world-wanderer-e19f2-default-rtdb.europe-west1.firebasedatabase.app/")
    private val databaseReference = database.getReference("highscores")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up view binding
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views and layout manager
        initViews()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Initialize leaderboard entries and adapter
        leaderboardEntries = mutableListOf()
        leaderboardAdapter = LeaderboardEntryAdapter(leaderboardEntries)
        recyclerView.adapter = leaderboardAdapter

        // Fetch data from Firebase database
        fetchDataFromDatabase()
    }

    private fun initViews(){
        // Initialize views from layout
        recyclerView = binding.recyclerView
        totalScoresRecorded = binding.totalScores
        userImage = binding.userImage
        userScore = binding.bestScore
    }

    private fun fetchDataFromDatabase() {
        // Fetch data from Firebase database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                leaderboardEntries.clear()
                // Iterate through the dataSnapshot to get leaderboard entries
                for (snapshot in dataSnapshot.children) {
                    val totalEntries = dataSnapshot.childrenCount
                    val encodedEmail = snapshot.key ?: ""
                    val email = decodeEmail(encodedEmail)
                    val score = (snapshot.value as? Long)?.toInt() ?: 0
                    // Update total scores recorded
                    "Total highscores recorded : $totalEntries".also { binding.totalScores.text = it }
                    // Add leaderboard entry
                    leaderboardEntries.add(LeaderboardEntry(email, score))
                }
                // Sort leaderboard entries by score
                leaderboardEntries.sortByDescending { it.score }
                // Update UI with the new data
                updateLeaderboardUI()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
                startActivity(Intent(applicationContext, GuessPlaceActivity::class.java))
                finish()
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateLeaderboardUI() {
        // Notify adapter of data change to update the UI
        leaderboardAdapter.notifyDataSetChanged()
    }

    private fun decodeEmail(encodedEmail: String): String {
        // Decode encoded email
        return encodedEmail.replace(',', '.')
    }

}

