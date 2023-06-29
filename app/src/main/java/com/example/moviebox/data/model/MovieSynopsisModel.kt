package com.example.moviebox.data.model

import com.example.moviebox.data.model.castcrew.CastAndCrewModel
import com.example.moviebox.data.model.movieReview.MovieReviewModel
import com.example.moviebox.data.model.moviedetail.MovieDetailModel

data class MovieSynopsisModel(
    val movieDetail: MovieDetailModel,
    val movieCastCrew: CastAndCrewModel,
    val movieReview: MovieReviewModel
)
