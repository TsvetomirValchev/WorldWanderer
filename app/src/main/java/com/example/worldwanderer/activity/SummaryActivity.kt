package com.example.worldwanderer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.worldwanderer.adapter.GameSummaryAdapter
import com.example.worldwanderer.GoogleMapClass
import com.example.worldwanderer.R
import com.example.worldwanderer.databinding.ActivitySummaryBinding
import com.example.worldwanderer.domain.PlaceModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SummaryActivity : AppCompatActivity(), OnMapReadyCallback {
    // View binding and Firebase instances
    private var binding: ActivitySummaryBinding? = null
    private lateinit var placesList: ArrayList<PlaceModel>
    private val auth = FirebaseAuth.getInstance()
    private val database = Firebase.database("https://world-wanderer-e19f2-default-rtdb.europe-west1.firebasedatabase.app/")
    private val databaseReference = database.getReference("highscores")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Retrieve data from intent
        val totalScore: Int = intent.getIntExtra("totalScore", 0)
        placesList = intent.getParcelableArrayListExtra("dataList")!!

        // Set up RecyclerView adapter
        setAdapter(placesList)

        // Set total score and total distance text views
        binding?.tvFinalScore?.text = "$totalScore points"
        binding?.tvFinalDistance?.text = "${getFinalScore(placesList)} km"

        // Get current user's email and encode it
        val currentUser = auth.currentUser
        val userEmail = currentUser?.email
        val encodedEmail = encodeEmail(userEmail)

        // Save user's score to the database
        saveUserScoreToDatabase(encodedEmail, totalScore)

        // Initialize and set up the Google Map fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.summary_map_fragment)
                as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Set up click listeners for buttons
        binding?.btnPlayAgain?.setOnClickListener {
            startActivity(Intent(this, GuessPlaceActivity::class.java))
            finish()
        }

        binding?.btnMainMenu?.setOnClickListener {
            finish()
        }
    }

    private fun setAdapter(dataList: ArrayList<PlaceModel>) {
        // Set up RecyclerView adapter
        val recyclerView: RecyclerView = findViewById(R.id.rv_game_summary)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = GameSummaryAdapter(dataList)
        recyclerView.adapter = adapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Set up Google Map markers and polylines
        val googleMapClass = GoogleMapClass(googleMap, this)

        for (place in placesList) {
            googleMapClass.addBlueMarker(place.correctPlace!!)
            googleMapClass.addRedMarker(place.guessedPlace!!)
            googleMapClass.addPolyline(place.correctPlace, place.guessedPlace)
        }
    }

    private fun getFinalScore(placesList: ArrayList<PlaceModel>): Int {
        // Calculate total distance
        var finalDistance = 0
        for (place in placesList) {
            finalDistance += place.distance
        }

        return finalDistance
    }

    private fun saveUserScoreToDatabase(userEmail: String, score: Int) {
        // Save user's score to the Firebase database
        databaseReference.child(userEmail).setValue(score)
    }

    private fun encodeEmail(email: String?): String {
        // Encode email for Firebase database use
        return email!!.replace('.', ',')
    }
}