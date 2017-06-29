package com.example.paula.login;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.Request;


public class UserView extends AppCompatActivity {

    Button Brepertoire;
    Button Btickets;
    Button Bdojazd;
    TextView wyswietlaczPobranychDanych;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        Button logout = (Button) findViewById(R.id.logout);

        Brepertoire = (Button) findViewById(R.id.Brepertoire);
        Btickets = (Button) findViewById(R.id.Btickets);
        Bdojazd = (Button) findViewById(R.id.Bdojazd);
        wyswietlaczPobranychDanych = (TextView) findViewById(R.id.wyswietlaczPobranychDanych);
        wyswietlaczPobranychDanych.setMovementMethod(new ScrollingMovementMethod());
        Intent i = getIntent();
        String userName = i.getStringExtra("userName");
        requestQueue = Volley.newRequestQueue(this);

        logout.setText(userName + " (wyloguj)");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        Bdojazd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://www.google.pl/maps/place/Cinema+City+Wolno%C5%9B%C4%87/@50.8119686,19.1124264,17z/data=!4m13!1m7!3m6!1s0x4710b5d086d15829:0xa9e6772d6811988b!2saleja+Naj%C5%9Bwi%C4%99tszej+Maryi+Panny,+Cz%C4%99stochowa!3b1!8m2!3d50.8120974!4d19.1151193!3m4!1s0x0:0xaabdb094b0d834eb!8m2!3d50.8131579!4d19.1178712");

                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        Brepertoire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, "http://arcyvilk.com/CinemaApp/repertuar.json", // JSON URL data
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("Film");
                                    wyswietlaczPobranychDanych.setText("");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject film = jsonArray.getJSONObject(i);
                                        String title = film.getString("title");
                                        String director = film.getString("director");
                                        String starring = film.getString("starring");
                                        Integer showingStart = film.getInt("showingStart");
                                        Integer showingEnd = film.getInt("showingEnd");
                                        String description = film.getString("description");

                                        String krotkiePodsumowanieFilmu="<i>" +title + "</i> (reż. " + director + "<br><u>Występują:</u> "+starring+"<br>Czas seansu: " + showingStart + "-" +showingEnd + "<br><br>";
                                        wyswietlaczPobranychDanych.append(Html.fromHtml(krotkiePodsumowanieFilmu));
                                    }
                                }
                                catch (JSONException e) {
                                    wyswietlaczPobranychDanych.append(e + "\n");
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse (VolleyError error){
                                Log.e("VOLLEY", "ERROR");
                            }

                        }
                ); requestQueue.add(jsonObjectRequest);

            };
        });

        Btickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, "http://arcyvilk.com/CinemaApp/bilety.json", // JSON URL data
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("Ticket");
                                    wyswietlaczPobranychDanych.setText("");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject bilet = jsonArray.getJSONObject(i);
                                        String id = bilet.getString("id");
                                        String showing = bilet.getString("showing");
                                        Integer price = bilet.getInt("price");
                                        String ticketType = bilet.getString("ticketType");
                                        String user = bilet.getString("user");

                                        String podsumowanieBiletu="Bilet "+id+" ("+ticketType+")<br>"+showing+"<br>Cena: " + price+"<br><br>";
                                        wyswietlaczPobranychDanych.append(Html.fromHtml(podsumowanieBiletu));
                                    }
                                }
                                catch (JSONException e) {
                                    wyswietlaczPobranychDanych.append(e + "\n");
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse (VolleyError error){
                                Log.e("VOLLEY", "ERROR");
                            }

                        }
                ); requestQueue.add(jsonObjectRequest);
            };
        });



    }}