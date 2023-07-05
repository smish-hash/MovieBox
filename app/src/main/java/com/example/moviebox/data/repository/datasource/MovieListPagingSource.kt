package com.example.moviebox.data.repository.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviebox.data.api.MovieListApi
import com.example.moviebox.data.model.movielist.Result
import retrofit2.HttpException
import java.io.IOException

private const val TMDB_STARTING_PAGE_INDEX = 1


class MovieListPagingSource(
    private val movieListApi: MovieListApi
): PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
            val response = movieListApi.getMovies(
                page = pageIndex
            )


            LoadResult.Page(
                data = response.results ?: emptyList(),
                prevKey = if (pageIndex== TMDB_STARTING_PAGE_INDEX) null else pageIndex.minus(1),
                nextKey = if (response.results?.isEmpty() == true) null else pageIndex.plus(1)
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}