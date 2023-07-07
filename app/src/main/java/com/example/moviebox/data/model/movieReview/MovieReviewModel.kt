package com.example.moviebox.data.model.movieReview


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName="moviereview")
data class MovieReviewModel(
    @PrimaryKey
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<Result>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)