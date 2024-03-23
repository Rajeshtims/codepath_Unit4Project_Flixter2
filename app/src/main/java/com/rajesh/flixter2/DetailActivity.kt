package com.rajesh.flixter2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException


private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var rating: TextView
    private lateinit var runtime: TextView
    private lateinit var overview: TextView
    private lateinit var imdb: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // TODO: Find the views for the screen
        mediaImageView = findViewById(R.id.imageView)
        titleTextView = findViewById(R.id.title)
        rating = findViewById(R.id.rating)
        overview = findViewById(R.id.overview)
        runtime = findViewById(R.id.runtime)
        imdb = findViewById(R.id.imdb)


        imdb.setOnClickListener {
            // Open browser with link
            val url = imdb.text.toString() // Replace with your link
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        // TODO: Get the extra from the Intent
        val movieId = intent.getSerializableExtra("MOVIE_ID") as String

        Log.d("Rajesh", "received movie id: $movieId")

        val client = AsyncHttpClient()
        val movieDetailUrl = "https://api.themoviedb.org/3/movie/$movieId?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
        client.get(movieDetailUrl, object : JsonHttpResponseHandler() {
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
                    val movieDetail = createJson().decodeFromString(MovieDetail.serializer(), json.jsonObject.toString())
                    Log.i("Rajesh", movieDetail.toString())
                    movieDetail?.let {
                        Glide.with(this@DetailActivity)
                            .load(movieDetail.posterUrl)
                            .into(mediaImageView)
                        titleTextView.text = movieDetail.title
                        rating.text = "Rating : " + movieDetail.rating.toString()
                        overview.text = movieDetail.overview
                        runtime.text = "Runtime : " + movieDetail.runtime.toString() + "minutes"
                        imdb.text = "https://www.imdb.com/title/${movieDetail.imdbId}"
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}