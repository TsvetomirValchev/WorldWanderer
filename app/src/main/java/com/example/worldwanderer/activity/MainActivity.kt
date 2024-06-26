package com.example.worldwanderer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.worldwanderer.R
import com.example.worldwanderer.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var emailTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth
        val user = auth.currentUser

        if (user == null) {
            val changeToLogin = Intent(applicationContext, LoginActivity::class.java)
            startActivity(changeToLogin)
            finish()
        }

        emailTextView = findViewById(R.id.user_email)
        emailTextView.text = "Hello, ${user?.email}"

        binding?.guessThePlaceBtn?.setOnClickListener {
            startActivity(Intent(this, GuessPlaceActivity::class.java))
        }

        binding?.guessTheCountryBtn?.setOnClickListener {
            startActivity((Intent(this, GuessCountryActivity::class.java)))
        }

        binding?.leaderboardsBtn?.setOnClickListener {
            startActivity((Intent(this, LeaderboardActivity::class.java)))
        }

        binding?.logoutBtn?.setOnClickListener {
            auth.signOut()
            val changeToLogin = Intent(applicationContext, LoginActivity::class.java)
            startActivity(changeToLogin)
            finish()
        }
    }
}