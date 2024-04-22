package com.example.worldwanderer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.worldwanderer.activity.GuessCountryActivity
import com.example.worldwanderer.activity.GuessPlaceActivity
import com.example.worldwanderer.activity.LoginActivity
import com.example.worldwanderer.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth
        val user = auth.currentUser

        if(user == null){
            val changeToLogin = Intent(applicationContext, LoginActivity::class.java)
            startActivity(changeToLogin)
            finish()
        }

        binding?.guessThePlaceBtn?.setOnClickListener {
            startActivity(Intent(this, GuessPlaceActivity::class.java))
        }

        binding?.guessTheCountryBtn?.setOnClickListener {
            startActivity((Intent(this, GuessCountryActivity::class.java)))
        }

        binding?.leaderboardsBtn?.setOnClickListener {
            Toast.makeText(this, "This feature will be added in next update",
                Toast.LENGTH_SHORT).show()
        }

        binding?.logoutBtn?.setOnClickListener {
            auth.signOut()
            val changeToLogin = Intent(applicationContext, LoginActivity::class.java)
            startActivity(changeToLogin)
            finish()
        }
    }
}