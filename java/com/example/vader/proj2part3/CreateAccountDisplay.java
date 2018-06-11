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

import java.util.regex.Pattern;

/**
 * Title: CST 338 Project 2 Part 3
 * Due December 9, 2016 at 11:55pm
 * Phillip T. Emmons
 *
 * NOTE: There is no SQLite. The OLS class contains all the datatypes for the program that is subclassed through the Application Layer.
 */

public class CreateAccountDisplay extends Activity implements View.OnClickListener {


    private EditText etUser, etPass;
    private TextView attView;

    private int counter = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cr_acc_display);

        Button verify = (Button) findViewById(R.id.verifyBtn);
        attView = (TextView)findViewById(R.id.numAttView);
        etUser = (EditText) findViewById(R.id.userName);
        etPass = (EditText) findViewById(R.id.pw2);

        verify.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        OLS allData = (OLS) this.getApplicationContext();
        String tempUser = etUser.getText().toString();
        String tempPass = etPass.getText().toString();
        if (validUserInfo(tempUser) && validUserInfo(tempPass) && checkUserName(tempUser)) {
            allData.cust.add(new Customer(tempUser, tempPass));
            Toast.makeText(CreateAccountDisplay.this, "Account has been created successfully.", Toast.LENGTH_SHORT).show();
            allData.tranNewAcc.add(new Transaction(tempUser));
            Toast.makeText(CreateAccountDisplay.this, "Transaction logged.", Toast.LENGTH_SHORT).show();
            popUp();
        } else {
            Toast.makeText(CreateAccountDisplay.this, "Attempt Failed", Toast.LENGTH_SHORT).show();
            counter --;
            attView.setText(Integer.toString(counter));
            if( counter == 0){
                popUp();
            }
        }
    }


    private void popUp(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
            .setTitle("Return to main menu.")
            .setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(CreateAccountDisplay.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
        });
        AlertDialog helpDialog = dialog.create();
        helpDialog.show();
    }


    private boolean checkUserName( String tempUser ){
        OLS allData = (OLS) this.getApplicationContext();

        for( int i= 0; i< allData.cust.size(); i++){
            if( tempUser.equals( allData.cust.get(i).getUserName() ) ){
                String checkResponse = "Username already exist";
                Toast.makeText(CreateAccountDisplay.this, checkResponse, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


    private boolean validUserInfo( String temp ){
        Pattern letter = Pattern.compile("[a-zA-z][a-zA-z][a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$]");

        String validResponse;
        if( temp.length() < 5){
            validResponse = "MIN length is five.";
            Toast.makeText(CreateAccountDisplay.this, validResponse, Toast.LENGTH_SHORT).show();
            return false;
        }else if( temp.equals( Librarian.getUsername() )){
            validResponse = "Reserved Username.";
            Toast.makeText(CreateAccountDisplay.this, validResponse, Toast.LENGTH_SHORT).show();
            return false;
        }else if(( letter.matcher(temp).find() ) &&
                ( digit.matcher(temp).find()  ) &&
                ( special.matcher(temp).find()) ) {
            validResponse = temp + ": correct format.";
            Toast.makeText(CreateAccountDisplay.this, validResponse, Toast.LENGTH_SHORT).show();
            return true;
        }
        validResponse = temp +": incorrect format.";
        Toast.makeText(CreateAccountDisplay.this, validResponse, Toast.LENGTH_LONG).show();
        return false;
    }
}

