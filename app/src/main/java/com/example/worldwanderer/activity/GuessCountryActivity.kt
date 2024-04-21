package com.example.worldwanderer.activity
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.example.worldwanderer.domain.GuessablePlaces
import java.io.IOException
import java.util.Locale
import kotlin.random.Random
import com.example.worldwanderer.R

class GuessCountryActivity : AppCompatActivity(), OnStreetViewPanoramaReadyCallback {
    private lateinit var streetViewPanorama: StreetViewPanorama
    private lateinit var randomLocation: LatLng
    private lateinit var correctCountry: String
    private lateinit var guessEditText: EditText
    private lateinit var placeList:Set<LatLng>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_country)

        guessEditText = findViewById(R.id.guess_edit_text)

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

        randomLocation = getRandomLocation()
        streetViewPanorama.setPosition(randomLocation)

        correctCountry = reverseGeocode(randomLocation)!!
    }

    private fun reverseGeocode(location: LatLng): String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        var countryName = "Unknown Country";
        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                countryName = addresses[0].countryName
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Reverse geocoding failed", Toast.LENGTH_SHORT).show()
        }
        return countryName;
    }


    private fun getRandomLocation(): LatLng {
        placeList = GuessablePlaces.getFamousPlaceList()
        val position = Random.nextInt(0, placeList.size)
        return placeList.random()
    }

    private fun guessCountry() {
        // Check if the player's guess matches the correct country
        val userGuess = guessEditText.text.toString().trim()
        if (userGuess.equals(correctCountry, ignoreCase = true)) {
            Toast.makeText(this, "Congratulations! You guessed it right!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sorry, wrong guess. The correct country is $correctCountry.", Toast.LENGTH_SHORT).show()
        }
    }
}
