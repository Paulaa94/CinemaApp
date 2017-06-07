package com.example.paula.login;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.Request;

import java.util.HashMap;
import java.util.Map;

import static org.junit.runner.Request.*;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context;
        context = getApplicationContext();

        final EditText user = (EditText) findViewById(R.id.user);
        final EditText pass = (EditText) findViewById(R.id.pass);
        Button bLogin = (Button) findViewById(R.id.bLogin);

         final RequestQueue requestQueue;
        final String URL = ""; // URL to PHPscript
         final StringRequest request;

        requestQueue = Volley.newRequestQueue(this);

        bLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                request = new StringRequest(com.android.volley.Request.Method.POST, URL, new Response<String>(){

                    public void onResponse(String response){

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.names().get(0).equals("OK")){

                                Toast.makeText(getApplicationContext(),"OK "+jsonObject.getString("OK"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), UserView.class));
                            } else {

                                Toast.makeText(getApplicationContext(),"Error "+jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
            }, new Response.ErrorListener(){

                    public void onErrorResponse(VolleyError error){
                    }

                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("user", user.getText().toString());
                        hashMap.put("password", pass.getText().toString());
                        return hashMap;
                    }
                };

                requestQueue.add(request);

        }
        });



        Button bdojazd = (Button) findViewById(R.id.Baccess);

        OnClickListener l = new OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://www.google.pl/maps/place/Cinema+City+Wolno%C5%9B%C4%87/@50.8119686,19.1124264,17z/data=!4m13!1m7!3m6!1s0x4710b5d086d15829:0xa9e6772d6811988b!2saleja+Naj%C5%9Bwi%C4%99tszej+Maryi+Panny,+Cz%C4%99stochowa!3b1!8m2!3d50.8120974!4d19.1151193!3m4!1s0x0:0xaabdb094b0d834eb!8m2!3d50.8131579!4d19.1178712");

                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }


        };
        bdojazd.setOnClickListener(l);


    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}
