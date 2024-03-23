package com.rajesh.flixter2

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



class MovieAdapter(private val context: Context, private val movies: MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Get the individual article and bind to holder
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val mediaImageView = itemView.findViewById<ImageView>(R.id.imageView)
        private val titleTextView = itemView.findViewById<TextView>(R.id.title)

        init {
            itemView.setOnClickListener(this)
        }

        // TODO: Write a helper method to help set up the onBindViewHolder method
        override fun onClick(v: View?) {
            // TODO: Get selected article
            val movie = movies[absoluteAdapterPosition]

            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)

            Log.d("Rajesh", movie.movieId.toString())
            intent.putExtra("MOVIE_ID", movie.movieId)
            context.startActivity(intent)

        }

        fun bind(movie: Movie) {
            var temp = movie.title?:""
            Log.d("Rajesh", temp)
            titleTextView.text = movie.title
            Glide.with(context)
                .load(movie.coverUrl)
                .into(mediaImageView)
        }
    }
}