package com.example.worldwanderer.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.worldwanderer.domain.GuessablePlaces
import com.example.worldwanderer.GoogleMapClass
import com.example.worldwanderer.R
import com.example.worldwanderer.view.SlideAnimation
import com.example.worldwanderer.databinding.ActivityGuessPlaceBinding
import com.example.worldwanderer.domain.PlaceModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.LatLng
import kotlin.math.max

class GuessPlaceActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityGuessPlaceBinding
    private lateinit var mapView: View
    private lateinit var scoreBoard: View
    private lateinit var streetView: StreetViewPanorama
    private var mGoogleMap: GoogleMap? = null
    private lateinit var googleMapClass: GoogleMapClass
    private var round = 1
    private var health: Int = 5000
    private lateinit var correctPlace: LatLng
    private var selectedPlace: LatLng? = null
    private lateinit var correctPlaceList: List<LatLng>
    private var totalScore = 0
    private val placeModelList = ArrayList<PlaceModel>()
    private lateinit var healthProgressBar: ProgressBar
    private lateinit var healthTextView: TextView
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
        mapView = binding.clMapView
        scoreBoard = binding.llScoreBoard
        correctPlaceList = GuessablePlaces.getFamousPlaceList().toList()
        correctPlace = correctPlaceList.first()
    }

    private fun initializeHealth() {
        healthProgressBar = findViewById(R.id.pb_health)
        healthProgressBar.max = 5000
        healthProgressBar.progress = health
        healthTextView = findViewById(R.id.tv_health)
        healthTextView.text = "Health: $health"
    }

    private fun initializeMap() {
        val streetViewPanoramaFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_StreetView)
                    as SupportStreetViewPanoramaFragment?
        streetViewPanoramaFragment?.getStreetViewPanoramaAsync {
            it.setPosition(correctPlace)
            streetView = it
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_Fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun initializeListeners() {
        with(binding) {
            fbOpenMap.setOnClickListener { SlideAnimation.slideUp(mapView) }
            closeMapButton.setOnClickListener { SlideAnimation.slideDown(mapView) }
            guessButton.setOnClickListener { handleGuessButtonClicked() }
            btnNextRound.setOnClickListener { handleNextRoundButtonClicked() }
            fbHintButton.setOnClickListener { handleHintButtonClicked() }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        googleMapClass = GoogleMapClass(googleMap, this@GuessPlaceActivity)
        setMapClickListener()
    }

    private fun setMapClickListener() {
        mGoogleMap?.setOnMapClickListener {
            selectedPlace = it
            googleMapClass.setSelectedPlace(it)
            googleMapClass.setCorrectPlace(correctPlace)
            googleMapClass.addSelectedPlaceMarker(it)
            binding.guessButton.visibility = View.VISIBLE
        }
    }

    private fun handleGuessButtonClicked() {
        selectedPlace?.let {
            googleMapClass.addBlueMarker(correctPlace)
            googleMapClass.addPolyline(correctPlace, it)
            googleMapClass.zoomOnMap()
            mGoogleMap?.setOnMapClickListener(null)
            updateHealth(it)
            setTotalScore()
            showScoreBoard()
            setPlaceModel()
            selectedPlace = null
        }
    }

    private fun updateHealth(selectedLocation: LatLng) {
        val distance = googleMapClass.getDistance()
        health -= distance
        healthProgressBar.progress = health
        healthTextView.text = "Health: $health"
        if (health <= 0) {
            Toast.makeText(this, "You have ran out of health", Toast.LENGTH_SHORT).show()
            endGame()
        }

    }
    private fun handleNextRoundButtonClicked() {

        round++
        if(round >correctPlaceList.size){
            Toast.makeText(this,"You have guessed every location", Toast.LENGTH_SHORT).show()
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
        googleMapClass.addCircle()
        binding.fbHintButton.visibility = View.GONE
    }

    private fun showScoreBoard() {
        with(binding) {
            tvScoreRound.text = "Round $round"
            tvScore.text = "You got ${getScore()} points"
            tvDistance.text = "You are ${googleMapClass.getDistance()} miles away"
            pbScore.progress = getScore()
        }
        SlideAnimation.slideDown(scoreBoard)
    }

    private fun getScore(): Int {
        val distance = googleMapClass.getDistance()
        return max(0, 5000 - 2 * distance)
    }

    private fun setTotalScore() {
        totalScore += getScore()
        binding.tvFinalScore.text = totalScore.toString()
    }

    private fun setPlaceModel() {
        val place = PlaceModel(
            correctPlace,
            selectedPlace,
            getScore(),
            googleMapClass.getDistance()
        )
        placeModelList.add(place)
    }

    private fun endGame() {
        val intent = Intent(this, SummaryActivity::class.java)
        intent.putExtra("totalScore", totalScore)
        intent.putExtra("dataList", placeModelList)
        startActivity(intent)
        finish()
    }
}