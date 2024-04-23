package com.example.worldwanderer.domain

import com.google.android.gms.maps.model.LatLng

object GuessablePlaces {

    private val placesList = listOf(
        LatLng(40.68871701100475, -74.04427434832975),             // Statue of Liberty, New York, USA
        LatLng(48.85965995677045, 2.2946408998280288),             // Eiffel Tower, Paris, France
        LatLng(51.50095483791037, -0.12571488204791229),           // Big Ben, London, UK
        LatLng(43.722874, 10.395610),                              // Leaning Tower of Pisa, Pisa, Italy
        LatLng(41.89107157930109, 12.490863223085935),             // Colosseum, Rome, Italy
        LatLng(19.436304177267342, -99.15561412340433),            // Monument to the Revolution, Mexico City
        LatLng(34.134187, -118.321615),                            // Hollywood Sign, Los Angeles, USA
        LatLng(41.40352175689611, 2.1732084157399107),             // Sagrada Família, Barcelona, Spain
        LatLng(-33.856144, 151.215155),                            // Sydney Opera House, Sydney, Australia
        LatLng(55.75271518994579, 37.62367616516008),              // Red Square, Moscow, Russia
        LatLng(48.874170372979364, 2.2945712915352066),            // Arc de Triomphe, Paris, France
        LatLng(51.178936, -1.826430),                              // Stonehenge, Wiltshire, UK
        LatLng(27.174229, 78.042182),                              // Taj Mahal, Agra, India
        LatLng(29.975264, 31.138207),                              // Pyramids of Giza, Cairo, Egypt
        LatLng(48.86083144497461, 2.3363173441857827),             // Louvre Museum, Paris, France
        LatLng(24.470431464021484, 54.60806308339859),             // Yas Marina Circuit, Abu Dhabi
        LatLng(51.501284, -0.141536),                              // London Eye, London, UK
        LatLng(1.298790724645449, 103.85758580386735),             // Singapore
        LatLng(-22.951985, -43.209790),                            // Christ the Redeemer, Rio de Janeiro, Brazil
        LatLng(25.199022, 55.273389),                              // Burj Khalifa, Dubai, UAE
        LatLng(3.156877, 101.712642),                              // Petronas Twin Towers, Kuala Lumpur, Malaysia
        LatLng(38.625419, -90.190066),                             // Gateway Arch, St. Louis, USA
        LatLng(-13.163141, -72.544963),                            // Machu Picchu, Peru
        LatLng(43.73718888636711, 7.422917794225432),              // Monaco
        LatLng(40.758896, -73.985130),                             // Times Square, New York, USA
        LatLng(36.1010805, -115.15806),                            // Las Vegas Strip, Las Vegas, USA
        LatLng(35.689487, 139.691711),                             // Tokyo Tower, Tokyo, Japan
        LatLng(40.416775, -3.703790),                              // Plaza Mayor, Madrid, Spain
        LatLng(-25.741831863490447, 28.211656043124265),           // Nelson Mandela statue, Pretoria, South Africa
        LatLng(41.09372990593703, 29.064028099518218),             // Bosphorus, Istanbul, Turkey
        LatLng(40.65247678071708, 22.939820181134024),             // Solun, Greece
        LatLng(37.811053353770156, -122.47560479341807),           // Golden Gate Bridge, San Francisco, USA
        LatLng(35.45490851927871, 139.63130999315223),             // Yokohama Landmark Tower, Yokohama, Japan
        LatLng(59.94032910795738, 30.3281407512523),               // Church of the Savior on Blood, St. Petersburg, Russia
        LatLng(47.507000156828816, 19.046834903859928),            // Budapest Parliament Building, Budapest, Hungary
        LatLng(48.2081733035436, 16.373916490665835),              // St. Stephen's Cathedral, Vienna, Austria
        LatLng(50.08692378919402, 14.420788063547551),             // Prague Astronomical Clock, Prague, Czech Republic
        LatLng(44.42475560671147, 26.087539148760232),             // Palace of the Parliament, Bucharest, Romania
        LatLng(50.452788121185904, 30.51413969647281),             // St. Sophia's Cathedral, Kyiv, Ukraine
        LatLng(41.90105305966253, 12.483157764246076),             // Trevi Fountain, Rome, Italy
        LatLng(36.25288052072606, 136.89951668874986),             // Shirakawa, Japan
        LatLng(45.440499063693544, 12.321624417372872),            // Venice, Italy
        LatLng(37.72879842101672, -119.63623861315413),            // Yosemite Mountain, California, USA
        LatLng(48.142588368967964, 17.10086328529693),             // Bratislava Castle, Bratislava, Slovakia
        LatLng(50.08663387020321, 14.41021352656581),              // Charles Bridge, Prague, Czech Republic
        LatLng(47.51506585949455, 19.077845116629152),             // Heroes' Square, Budapest, Hungary
        LatLng(43.71270482065349, 24.407150654863937),             // Hija, Baykal, Bulgaria
        LatLng(42.69382965130996, 23.33452681375338),              // Sofia University, Sofia, Bulgaria
        LatLng(42.13427256647482, 23.341089155303322),             // Rila Monastery, Rila, Bulgaria
        LatLng(42.684784497400386, 23.319896387243915),            // National Palace of Culture, Sofia, Bulgaria
        LatLng(43.213032847866685, 27.944088751508968),            // Varna Dolphinarium Museum, Varna, Bulgaria
        LatLng(36.065128120673165, -112.13712061125057),           // Grand Canyon, USA // Antilla shipwreck, Aruba , Venezuela
        LatLng(68.50860591509297, 27.482234739913483),             // Finland
        LatLng(13.413332971246504, 103.86686841534268),            // Angkor Wat, Cambodia
        LatLng(48.677370148322375, 20.976620110872062),            // Jasovská Cave, Slovak Karst, Slovakia
        LatLng(65.48438924102332, -145.41217855926362),            // Pinnel Trail, Alaska
        LatLng(34.66873238168148, 135.50048215719696)              //Dōtonbori, Japan
    )

    fun getFamousPlaceList():Set<LatLng>
    {
        val list = mutableSetOf<LatLng>()
        while (list.size< placesList.size)
        {
            list.add(placesList.random())
        }
        return list
    }
}