package com.example.foodycookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
    private static String URL_random = "https://www.themealdb.com/api/json/v1/1/random.php";
    ImageView Dishimg;
    TextView DishName,strCategory,strArea;
    EditText Search;
    Button SearchBtn,fav_btn,fav_btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchBtn = findViewById(R.id.Search);
        Search = findViewById(R.id.SearchBar);
        fav_btn = findViewById(R.id.fav);
        fav_btn2 = findViewById(R.id.fav2);
        DishName = findViewById(R.id.DishName);
        getdata_random();

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
                URL_random = "https://www.themealdb.com/api/json/v1/1/random.php";
                SearchFunction();
                getdata();

            }
        });
        fav_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, fav_list.class);
                startActivity(i);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("foodycookbook2-default-rtdb");

        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("fav", DishName.getText().toString());
                Toast.makeText(MainActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(DishName.getText().toString()).exists()){
                            Toast.makeText(MainActivity.this,"Already added as favorite",Toast.LENGTH_SHORT);
                        }
                        else{
                            Favs user = new Favs(Search.getText().toString());
                            table_user.child(Search.getText().toString()).setValue(user);
                            Toast.makeText(MainActivity.this,"Added to Favorites",Toast.LENGTH_SHORT);
                    }}

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

    });
    }
    void SearchFunction(){

        Search = findViewById(R.id.SearchBar);
        String SearchItem = Search.getText().toString();
        URL = URL.concat(SearchItem);

    }
    void getdata(){
        final RecyclerView userList = (RecyclerView) findViewById(R.id.userList);
        userList.setNestedScrollingEnabled(false);
        userList.setLayoutManager(new LinearLayoutManager(this));
        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.length()<=0){
                    Toast.makeText(MainActivity.this,"Item not found",Toast.LENGTH_SHORT);
                    Log.d("inside if", "hello");
                }

                    response = response.substring(10, response.length() - 2);

                Log.d("Code", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.setLenient().create();
                ApiData data = gson.fromJson(response, ApiData.class);
                userList.setAdapter(new FoodAdapter(MainActivity.this,data));

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Item not found",Toast.LENGTH_SHORT);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    void getdata_random(){
        final RecyclerView userList = (RecyclerView) findViewById(R.id.userList);
        userList.setNestedScrollingEnabled(false);
        userList.setLayoutManager(new LinearLayoutManager(this));
        StringRequest request = new StringRequest(URL_random, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response= response.substring(10,response.length()-2);
                Log.d("Code", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ApiData data = gson.fromJson(response, ApiData.class);
                userList.setAdapter(new FoodAdapter(MainActivity.this,data));

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
