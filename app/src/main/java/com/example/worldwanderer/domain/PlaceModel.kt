package com.example.worldwanderer.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

// Define a data class `PlaceModel` representing a place with its attributes
data class PlaceModel(
    val correctPlace: LatLng?,  // The correct location of the place
    val guessedPlace: LatLng?,  // The guessed location of the place
    val score: Int,             // The score achieved for this place
    val distance: Int           // The distance between the correct and guessed locations
) : Parcelable {               // Implementing Parcelable to allow passing instances between components

    // Parcelable constructor to read from a Parcel
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(LatLng::class.java.classLoader), // Read correctPlace from Parcel
        parcel.readParcelable(LatLng::class.java.classLoader), // Read guessedPlace from Parcel
        parcel.readInt(),                                      // Read score from Parcel
        parcel.readInt()                                       // Read distance from Parcel
    )

    // Write this object to a Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(correctPlace, flags) // Write correctPlace to Parcel
        parcel.writeParcelable(guessedPlace, flags) // Write guessedPlace to Parcel
        parcel.writeInt(score)                      // Write score to Parcel
        parcel.writeInt(distance)                   // Write distance to Parcel
    }

    // Describe the contents of this Parcelable
    override fun describeContents(): Int {
        return 0
    }

    // Companion object implementing Parcelable.Creator interface
    companion object CREATOR : Parcelable.Creator<PlaceModel> {
        // Create a PlaceModel object from a Parcel
        override fun createFromParcel(parcel: Parcel): PlaceModel {
            return PlaceModel(parcel)
        }

        // Create an array of PlaceModel objects with the given size
        override fun newArray(size: Int): Array<PlaceModel?> {
            return arrayOfNulls(size)
        }
    }
}