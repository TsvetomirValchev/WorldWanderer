package com.example.worldwanderer.utils

import android.view.View

object SlideAnimation {

    // Function to slide down a view this is used in the GuessPlaceActivity to move the map up to see the google map view where you put a pin to make a guess
    fun slideUp(view: View) {
        view.animate()
            .translationY(-view.height.toFloat()) // Translate the view upwards by its height
            .setDuration(500) // Set animation duration to 500 milliseconds
            .setListener(null) // Set an optional listener (null here)
    }

    // Function to slide down a view this is used in the GuessPlaceActivity to move the map down to see the actual street view of the place
    fun slideDown(view: View) {
        view.animate()
            .translationY(view.height.toFloat()) // Translate the view downwards by its height
            .setDuration(500) // Set animation duration to 500 milliseconds
            .setListener(null) // Set an optional listener (null here)
    }
}