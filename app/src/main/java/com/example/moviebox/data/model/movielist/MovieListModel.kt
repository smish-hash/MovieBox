package com.example.moviebox.data.model.movielist


import com.google.gson.annotations.SerializedName

data class MovieListModel(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<Result?>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)