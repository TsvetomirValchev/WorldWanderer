package com.example.destinationdetective

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

class GoogleMapClass(private val mGoogleMap: GoogleMap?, private val context:Context) {

    private var correctPlace = LatLng(0.0,0.0)
    private var selectedPlace:LatLng? = null

    fun setSelectedPlace(place:LatLng?)
    {
        this.selectedPlace = place
    }

    fun setCorrectPlace(place:LatLng)
    {
        this.correctPlace = place
    }

    fun getDistance():Int
    {
        val result = FloatArray(1)
        Location.distanceBetween(
            selectedPlace?.latitude!!,
            selectedPlace?.longitude!!,
            correctPlace.latitude,
            correctPlace.longitude,
            result
        )
        return (result[0]/1609.34).roundToInt()
    }

    private var placeMarker:Marker? = null
    fun addSelectedPlaceMarker(position:LatLng)
    {
        placeMarker?.remove()
        placeMarker = mGoogleMap?.addMarker(MarkerOptions()
            .position(position)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin_50px))
        )
    }

    fun addBlueMarker(position: LatLng)
    {
        mGoogleMap?.addMarker(MarkerOptions()
            .position(position)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_pin_50px))
        )
    }

    fun addRedMarker(position: LatLng)
    {
        mGoogleMap?.addMarker(MarkerOptions()
            .position(position)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin_50px))
        )
    }

    fun addCircle()
    {
        val mLat = correctPlace.latitude
        val mLong = correctPlace.longitude
        val position = LatLng(mLat+ Random.nextDouble(0.01,0.1),
            mLong+ Random.nextDouble(0.01,0.1)
            )
        mGoogleMap?.addCircle(CircleOptions()
            .center(position)
            .fillColor(ContextCompat.getColor(context,R.color.trans_yellow))
            .strokeColor(ContextCompat.getColor(context,R.color.yellow))
            .radius(15000.0)
        )

        zoomOnMap(position,10f)
    }

    fun zoomOnMap()
    {
        val builder = LatLngBounds.builder()
        builder.include(selectedPlace!!)
        builder.include(correctPlace)
        val bounds = builder.build()
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,200)
        mGoogleMap?.animateCamera(cameraUpdate)
    }

    fun zoomOnMap(position:LatLng,zoomLevel:Float)
    {
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(position,zoomLevel)
        mGoogleMap?.animateCamera(newLatLngZoom)
    }

    fun addPolyline(correctPlace:LatLng, selectedPlace:LatLng)
    {
        val dash:PatternItem = Dash(20f)
        val gap:PatternItem = Gap(20f)
        val linePattern = listOf(gap,dash)

        mGoogleMap?.addPolyline(PolylineOptions()
            .add(correctPlace,selectedPlace)
            .color(ContextCompat.getColor(context,R.color.black))
            .pattern(linePattern)
        )
    }
}