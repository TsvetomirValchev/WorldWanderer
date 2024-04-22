package com.example.worldwanderer.domain

import com.google.android.gms.maps.model.LatLng

object GuessablePlaces {

    private val famousPlaceList = listOf(
        LatLng(40.690667, -74.046202),                            // Statue of Liberty, New York, USA
        LatLng(48.858994, 2.293695),                              // Eiffel Tower, Paris, France
        LatLng(51.500965, -0.124230),                             // Big Ben, London, UK
        LatLng(43.722874, 10.395610),                              // Leaning Tower of Pisa, Pisa, Italy
        LatLng(41.890210, 12.490246),                              // Colosseum, Rome, Italy
        LatLng(34.134187, -118.321615),                            // Hollywood Sign, Los Angeles, USA
        LatLng(51.502912, -0.117386),                              // Buckingham Palace, London, UK
        LatLng(41.402655, 2.174312),                               // Sagrada Família, Barcelona, Spain
        LatLng(-33.856144, 151.215155),                            // Sydney Opera House, Sydney, Australia
        LatLng(55.752563, 37.623820),                              // Red Square, Moscow, Russia
        LatLng(48.874060, 2.294341),                               // Louvre Museum, Paris, France
        LatLng(51.178936, -1.826430),                              // Stonehenge, Wiltshire, UK
        LatLng(27.174229, 78.042182),                              // Taj Mahal, Agra, India
        LatLng(29.975264, 31.138207),                              // Pyramids of Giza, Cairo, Egypt
        LatLng(48.860413, 2.337558),                               // Arc de Triomphe, Paris, France
        LatLng(51.501284, -0.141536),                              // London Eye, London, UK
        LatLng(-22.951985, -43.209790),                            // Christ the Redeemer, Rio de Janeiro, Brazil
        LatLng(25.199022, 55.273389),                              // Burj Khalifa, Dubai, UAE
        LatLng(3.156877, 101.712642),                              // Petronas Twin Towers, Kuala Lumpur, Malaysia
        LatLng(47.620947, -122.349362),                            // Space Needle, Seattle, USA
        LatLng(38.625419, -90.190066),                             // Gateway Arch, St. Louis, USA
        LatLng(-13.163141, -72.544963),                            // Machu Picchu, Peru
        LatLng(41.890251, 12.492373),                              // Roman Forum, Rome, Italy
        LatLng(40.758896, -73.985130),                             // Times Square, New York, USA
        LatLng(36.1010805, -115.15806),                            // Las Vegas Strip, Las Vegas, USA
        LatLng(35.689487, 139.691711),                             // Tokyo Tower, Tokyo, Japan
        LatLng(40.416775, -3.703790),                              // Plaza Mayor, Madrid, Spain
        LatLng(-25.670997, 28.523736),                             // Union Buildings, Pretoria, South Africa
        LatLng(37.774929, -122.419416),                            // Golden Gate Bridge, San Francisco, USA
        LatLng(35.710063, 139.810700),                             // Tokyo Disneyland, Tokyo, Japan
        LatLng(35.4437, 139.6374),                                 // Yokohama Landmark Tower, Yokohama, Japan
        LatLng(59.9386, 30.3141),                                  // Church of the Savior on Blood, St. Petersburg, Russia
        LatLng(47.4979, 19.0402),                                  // Budapest Parliament Building, Budapest, Hungary
        LatLng(48.2082, 16.3738),                                  // St. Stephen's Cathedral, Vienna, Austria
        LatLng(50.0755, 14.4378),                                  // Prague Astronomical Clock, Prague, Czech Republic
        LatLng(44.42475560671147, 26.087539148760232),             // Palace of the Parliament, Bucharest, Romania
        LatLng(50.4501, 30.5234),                                  // St. Sophia's Cathedral, Kyiv, Ukraine
        LatLng(41.90105305966253, 12.483157764246076),             // Trevi Fountain, Rome, Italy
        LatLng(35.521741, 138.757103),                             // Nagasaki Park, Nagasaki, Japan
        LatLng(36.25288052072606, 136.89951668874986),             // Shirakawa, Japan
        LatLng(27.8488, 86.7516),                                  // Nepal
        LatLng(45.4378, 12.3360),                                  // Venice, Italy
        LatLng(37.7292, -119.6357),                                // Yosemite Mountain, California, USA
        LatLng(48.142588368967964, 17.10086328529693),             // Bratislava Castle, Bratislava, Slovakia
        LatLng(50.08663387020321, 14.41021352656581),              // Charles Bridge, Prague, Czech Republic
        LatLng(47.51506585949455, 19.077845116629152),             // Heroes' Square, Budapest, Hungary
        LatLng(43.71270482065349, 24.407150654863937),             // Hija, Baykal, Bulgaria
        LatLng(42.69382965130996, 23.33452681375338),              // Sofia University, Sofia, Bulgaria
        LatLng(42.13427256647482, 23.341089155303322),             // Rila Monastery, Rila, Bulgaria
        LatLng(42.684784497400386, 23.319896387243915),            // National Palace of Culture, Sofia, Bulgaria
        LatLng(43.213032847866685, 27.944088751508968),            // Varna Dolphinarium Museum, Varna, Bulgaria
        LatLng(36.065128120673165, -112.13712061125057),           // Grand Canyon, USA
        LatLng(12.602915268700833, -70.05776153321311),            // Antilla shipwreck, Aruba , Venezuela
        LatLng(68.50860591509297, 27.482234739913483),             // Finland
        LatLng(13.413332971246504, 103.86686841534268),            // Angkor Wat, Cambodia
        LatLng(48.677370148322375, 20.976620110872062),            // Jasovská Cave, Slovak Karst, Slovakia
        LatLng(65.48438924102332, -145.41217855926362)             // Pinnel Trail, Alaska
    )

    fun getFamousPlaceList():Set<LatLng>
    {
        val list = mutableSetOf<LatLng>()
        while (list.size< famousPlaceList.size)
        {
            list.add(famousPlaceList.random())
        }
        return list
    }
}