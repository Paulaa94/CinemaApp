package com.example.paula.login;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class UserView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);


        Button logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        Intent i = getIntent();



        Button bdojazd = (Button) findViewById(R.id.butDojazd);

        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://www.google.pl/maps/place/Cinema+City+Wolno%C5%9B%C4%87/@50.8119686,19.1124264,17z/data=!4m13!1m7!3m6!1s0x4710b5d086d15829:0xa9e6772d6811988b!2saleja+Naj%C5%9Bwi%C4%99tszej+Maryi+Panny,+Cz%C4%99stochowa!3b1!8m2!3d50.8120974!4d19.1151193!3m4!1s0x0:0xaabdb094b0d834eb!8m2!3d50.8131579!4d19.1178712");

                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }

        };
        bdojazd.setOnClickListener(l);



    }


}
