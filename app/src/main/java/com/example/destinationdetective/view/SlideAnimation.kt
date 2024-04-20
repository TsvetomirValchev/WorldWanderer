package com.example.destinationdetective.view

import android.view.View

object SlideAnimation {

    fun slideUp(view: View)
    {
        view.animate()
            .translationY(-view.height.toFloat())
            .setDuration(500)
            .setListener(null)
    }

    fun slideDown(view: View)
    {
        view.animate()
            .translationY(view.height.toFloat())
            .setDuration(500)
            .setListener(null)
    }

}