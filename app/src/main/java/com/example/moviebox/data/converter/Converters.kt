package com.example.moviebox.data.converter

import androidx.room.TypeConverter
import com.example.moviebox.data.model.moviedetail.Genre
import com.example.moviebox.data.model.moviedetail.SpokenLanguage
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJsonString(value: List<Int>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()

    @TypeConverter
    fun genreListToJsonString(value: List<Genre>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToGenreList(value: String) = Gson().fromJson(value, Array<Genre>::class.java).toList()

    @TypeConverter
    fun spokenLangListToJsonString(value: List<SpokenLanguage>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToSpokenLangList(value: String) = Gson().fromJson(value, Array<SpokenLanguage>::class.java).toList()
}