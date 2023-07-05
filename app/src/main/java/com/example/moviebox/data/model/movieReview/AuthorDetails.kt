package com.example.moviebox.data.model.movieReview


import com.google.gson.annotations.SerializedName

data class AuthorDetails(
    @SerializedName("avatar_path")
    val avatarPath: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("username")
    val username: String? = null
)