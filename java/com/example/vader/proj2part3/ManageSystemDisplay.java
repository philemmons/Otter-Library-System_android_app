package com.example.vader.proj2part3;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Title: CST 338 Project 2 Part 3
 * Due December 9, 2016 at 11:55pm
 * Phillip T. Emmons
 *
 * NOTE: There is no SQLite. The OLS class contains all the datatypes for the program that is subclassed through the Application Layer.
 */

public class ManageSystemDisplay extends Activity implements View.OnClickListener {

    private EditText etUser2, etPass2;
    private TextView tvc, transLog, hide;

    private int counter = 2;
    private Button yes,bLogin, no;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ma_sys_display);

        bLogin = (Button) findViewById(R.id.b1);
        bLogin.setOnClickListener(this);

        yes = (Button) findViewById(R.id.b2);
        yes.setOnClickListener(this);
        yes.setEnabled(false);
        yes.setVisibility(View.INVISIBLE);

        no = (Button) findViewById(R.id.b3);
        no.setOnClickListener(this);
        no.setEnabled(false);
        no.setVisibility(View.INVISIBLE);

        hide = (TextView) findViewById(R.id.tv1);
        hide.setVisibility(View.INVISIBLE);

        tvc = (TextView) findViewById(R.id.tvCounter);
        etUser2 = (EditText) findViewById(R.id.un2);
        etPass2 = (EditText) findViewById(R.id.pw2);
        transLog = (TextView)findViewById(R.id.tvLog);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.b1) {
            String tempUser = etUser2.getText().toString();
            String tempPass = etPass2.getText().toString();
            if (tempUser.equals(Librarian.getUsername()) && tempPass.equals(Librarian.getPassword())) {
                Toast.makeText(ManageSystemDisplay.this, "Welcome Librarian.", Toast.LENGTH_SHORT).show();
                etUser2.setText("");
                etPass2.setText("");
                displayLog();
                yes.setEnabled(true);
                yes.setVisibility(View.VISIBLE);
                no.setEnabled(true);
                no.setVisibility(View.VISIBLE);
                hide.setVisibility(View.VISIBLE);
                bLogin.setEnabled(false);
            } else {
                Toast.makeText(ManageSystemDisplay.this, "Librarian login required.", Toast.LENGTH_SHORT).show();
                counter--;
                tvc.setText(Integer.toString(counter));
                if (counter == 0) {
                    popUp();
                }
            }
        }else if(v.getId() == R.id.b2){
            Intent i = new Intent(ManageSystemDisplay.this, AddBookToLibrary.class);
            startActivity(i);
            finish();
        }else if(v.getId() == R.id.b3){
            Intent i = new Intent(ManageSystemDisplay.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }


    private void displayLog(){
        OLS allData = (OLS) this.getApplicationContext();

        StringBuilder stBuild = new StringBuilder("Transaction type: New Accounts\n");
        for( Transaction superLoop: allData.tranNewAcc){
            stBuild.append("Customer’s username: ").append(superLoop.getUsername()).append("\n")
                    .append("Transaction date and time: ").append(superLoop.getTransTime()).append("\n");
        }

        stBuild.append("Transaction type: Cancellations\n");
        for( TransCancel superLoop: allData.tranCancel){
            stBuild.append(superLoop.getUsername()).append("  ")
                    .append(superLoop.getTitle()).append("  ")
                    .append(superLoop.getPickupTime()).append("  ")
                    .append(superLoop.getReturnTime()).append("  ")
                    .append(superLoop.getResNum()).append("  ")
                    .append(superLoop.getTransTime()).append("\n");
        }

        stBuild.append("Transaction type: Place Holds\n");
        for( TransHold superLoop: allData.tranHold){
            stBuild.append(superLoop.getUsername()).append("  ")
                    .append(superLoop.getTransTime()).append("  ")
                    .append(superLoop.getTitle()).append("  ")
                    .append(superLoop.getPickupTime()).append("  ")
                    .append(superLoop.getReturnTime()).append("  ")
                    .append(superLoop.getResNum()).append("  ")
                    .append(superLoop.getTotalFee()).append("\n");
        }
        String stLog = stBuild.toString();
        transLog.setText(stLog);

    }


    private void popUp() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Returning to Main Menu.")
                .setPositiveButton("OKAY",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(ManageSystemDisplay.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
        AlertDialog helpDialog = dialog.create();
        helpDialog.show();
    }

}//EOF