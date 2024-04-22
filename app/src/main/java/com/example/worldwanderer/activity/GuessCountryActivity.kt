package com.example.worldwanderer.activity

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.worldwanderer.MainActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.example.worldwanderer.domain.GuessablePlaces
import java.io.IOException
import java.util.Locale
import com.example.worldwanderer.R

class GuessCountryActivity : AppCompatActivity(), OnStreetViewPanoramaReadyCallback {
    private lateinit var streetViewPanorama: StreetViewPanorama
    private lateinit var randomLocation: LatLng
    private lateinit var correctCountry: String
    private lateinit var guessEditText: EditText
    private lateinit var roundTextView: TextView
    private val visitedLocations = mutableSetOf<LatLng>()
    private var round = 1
    private var lives = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_country)

        guessEditText = findViewById(R.id.guess_edit_text)
        roundTextView = findViewById(R.id.round_text_view)

        val guessButton: Button = findViewById(R.id.guess_button)
        guessButton.setOnClickListener {
            guessCountry()
        }

        val streetViewPanoramaFragment = supportFragmentManager
            .findFragmentById(R.id.streetviewpanorama) as SupportStreetViewPanoramaFragment
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this)
    }

    override fun onStreetViewPanoramaReady(streetViewPanorama: StreetViewPanorama) {
        this.streetViewPanorama = streetViewPanorama
        nextRound()
    }

    private fun nextRound() {
        roundTextView.text = "Round $round / Lives: $lives"
        guessEditText.setText("")
        randomLocation = getRandomLocation()
        streetViewPanorama.setPosition(randomLocation)
        correctCountry = reverseGeocode(randomLocation)!!

    }

    private fun reverseGeocode(location: LatLng): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        var countryName = "Unknown Country"
        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                countryName = addresses[0].countryName
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Reverse geocoding failed", Toast.LENGTH_SHORT).show()
        }
        return countryName
    }


    private fun getRandomLocation(): LatLng {
        val placeList =
            GuessablePlaces.getFamousPlaceList().filter { !visitedLocations.contains(it) }
        if (placeList.isEmpty()) {
            Toast.makeText(this, "You have guessed every location", Toast.LENGTH_SHORT).show()
            val changeToMainMenu = Intent(applicationContext, MainActivity::class.java)
            startActivity(changeToMainMenu)
            finish()
        }
        val randomLocation = placeList.random()
        visitedLocations.add(randomLocation)
        return randomLocation
    }

    private fun guessCountry() {
        val userGuess = guessEditText.text.toString().trim()
        if (userGuess.equals(correctCountry, ignoreCase = true)) {
            handleCorrectGuess()
        } else {
            handleIncorrectGuess()
        }
    }

    private fun handleCorrectGuess() {
        Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        round++
        nextRound()
    }

    private fun handleIncorrectGuess() {
        lives--
        if (lives <= 0) {
            handleGameOver()
        } else {
            Toast.makeText(
                this,
                "Wrong guess. Country was $correctCountry. $lives lives remaining.",
                Toast.LENGTH_SHORT
            ).show()
            nextRound()
        }
    }

    private fun handleGameOver() {
        Toast.makeText(this, "Game over! You ran out of lives.", Toast.LENGTH_SHORT).show()
        finish()
    }
}
