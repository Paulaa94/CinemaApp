package com.example.paula.login;

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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.Request;


public class GuestView extends AppCompatActivity {

    Button Brepertuar;
    Button Bmapa;
    Button Bpowrot;
    TextView wyswietlaczRepertuaru;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_view);

        Brepertuar = (Button) findViewById(R.id.Brepertuar);
        Bmapa = (Button) findViewById(R.id.Bmapa);
        Bpowrot = (Button) findViewById(R.id.Bpowrot);
        wyswietlaczRepertuaru = (TextView) findViewById(R.id.wyswietlaczRepertuaru);
        wyswietlaczRepertuaru.setMovementMethod(new ScrollingMovementMethod());

        requestQueue = Volley.newRequestQueue(this);

        Intent i = getIntent();
        Bmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://www.google.pl/maps/place/Cinema+City+Wolno%C5%9B%C4%87/@50.8119686,19.1124264,17z/data=!4m13!1m7!3m6!1s0x4710b5d086d15829:0xa9e6772d6811988b!2saleja+Naj%C5%9Bwi%C4%99tszej+Maryi+Panny,+Cz%C4%99stochowa!3b1!8m2!3d50.8120974!4d19.1151193!3m4!1s0x0:0xaabdb094b0d834eb!8m2!3d50.8131579!4d19.1178712");

                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }

        });

        Brepertuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, "http://arcyvilk.com/CinemaApp/repertuar.json", // JSON URL data
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("Film");
                                    wyswietlaczRepertuaru.setText("");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject film = jsonArray.getJSONObject(i);
                                        String title = film.getString("title");
                                        String director = film.getString("director");
                                        String starring = film.getString("starring");
                                        Integer showingStart = film.getInt("showingStart");
                                        Integer showingEnd = film.getInt("showingEnd");
                                        String description = film.getString("description");

                                        String krotkiePodsumowanieFilmu="<i>" +title + "</i> (reż. " + director + "<br><u>Występują:</u> "+starring+"<br>Czas seansu: " + showingStart + "-" +showingEnd + "<br><br>";
                                        wyswietlaczRepertuaru.append(Html.fromHtml(krotkiePodsumowanieFilmu));
                                    }
                                }
                                catch (JSONException e) {
                                    wyswietlaczRepertuaru.append(e + "\n");
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
        Bpowrot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                wrocDoStronyLogowania();
            }
        });
    }

    private void wrocDoStronyLogowania() {
        try {
            Intent intent = new Intent(GuestView.this, MainActivity.class);
            startActivity(intent);
        }
        catch(Exception e)
        {
            toastMsg(e.toString());
        }
    }
    private void toastMsg (String message) { //sluzy do szybkiego wyswietlania wiadomosci typu Toast
        Toast.makeText(GuestView.this, message, Toast.LENGTH_SHORT).show();
    }
}
