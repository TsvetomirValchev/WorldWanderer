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

    private lateinit var binding: ActivityLeaderboardBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalScoresRecorded: TextView
    private lateinit var userImage: TextView
    private lateinit var userScore: TextView
    private lateinit var leaderboardEntries: MutableList<LeaderboardEntry>
    private lateinit var leaderboardAdapter: LeaderboardEntryAdapter


    private val database = Firebase.database("https://world-wanderer-e19f2-default-rtdb.europe-west1.firebasedatabase.app/")
    private val databaseReference = database.getReference("highscores")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        leaderboardEntries = mutableListOf()
        leaderboardAdapter = LeaderboardEntryAdapter(leaderboardEntries)
        recyclerView.adapter = leaderboardAdapter

        fetchDataFromDatabase()
    }

    private fun initViews(){
        recyclerView = binding.recyclerView
        totalScoresRecorded = binding.totalScores
        userImage = binding.userImage
        userScore = binding.bestScore
    }

    private fun fetchDataFromDatabase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                leaderboardEntries.clear()
                for (snapshot in dataSnapshot.children) {
                    val email = snapshot.key ?: ""
                    val score = (snapshot.value as? Long)?.toInt() ?: 0
                    leaderboardEntries.add(LeaderboardEntry(email, score))
                }
                leaderboardEntries.sortByDescending { it.score }
                updateLeaderboardUI()
            }

            override fun onCancelled(error: DatabaseError) {
                startActivity(Intent(applicationContext, GuessPlaceActivity::class.java))
                finish()
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateLeaderboardUI() {
        leaderboardAdapter.notifyDataSetChanged()
    }

}

