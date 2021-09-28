package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editText;
    final String sURL = Constants.BASE_URL + Constants.KEY + Constants.MULTIPLE_RESULTS;
    private ProgressBar progressBar;
    private Sprite wave;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editTextTextPersonName);
        wave = new Wave();
        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar.setIndeterminateDrawable(wave);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String MOVIE_NAME = editText.getText().toString().toLowerCase();
                recyclerView.setVisibility(View.INVISIBLE);
                LoadJson(MOVIE_NAME);
            }
        });
    }

    void LoadJson(String movieName) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                sURL.concat(movieName.trim()),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArrays = response.getJSONArray(Constants.SEARCH);
                    int n = jsonArrays.length();
                    List<JSONObject> jsonObjectList = new ArrayList<>();

                    for (int i = 0; i < n; i++) {
                        jsonObjectList.add(jsonArrays.getJSONObject(i));
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    MyListAdapter adapter = new MyListAdapter(jsonObjectList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(adapter);

                    progressBar.setVisibility(View.INVISIBLE);


                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}