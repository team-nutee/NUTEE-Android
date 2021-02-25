package kr.nutee.nutee_android.data.main.home

import android.os.Parcel
import android.os.Parcelable


data class Image(
    val src: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        src=parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(src)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}