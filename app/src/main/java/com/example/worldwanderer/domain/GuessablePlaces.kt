package com.example.worldwanderer.domain

import com.google.android.gms.maps.model.LatLng

object GuessablePlaces {

    private val famousPlaceList = listOf(
        LatLng(40.690667, -74.046202),     // Statue of Liberty, New York, USA
        LatLng(48.858994, 2.293695),       // Eiffel Tower, Paris, France
        LatLng(51.500965, -0.124230),      // Big Ben, London, UK
        LatLng(43.722874, 10.395610),      // Leaning Tower of Pisa, Pisa, Italy
        LatLng(41.890210, 12.490246),      // Colosseum, Rome, Italy
        LatLng(34.134187, -118.321615),    // Hollywood Sign, Los Angeles, USA
        LatLng(51.502912, -0.117386),      // Buckingham Palace, London, UK
        LatLng(41.402655, 2.174312),       // Sagrada Família, Barcelona, Spain
        LatLng(-33.856144, 151.215155),    // Sydney Opera House, Sydney, Australia
        LatLng(55.752563, 37.623820),      // Red Square, Moscow, Russia
        LatLng(48.874060, 2.294341),       // Louvre Museum, Paris, France
        LatLng(51.178936, -1.826430),      // Stonehenge, Wiltshire, UK
        LatLng(27.174229, 78.042182),      // Taj Mahal, Agra, India
        LatLng(29.975264, 31.138207),      // Pyramids of Giza, Cairo, Egypt
        LatLng(48.860413, 2.337558),       // Arc de Triomphe, Paris, France
        LatLng(51.501284, -0.141536),      // London Eye, London, UK
        LatLng(-22.951985, -43.209790),    // Christ the Redeemer, Rio de Janeiro, Brazil
        LatLng(25.199022, 55.273389),      // Burj Khalifa, Dubai, UAE
        LatLng(3.156877, 101.712642),      // Petronas Twin Towers, Kuala Lumpur, Malaysia
        LatLng(47.620947, -122.349362),    // Space Needle, Seattle, USA
        LatLng(38.625419, -90.190066),     // Gateway Arch, St. Louis, USA
        LatLng(-13.163141, -72.544963),    // Machu Picchu, Peru
        LatLng(41.890251, 12.492373),      // Roman Forum, Rome, Italy
        LatLng(40.758896, -73.985130),     // Times Square, New York, USA
        LatLng(36.1010805,-115.15806),    // Las Vegas Strip, Las Vegas, USA
        LatLng(35.689487, 139.691711),     // Tokyo Tower, Tokyo, Japan
        LatLng(40.416775, -3.703790),      // Plaza Mayor, Madrid, Spain
        LatLng(-25.670997, 28.523736),     // Union Buildings, Pretoria, South Africa
        LatLng(37.774929, -122.419416)     // Golden Gate Bridge, San Francisco, USA
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