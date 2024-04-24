package com.example.worldwanderer.database

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.worldwanderer.activity.MainActivity
import com.example.worldwanderer.adapter.LeaderboardEntryAdapter
import com.example.worldwanderer.databinding.ActivityLeaderboardBinding
import com.example.worldwanderer.domain.LeaderboardEntry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DatabaseManager(private val context: Context) {

    // Reference to the Firebase database
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance("https://world-wanderer-e19f2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("highscores")

    // Adapter class for the leaderboard entries
    lateinit var leaderboardAdapter: LeaderboardEntryAdapter

    // Fetches leaderboard entries from the database
    fun fetchDataFromDatabase(leaderboardEntries: MutableList<LeaderboardEntry>, binding: ActivityLeaderboardBinding) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear the existing list of leaderboard entries
                leaderboardEntries.clear()
                // Iterate through each child node in the database snapshot
                for (snapshot in dataSnapshot.children) {
                    // Retrieve the total number of entries in the leaderboard
                    val totalEntries = dataSnapshot.childrenCount
                    // Retrieve the encoded email and decode it
                    val encodedEmail = snapshot.key ?: ""
                    // Decode the email retrieved from the database, so that it looks like an actual email in the view
                    val email = decodeEmail(encodedEmail)
                    // Retrieve the score associated with the email
                    val score = (snapshot.value as? Long)?.toInt() ?: 0
                    // Update the total highscores recorded in the UI
                    "Total highscores recorded : $totalEntries".also {
                        binding.totalScores.text = it
                    }
                    // Add the leaderboard entry to the list
                    leaderboardEntries.add(LeaderboardEntry(email, score))
                    // Updates the UI with the new leaderboard data
                    updateLeaderboardUI()
                }
            }
            // Handle database error by starting MainActivity
            override fun onCancelled(error: DatabaseError) {
                context.startActivity(Intent(context, MainActivity::class.java))
                (context as AppCompatActivity).finish()
            }
        })
    }

    // Saves user score to the database if it's higher than the current score
    fun saveUserScoreToDatabase(userEmail: String, score: Int) {
        // Retrieve the current score from the database
        databaseReference.child(userEmail)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val currentScore = dataSnapshot.getValue(Int::class.java) ?: 0
                    // Check if the new score is higher than the current score
                    if (score > currentScore) {
                        // Update the score in the database only if the new score is higher
                        databaseReference.child(userEmail).setValue(score)
                    }
                }
                // Handle database error by starting MainActivity
                override fun onCancelled(error: DatabaseError) {
                    context.startActivity(Intent(context, MainActivity::class.java))
                    (context as AppCompatActivity).finish()
                }
            })
    }

    // Encodes an email address for use as a database key
    fun encodeEmail(email: String?): String {
        return email!!.replace('.', ',')
    }

    // Decodes an encoded email address
    private fun decodeEmail(encodedEmail: String): String {
        return encodedEmail.replace(',', '.')
    }

    // Updates the UI with the new leaderboard data
    @SuppressLint("NotifyDataSetChanged")
    private fun updateLeaderboardUI() {
        leaderboardAdapter.notifyDataSetChanged()
    }


}