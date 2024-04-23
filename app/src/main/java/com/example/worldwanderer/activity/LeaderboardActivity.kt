package com.example.worldwanderer.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.worldwanderer.R
import com.example.worldwanderer.databinding.ActivityLeaderboardBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LeaderboardActivity : AppCompatActivity() {

    private val database = Firebase.database("https://world-wanderer-e19f2-default-rtdb.europe-west1.firebasedatabase.app/")
    private val databaseReference = database.getReference("message")
    private lateinit var binding: ActivityLeaderboardBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalScoresRecorded: TextView
    private lateinit var userImage: TextView
    private lateinit var userScore: TextView
    private lateinit var userRank: TextView
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_leaderboard)
        initViews()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Assuming you have a method to fetch data from the database
        val data = fetchDataFromDatabase()

    }

    private fun initViews(){
        recyclerView = binding.recyclerView
        totalScoresRecorded = binding.totalScores
        userImage = binding.userImage
        userScore = binding.bestScore
        userRank = binding.rank
    }

    private fun fetchDataFromDatabase(): Map<String, Int> {

        val leaderboardData : MutableMap<String, Int> = mutableMapOf(Pair("Az", 100)).toMutableMap()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.value
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return leaderboardData
    }

}
