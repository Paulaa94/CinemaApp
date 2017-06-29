package com.example.paula.login;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.android.volley.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.Request;

import java.util.HashMap;
import java.util.Map;

import static org.junit.runner.Request.*;

public class MainActivity extends AppCompatActivity {

    StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();

        final String URL = ""; // URL to PHPscript
        final RequestQueue requestQueue;
        final EditText user = (EditText)findViewById(R.id.user);
        final EditText pass = (EditText)findViewById(R.id.pass);
        requestQueue = Volley.newRequestQueue(this);
        Button bLogin = (Button) findViewById(R.id.bLogin);
        Button bBrowseAsGuest = (Button) findViewById(R.id.BbrowseAsGuest);

        bLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String userName = user.getText().toString();
                    String userPass = pass.getText().toString();
                    if (userName.equals("")  || userPass.equals("")) {
                        toastMsg("Wpisz login i haslo!");
                        return;
                    }
                    String requestURL="http://192.168.1.1:8080/loginMobile?username=" + userName + "&password=" + userPass;
                    request = new StringRequest(com.android.volley.Request.Method.POST, requestURL,
                            new Response.Listener<String>() {
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        if (jsonObject.names().get(0).equals("OK")) {

                                            Toast.makeText(getApplicationContext(), "OK " + jsonObject.getString("OK"), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), UserView.class));
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        toastMsg(e.toString());
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("user", user.getText().toString());
                            hashMap.put("password", pass.getText().toString());
                            return hashMap;
                        }
                    };
                    requestQueue.add(request);
                    zalogowaniePomyslne(userName);
                }
                catch(Exception e) {
                    toastMsg(e.toString());
                }
            }
        });
        bBrowseAsGuest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                przegladanieJakoGosc();
            }
        });
    }

    private void zalogowaniePomyslne(String userName) {
        try {
            Intent intent = new Intent(MainActivity.this, UserView.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        }
        catch(Exception e)
        {
            toastMsg(e.toString());
        }
    }
    private void przegladanieJakoGosc() {
        try {
            Intent intent = new Intent(MainActivity.this, GuestView.class);
            startActivity(intent);
        }
        catch(Exception e)
        {
            toastMsg(e.toString());
        }
    }
    private void toastMsg (String message) { //sluzy do szybkiego wyswietlania wiadomosci typu Toast
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}