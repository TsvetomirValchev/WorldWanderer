package com.example.worldwanderer.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.worldwanderer.domain.GuessablePlaces
import com.example.worldwanderer.GoogleMapClass
import com.example.worldwanderer.R
import com.example.worldwanderer.utils.SlideAnimation
import com.example.worldwanderer.databinding.ActivityGuessPlaceBinding
import com.example.worldwanderer.domain.PlaceModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import kotlin.math.max
import kotlin.math.min

class GuessPlaceActivity : AppCompatActivity(), OnMapReadyCallback {
    // Views and UI elements
    private lateinit var binding: ActivityGuessPlaceBinding
    private lateinit var mapView: View
    private lateinit var scoreBoard: View
    private lateinit var streetView: StreetViewPanorama
    private lateinit var healthProgressBar: ProgressBar
    private lateinit var healthTextView: TextView

    // Game variables
    private var mGoogleMap: GoogleMap? = null
    private lateinit var googleMapClass: GoogleMapClass
    private var round = 1
    private var health: Int = 5000
    private lateinit var correctPlace: LatLng
    private var selectedPlace: LatLng? = null
    private lateinit var correctPlaceList: List<LatLng>
    private var totalScore = 0
    private val placeModelList = ArrayList<PlaceModel>()
    private var scoreboardSlidDown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuessPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        initializeMap()
        initializeListeners()
        initializeHealth()
    }

    private fun initializeViews() {
        // Initialize views from layout
        mapView = binding.clMapView
        scoreBoard = binding.llScoreBoard
        correctPlaceList = GuessablePlaces.getFamousPlaceList().toList()
        correctPlace = correctPlaceList.first()
    }

    private fun initializeHealth() {
        // Initialize health progress bar and text view
        healthProgressBar = findViewById(R.id.pb_health)
        healthProgressBar.max = 5000
        healthProgressBar.progress = health
        healthTextView = findViewById(R.id.tv_health)
        healthTextView.text = "Health: $health"
    }

    private fun initializeMap() {
        // Initialize Street View and Google Map
        val streetViewPanoramaFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_StreetView)
                    as SupportStreetViewPanoramaFragment?
        streetViewPanoramaFragment?.getStreetViewPanoramaAsync {
            it.setPosition(correctPlace)
            streetView = it
        }

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_Fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun initializeListeners() {
        // Set up click listeners for buttons
        with(binding) {
            fbOpenMap.setOnClickListener { SlideAnimation.slideUp(mapView) }
            closeMapButton.setOnClickListener { SlideAnimation.slideDown(mapView) }
            guessButton.setOnClickListener {
                if (scoreboardSlidDown) { SlideAnimation.slideDown(mapView) }
                else { handleGuessButtonClicked() } }
            guessButton.backgroundTintList = ColorStateList.valueOf(
                ResourcesCompat.getColor(
                    resources, R.color.purple, null
                ))
            btnNextRound.setOnClickListener { handleNextRoundButtonClicked() }
            fbHintButton.setOnClickListener { handleHintButtonClicked() }
            fbHintButton.backgroundTintList = ColorStateList.valueOf(
                ResourcesCompat.getColor(
                    resources, R.color.yellow, null
                ))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Callback when the Google Map is ready
        mGoogleMap = googleMap
        googleMapClass = GoogleMapClass(googleMap, this@GuessPlaceActivity)
        setMapClickListener()
    }

    private fun setMapClickListener() {
        // Set click listener for selecting a place on the map
        mGoogleMap?.setOnMapClickListener {
            selectedPlace = it
            googleMapClass.setSelectedPlace(it)
            googleMapClass.setCorrectPlace(correctPlace)
            googleMapClass.addSelectedPlaceMarker(it)
            binding.guessButton.visibility = View.VISIBLE
        }
    }

    private fun handleGuessButtonClicked() {
        // Handle guess button click
        selectedPlace?.let {
            googleMapClass.addBlueMarker(correctPlace)
            googleMapClass.addPolyline(correctPlace, it)
            googleMapClass.zoomOnMap()
            mGoogleMap?.setOnMapClickListener(null)
            setTotalScore()
            showScoreBoard()
            setPlaceModel()
            updateHealth()
            selectedPlace = null
        }
    }

    private fun updateHealth() {
        // Update health based on the distance between correct and selected place
        val distance = googleMapClass.getDistance()
        val healthToDeduct = min((distance.toDouble()*1.6), 4000.0)
        health -= healthToDeduct.toInt()
        healthProgressBar.progress = health
        healthTextView.text = "Health: $health"
        if (health <= 0) {
            // End game if health reaches 0
            Toast.makeText(this, "You have run out of health", Toast.LENGTH_SHORT).show()
            endGame()
        }
    }

    private fun handleNextRoundButtonClicked() {
        // Handle next round button click
        if (round == 1) {
            binding.fbHintButton.visibility = View.VISIBLE
        }
        round++
        scoreboardSlidDown = false
        if (round > correctPlaceList.size) {
            // End game if all rounds are completed
            Toast.makeText(this, "You have guessed every location", Toast.LENGTH_SHORT).show()
            endGame()
        }
        binding.tvRound.text = "$round"
        correctPlace = correctPlaceList.elementAt(round - 1)
        googleMapClass.setCorrectPlace(correctPlace)
        streetView.setPosition(correctPlace)
        SlideAnimation.slideUp(scoreBoard)
        SlideAnimation.slideDown(mapView)
        mGoogleMap?.clear()
        binding.guessButton.visibility = View.VISIBLE
        setMapClickListener()
        googleMapClass.zoomOnMap(LatLng(0.0, 0.0), 1f)
    }

    private fun handleHintButtonClicked() {
        // Handle hint button click
        googleMapClass.addCircle()
        binding.fbHintButton.visibility = View.GONE
    }

    private fun showScoreBoard() {
        // Show the scoreboard with score, distance, and progress bar
        with(binding) {
            tvScoreRound.text = "Round $round"
            tvScore.text = "You got ${getScore()} points"
            tvDistance.text = "You are ${googleMapClass.getDistance()} km away"
            pbScore.progress = getScore()
        }
        SlideAnimation.slideDown(scoreBoard)
        scoreboardSlidDown = true
    }

    private fun getScore(): Int {
        // Calculate score based on distance
        val distance = googleMapClass.getDistance()
        return max(0, 5000 - 2 * distance)
    }

    private fun setTotalScore() {
        // Update total score
        totalScore += getScore()
        binding.tvFinalScore.text = totalScore.toString()
    }

    private fun setPlaceModel() {
        // Set PlaceModel for the current round
        val place = PlaceModel(
            correctPlace,
            selectedPlace,
            getScore(),
            googleMapClass.getDistance()
        )
        placeModelList.add(place)
    }

    private fun endGame() {
        // End the game and start the summary activity
        val intent = Intent(this, SummaryActivity::class.java)
        intent.putExtra("totalScore", totalScore)
        intent.putExtra("dataList", placeModelList)
        startActivity(intent)
        finish()
    }
}