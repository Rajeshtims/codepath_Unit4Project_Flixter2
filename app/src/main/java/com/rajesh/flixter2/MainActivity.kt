package com.rajesh.flixter2

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rajesh.flixter2.R
import com.rajesh.flixter2.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed" // BuildConfig.API_KEY //
private const val ARTICLE_SEARCH_URL =
    "https://api.themoviedb.org/3/movie/top_rated?api_key=$SEARCH_API_KEY"

class MainActivity : AppCompatActivity() {
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        movieRecyclerView = findViewById(R.id.articles)
        // TODO: Set up ArticleAdapter with articles
        val movieAdapter = MovieAdapter(this, movies)
        movieRecyclerView.adapter = movieAdapter

        movieRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            movieRecyclerView.addItemDecoration(dividerItemDecoration)
        }

//        movieRecyclerView.layoutManager = GridLayoutManager(this, 2).also {
//                        val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
//            movieRecyclerView.addItemDecoration(dividerItemDecoration)
//        }


        val client = AsyncHttpClient()
        client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    val parsedJson = createJson().decodeFromString(BaseResponse.serializer(), json.jsonObject.toString())
                    Log.i("Rajesh", parsedJson.toString())
                    parsedJson.movies?.let { list: List<Movie> ->
                        Log.i("KKK", "inslide list")
                        movies.addAll(list)
                    }
                    Log.d("Rajesh", movies.toString())
                    // Reload the screen
                    movieAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}