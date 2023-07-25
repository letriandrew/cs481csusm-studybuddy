 package com.example.studybuddy20

import android.os.Parcel
import android.os.Parcelable

data class Technique(val title: String, val steps: List<String>) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeStringList(steps)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Technique> {
        override fun createFromParcel(parcel: Parcel): Technique {
            return Technique(parcel)
        }

        override fun newArray(size: Int): Array<Technique?> {
            return arrayOfNulls(size)
        }
    }
}





