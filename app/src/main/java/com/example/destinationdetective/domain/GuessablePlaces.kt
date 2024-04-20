package com.example.destinationdetective.domain

import com.google.android.gms.maps.model.LatLng

object GuessablePlaces {

    private val famousPlaceList = listOf(
        LatLng(40.690667, -74.046202),
        LatLng(48.858994, 2.293695),
        LatLng(51.500965, -0.124230),
        LatLng(43.722874, 10.395610),
        LatLng(41.890210, 12.490246),
        LatLng(34.134187, -118.321615),
        LatLng(51.502912, -0.117386),
        LatLng(41.402655, 2.174312),
        LatLng(-33.856144, 151.215155),
        LatLng(55.752563, 37.623820),
        LatLng(48.874060, 2.294341),
        LatLng(51.178936, -1.826430),
        LatLng(27.174229, 78.042182),
        LatLng(29.975264, 31.138207),
        LatLng(48.860413, 2.337558),
        LatLng(51.501284, -0.141536),
        LatLng(-22.951985, -43.209790),
        LatLng(25.199022, 55.273389),
        LatLng(3.156877, 101.712642),
        LatLng(47.620947, -122.349362),
        LatLng(38.625419, -90.190066)
    )

    fun getFamousPlaceList():Set<LatLng>
    {
        val list = mutableSetOf<LatLng>()
        while (list.size<5)
        {
            list.add(famousPlaceList.random())
        }
        return list
    }
}