package com.example.worldwanderer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.worldwanderer.activity.GuessCountryActivity
import com.example.worldwanderer.activity.GuessPlaceActivity
import com.example.worldwanderer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.guessThePlaceBtn?.setOnClickListener {
            startActivity(Intent(this, GuessPlaceActivity::class.java))
        }

        binding?.guessTheCountryBtn?.setOnClickListener {
            startActivity((Intent(this, GuessCountryActivity::class.java)))
        }

        binding?.playWithFriendBtn?.setOnClickListener {
            Toast.makeText(this, "This feature will be added in next update",
                Toast.LENGTH_SHORT).show()
        }
    }
}