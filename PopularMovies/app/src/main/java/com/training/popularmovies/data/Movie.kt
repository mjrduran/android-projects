package com.training.popularmovies.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by moacir on 26/02/17.
 */

data class Movie(@SerializedName("poster_path") val posterPath: String?,
                 @SerializedName("overview") val overview: String?,
                 @SerializedName("release_date") val releaseDate: String?,
                 @SerializedName("id") val id: Int?,
                 @SerializedName("original_title") val originalTitle: String?,
                 @SerializedName("vote_average") val voteAverage: Double?) : Parcelable {

    companion object{
        @JvmField val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readInt(), source.readString(), source.readDouble())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(posterPath)
        dest?.writeString(overview)
        dest?.writeString(releaseDate)
        dest?.writeInt(id!!)
        dest?.writeString(originalTitle)
        dest?.writeDouble(voteAverage!!)
    }

    override fun describeContents() = 0


}



























