package com.example.worldwanderer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.worldwanderer.view.GameSummaryView
import com.example.worldwanderer.GoogleMapClass
import com.example.worldwanderer.R
import com.example.worldwanderer.databinding.ActivitySummaryBinding
import com.example.worldwanderer.domain.PlaceModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class SummaryActivity : AppCompatActivity(), OnMapReadyCallback {
    private var binding:ActivitySummaryBinding? = null
    private lateinit var placesList:ArrayList<PlaceModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val totalScore:Int = intent.getIntExtra("totalScore",0)
        placesList = intent.getParcelableArrayListExtra("dataList")!!
        setAdapter(placesList)
        binding?.tvFinalScore?.text = "$totalScore points"
        binding?.tvFinalDistance?.text = "${getFinalScore(placesList)} miles"

        val mapFragment = supportFragmentManager.findFragmentById(R.id.summary_map_fragment)
                as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding?.btnPlayAgain?.setOnClickListener {
            startActivity(Intent(this, GuessPlaceActivity::class.java))
            finish()
        }

        binding?.btnMainMenu?.setOnClickListener {
            finish()
        }
    }

    private fun setAdapter(dataList:ArrayList<PlaceModel>)
    {
        val recyclerView:RecyclerView = findViewById(R.id.rv_game_summary)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = GameSummaryView(dataList)
        recyclerView.adapter = adapter
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val googleMapClass = GoogleMapClass(googleMap, this)

        for (place in placesList)
        {
            googleMapClass.addBlueMarker(place.correctPlace!!)
            googleMapClass.addRedMarker(place.guessedPlace!!)
            googleMapClass.addPolyline(place.correctPlace,place.guessedPlace)
        }

    }

    private fun getFinalScore(placesList: ArrayList<PlaceModel>):Int
    {
        var finalDistance = 0
        for (place in placesList)
        {
            finalDistance+= place.distance
        }

        return finalDistance
    }
}