package com.example.moviebox.data.model.castcrew


import com.google.gson.annotations.SerializedName

data class CastAndCrewModel(
    @SerializedName("cast")
    val cast: List<Cast?>? = null,
    @SerializedName("crew")
    val crew: List<Crew?>? = null,
    @SerializedName("id")
    val id: Int? = null
)