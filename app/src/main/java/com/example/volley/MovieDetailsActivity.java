package com.example.volley;

import static com.example.volley.R.*;
import static com.example.volley.R.drawable.genre_background;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView poster;
    private TextView title;
    private TextView releasedYear;
    private TextView director;
    private TextView runtime;
    private TextView language;
    private TextView country;
    private LinearLayout genreLinearLayout;
    private TextView plot;
    private TextView starring;
    private TextView imdbRating;
    private TextView metaScore;
    private TextView awards;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private Sprite wave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_movie_details);
        Intent intent = getIntent();
        String imdbID = intent.getStringExtra(Constants.MOVIE_ID);
        linearLayout = findViewById(id.linearLayout);
        wave = new Wave();
        progressBar = findViewById(R.id.progress);
        progressBar.setIndeterminateDrawable(wave);

        poster = findViewById(id.movie_poster);
        title = findViewById(id.movie_title);
        releasedYear = findViewById(id.movie_released);
        director = findViewById(id.movie_director);
        runtime = findViewById(id.movie_runtime);
        language = findViewById(id.movie_language);
        country = findViewById(id.movie_country);
        plot = findViewById(id.movie_plot);
        starring = findViewById(id.movie_starring);
        imdbRating = findViewById(id.movie_imdb_rating);
        metaScore = findViewById(id.movie_meta_score);
        awards = findViewById(id.movie_awards);
        genreLinearLayout = findViewById(id.genre_linear_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 0);
        genreLinearLayout.setLayoutParams(params);
        ReadDataFromJSON(imdbID);
    }

    void ReadDataFromJSON(String imdbID) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.ID_SEARCH_URL.concat(imdbID)
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject object = response;
                try {
                    title.setText(object.getString(Constants.MDV_TITLE));
                    releasedYear.setText(Constants.MDA_RELEASED.concat(object.getString(Constants.MDV_RELEASED)));
                    director.setText(Constants.MDA_DIRECTOR.concat(object.getString(Constants.MDV_DIRECTOR)));
                    language.setText(Constants.MDA_LANGUAGE.concat(object.getString(Constants.MDV_LANGUAGE)));
                    country.setText(Constants.MDA_COUNTRY.concat(object.getString(Constants.MDV_COUNTRY)));
                    runtime.setText(Constants.MDA_RUNTIME.concat(object.getString(Constants.MDV_RUNTIME)));
                    Glide.with(MovieDetailsActivity.this)
                            .load(object.getString(Constants.MDV_POSTER)).into(poster);
                    plot.setText(object.getString(Constants.MDV_PLOT));
                    starring.setText(object.getString(Constants.MDV_ACTORS));
                    imdbRating.setText(object.getString(Constants.MDV_IMDB_RATING));
                    metaScore.setText(object.getString(Constants.MDV_METASCORE));
                    awards.setText(object.getString(Constants.MDV_AWARDS));

                    String[] genre = object.getString(Constants.MDV_GENRE).split(Constants.SEPARATOR);
                    addGenresToLinearLayout(genre);
                    progressBar.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        requestQueue.add(objectRequest);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void addGenresToLinearLayout(String[] genre) {
        for (String value : genre) {
            TextView textView = new TextView(MovieDetailsActivity.this);
            textView.setText(value);
            textView.setBackground(getDrawable(genre_background));
            textView.setTextColor(getColor(color.black));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
            params.weight = 1;
            textView.setLayoutParams(params);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            genreLinearLayout.addView(textView);
        }
    }
}