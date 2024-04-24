package com.example.worldwanderer.database

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://world-wanderer-e19f2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("highscores")
    lateinit var leaderboardAdapter: LeaderboardEntryAdapter


    fun fetchDataFromDatabase(leaderboardEntries: MutableList<LeaderboardEntry>, binding: ActivityLeaderboardBinding) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                leaderboardEntries.clear()
                for (snapshot in dataSnapshot.children) {
                    val totalEntries = dataSnapshot.childrenCount
                    val encodedEmail = snapshot.key ?: ""
                    val email = decodeEmail(encodedEmail)
                    val score = (snapshot.value as? Long)?.toInt() ?: 0
                    "Total highscores recorded : $totalEntries".also { binding.totalScores.text = it }
                    leaderboardEntries.add(LeaderboardEntry(email, score))
                }
                leaderboardEntries.sortByDescending { it.score }
                updateLeaderboardUI()
            }

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

    fun fetchUserHighscoreFromDatabase(email: String): LiveData<Int> {
        val highscoreLiveData = MutableLiveData<Int>()

        // Assuming "highscores" is the node where highscores are stored in the database
        val userHighscoreRef = databaseReference.child(encodeEmail(email))

        userHighscoreRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Check if the dataSnapshot exists and contains a value
                if (dataSnapshot.exists()) {
                    val highscore = dataSnapshot.getValue(Int::class.java)
                    highscoreLiveData.postValue(highscore)
                } else {
                    // If no data exists for the user, post null
                    highscoreLiveData.postValue(null)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Post null in case of error
                highscoreLiveData.postValue(null)
            }
        })

        return highscoreLiveData
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