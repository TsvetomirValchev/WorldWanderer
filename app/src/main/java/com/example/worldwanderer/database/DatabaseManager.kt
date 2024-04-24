package com.example.worldwanderer.database

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.worldwanderer.activity.GuessPlaceActivity
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
                context.startActivity(Intent(context, GuessPlaceActivity::class.java))
                (context as AppCompatActivity).finish()
            }
        })
    }

    fun saveUserScoreToDatabase(userEmail: String, score: Int) {
        databaseReference.child(userEmail).setValue(score)
    }

    fun encodeEmail(email: String?): String {
        return email!!.replace('.', ',')
    }

    private fun decodeEmail(encodedEmail: String): String {
        return encodedEmail.replace(',', '.')
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateLeaderboardUI() {
        leaderboardAdapter.notifyDataSetChanged()
    }



}