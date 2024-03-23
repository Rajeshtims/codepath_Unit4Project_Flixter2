package com.rajesh.flixter2

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


//@Keep
//@Serializable
//data class BaseResponse(
//    @SerialName("docs")
//    val docs: List<Article>?
//)
//@Keep
//@Serializable
//data class SearchNewsResponse(
//    @SerialName("response")
//    val response: BaseResponse?
//)
//@Keep
//@Serializable
//data class Article(
//    @SerialName("abstract")
//    val abstract: String?,
//    @SerialName("byline")
//    val byline: Byline?,
//    @SerialName("headline")
//    val headline: HeadLine?,
//    @SerialName("multimedia")
//    val multimedia: List<MultiMedia>?,
//) : java.io.Serializable {
//    val mediaImageUrl = "https://www.nytimes.com/${multimedia?.firstOrNull { it.url != null }?.url ?: ""}"
//}
//@Keep
//@Serializable
//data class HeadLine(
//    @SerialName("main")
//    val main: String
//) : java.io.Serializable
//@Keep
//@Serializable
//data class Byline(
//    @SerialName("original")
//    val original: String? = null
//) : java.io.Serializable
//@Keep
//@Serializable
//data class MultiMedia(
//    @SerialName("url")
//    val url: String?
//) : java.io.Serializable



@Keep
@Serializable
data class BaseResponse(
    @SerialName("results")
    val movies: MutableList<Movie>?
)


@Keep
@Serializable
data class Movie(
    @SerialName("original_title")
    val title: String?,
    @SerialName("backdrop_path")
   val cover: String?,
    @SerialName("id")
    val movieId: String?,
) : java.io.Serializable {
    val coverUrl = "https://image.tmdb.org/t/p/w500$cover"
}


@androidx.annotation.Keep
@Serializable
data class MovieDetail(
    @SerialName("original_title")
    val title: String?,
    @SerialName("overview")
    val overview: String,
    @SerialName("genres")
    val genres: List<Genre>,
    @SerialName("vote_average")
    val rating: Double,
    @SerialName("runtime")
    val runtime: Int,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("imdb_id")
    val imdbId: String
) : java.io.Serializable{
    val posterUrl = "https://image.tmdb.org/t/p/w500$posterPath"
}

@Keep
@Serializable
data class Genre(
    @SerialName("name")
    val name: String
): java.io.Serializable

