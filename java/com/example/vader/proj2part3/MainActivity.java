package com.example.vader.proj2part3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
/**
 * Title: CST 338 Project 2 Part 3
 *
 * Abstract: an Android app for the Otter Library System, a book reservation
 * system for a library.
 *
 * Due December 9, 2016 at 11:55pm
 * Phillip T. Emmons
 *
 * NOTE: There is no SQLite. The OLS class contains all the datatypes for the program that is subclassed through the Application Layer.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View createAccount = findViewById(R.id.createAccBtn);
        createAccount.setOnClickListener(this);

        View placeHold = findViewById(R.id.placeHoldBtn);
        placeHold.setOnClickListener(this);

        View cancelHold = findViewById(R.id.cancelHoldBtn);
        cancelHold.setOnClickListener(this);

        View manageSystem = findViewById(R.id.manageSystemBtn);
        manageSystem.setOnClickListener(this);

       new OLS();
    }


    @Override
    public void onClick( View v ) {
        switch (v.getId()) {
            case R.id.createAccBtn:
                Intent crAccIntent = new Intent(this, CreateAccountDisplay.class);
                startActivity(crAccIntent);
                break;
            case R.id.placeHoldBtn:
                Intent plHoldIntent = new Intent(this, PlaceHoldDisplay.class);
                startActivity(plHoldIntent);
                break;
            case R.id.cancelHoldBtn:
                Intent caHoldIntent = new Intent(this, CancelHoldDisplay.class);
                startActivity(caHoldIntent);
                break;
            case R.id.manageSystemBtn:
                Intent maSysIntent = new Intent(this, ManageSystemDisplay.class);
                startActivity(maSysIntent);
                break;
            default:
                return;
        }
    }

}//EOF
