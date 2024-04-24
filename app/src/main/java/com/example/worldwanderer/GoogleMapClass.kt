package com.example.worldwanderer

import android.content.Context
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PatternItem
import com.google.android.gms.maps.model.PolylineOptions
import kotlin.math.roundToInt
import kotlin.random.Random

class GoogleMapClass(private val mGoogleMap: GoogleMap?, private val context: Context) {

    // Define variables to store the correct place and selected place
    private var correctPlace = LatLng(0.0, 0.0)
    private var selectedPlace: LatLng? = null
    private var placeMarker: Marker? = null

    // Method to set the selected place
    fun setSelectedPlace(place: LatLng?) {
        this.selectedPlace = place
    }

    // Method to set the correct place
    fun setCorrectPlace(place: LatLng) {
        this.correctPlace = place
    }

    // Method to calculate the distance between selected and correct places
    fun getDistance(): Int {
        val result = FloatArray(1)
        Location.distanceBetween(
            selectedPlace?.latitude ?: 0.0,
            selectedPlace?.longitude ?: 0.0,
            correctPlace.latitude,
            correctPlace.longitude,
            result
        )
        return (result[0] / 1000).roundToInt() // Convert distance to kilometers and round it
    }

    // Method to add a marker for the selected place
    fun addSelectedPlaceMarker(position: LatLng) {
        placeMarker?.remove() // Remove existing marker if present
        placeMarker = mGoogleMap?.addMarker(
            MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin_50px)) // Set marker icon
        )
    }

    // Method to add a blue marker for a given position
    fun addBlueMarker(position: LatLng) {
        mGoogleMap?.addMarker(
            MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_pin_50px)) // Set marker icon
        )
    }

    // Method to add a red marker for a given position
    fun addRedMarker(position: LatLng) {
        mGoogleMap?.addMarker(
            MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin_50px)) // Set marker icon
        )
    }

    // Method to add a circle on the map
    fun addCircle() {
        val mLat = correctPlace.latitude
        val mLong = correctPlace.longitude
        val position = LatLng(mLat + Random.nextDouble(0.01, 0.1), mLong + Random.nextDouble(0.01, 0.1))
        mGoogleMap?.addCircle(
            CircleOptions()
                .center(position)
                .fillColor(ContextCompat.getColor(context, R.color.trans_yellow)) // Set circle fill color
                .strokeColor(ContextCompat.getColor(context, R.color.yellow)) // Set circle stroke color
                .radius(15000.0) // Set circle radius
        )

        zoomOnMap(position, 10f) // Zoom on the map to the specified position
    }

    // Method to zoom on the map
    fun zoomOnMap() {
        val builder = LatLngBounds.builder()
        builder.include(selectedPlace!!)
        builder.include(correctPlace)
        val bounds = builder.build()
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200)
        mGoogleMap?.animateCamera(cameraUpdate) // Animate camera to zoom on the map within the specified bounds
    }

    // Method to zoom on the map to a specific position with a specified zoom level
    fun zoomOnMap(position: LatLng, zoomLevel: Float) {
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(position, zoomLevel)
        mGoogleMap?.animateCamera(newLatLngZoom) // Animate camera to zoom on the map to the specified position with the specified zoom level
    }

    // Method to add a polyline between two points
    fun addPolyline(correctPlace: LatLng, selectedPlace: LatLng) {
        val dash: PatternItem = Dash(20f)
        val gap: PatternItem = Gap(20f)
        val linePattern = listOf(gap, dash) // Define line pattern

        mGoogleMap?.addPolyline(
            PolylineOptions()
                .add(correctPlace, selectedPlace) // Add polyline between correct and selected places
                .color(ContextCompat.getColor(context, R.color.black)) // Set polyline color
                .pattern(linePattern) // Set line pattern
        )
    }
}