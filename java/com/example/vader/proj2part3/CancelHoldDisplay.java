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

public class CancelHoldDisplay extends Activity implements View.OnClickListener {

    private String pickupTime = "", returnTime = "", title = "";
    protected Button selRes, login, confirm;
    private EditText etUser, etPass, etNum;
    private TextView display, attempts, output;
    private String stUser, stPass;
    private int indexCancelHold = 0,resNum, counter = 2,  cancelNum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ca_hold_display);

        login = (Button) findViewById(R.id.b1);
        login.setOnClickListener(this);

        selRes = (Button) findViewById(R.id.b2);
        selRes.setOnClickListener(this);
        selRes.setEnabled(false);

        confirm = (Button) findViewById(R.id.b3);
        confirm.setOnClickListener(this);
        confirm.setEnabled(false);

        display = (TextView) findViewById(R.id.tvDisplay);
        attempts = (TextView) findViewById(R.id.tvCount);
        output = (TextView) findViewById(R.id.tvOut);

        etUser = (EditText) findViewById(R.id.et1);
        etPass = (EditText) findViewById(R.id.et2);
        etNum = (EditText)findViewById(R.id.et3);
    }


    @Override
    public void onClick(View v) {
        OLS allData = (OLS) this.getApplicationContext();

         stUser = etUser.getText().toString();
         stPass = etPass.getText().toString();

        if (v.getId() == R.id.b1) {//Login button
            if(custLogin(stUser, stPass)) {
                listRes();
                selRes.setEnabled(true);
                login.setEnabled(false);
            }else{
                counter--;
                attempts.setText(Integer.toString(counter));
                Toast.makeText(CancelHoldDisplay.this, "Invalid Login.", Toast.LENGTH_SHORT).show();
                if (counter == 0) {
                    popUp();
                }
            }
        }else if (v.getId() == R.id.b2) { //select reservation button
            cancelNum = Integer.parseInt( etNum.getText().toString() );
            cancelHold1(cancelNum);
            Toast.makeText(CancelHoldDisplay.this, "Do you really want to cancel the reservation?", Toast.LENGTH_LONG).show();
            confirm.setEnabled(true);
            selRes.setEnabled(false);
        }else if (v.getId() == R.id.b3) {
            cancelHold2(cancelNum);
            Toast.makeText(CancelHoldDisplay.this, "Hold Cancelled.", Toast.LENGTH_SHORT).show();
            popUp();
        }
    }


    private boolean custLogin(String user, String pass){
        OLS allData = (OLS) this.getApplicationContext();
        for( int i= 0; i< allData.cust.size(); i++) {
            if (user.equals(allData.cust.get(i).getUserName()) &&
                    pass.equals(allData.cust.get(i).getPassword())) {
                Toast.makeText(CancelHoldDisplay.this, "Login successful.", Toast.LENGTH_SHORT).show();
                System.out.println("Login successful.");
                return true;
            }
        }
        return false;
    }


    private void popUp() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Return to main menu.")
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(CancelHoldDisplay.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
        AlertDialog helpDialog = dialog.create();
        helpDialog.show();
    }


    private void popUp(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(message)
                .setMessage("Returning to main menu.")
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(CancelHoldDisplay.this, MainActivity.class);
                                startActivity(i);
                            }
                        });
        AlertDialog helpDialog = dialog.create();
        helpDialog.show();
    }


   public void listRes(){
       boolean flag = false;


       OLS allData = (OLS) this.getApplicationContext();
       StringBuilder stBuild = new StringBuilder("Display Reservations: \n");
       System.out.println("Display Reservations: ");
       //System.out.println("Res Num \t Pickup \t\t Return \t\t Title");
       for( TransHold superLoop: allData.tranHold ){
           if( stUser.equals(superLoop.getUsername()) ){
               stBuild.append(superLoop.getResNum()).append("\t").append(superLoop.getPickupTime()).append("\t").append(superLoop.getReturnTime()).append("\t").append(superLoop.getTitle()).append("\n");
               //System.out.println( superLoop.getResNum() +"\t" + superLoop.getPickupTime() + "\t"
               //+ superLoop.getReturnTime() + "\t" + superLoop.getTitle() );
               flag = true;
           }
       }
       if(flag) {
           String stLog = stBuild.toString();
           display.setText(stLog);
       }
       if( !flag ){
           Toast.makeText(CancelHoldDisplay.this, "There are no reservation with the username.", Toast.LENGTH_SHORT).show();
           System.out.println("There are no reservation with the username.");
           popUp();
       }
   }


    private void cancelHold1(Integer cancelNum) {
        OLS allData = (OLS) this.getApplicationContext();

        for (int i = 0; i < allData.tranHold.size(); i++) {
            if (cancelNum == allData.tranHold.get(i).getResNum()) {
                indexCancelHold = i;
                resNum = allData.tranHold.get(i).getResNum();
                pickupTime = allData.tranHold.get(i).getPickupTime();
                returnTime = allData.tranHold.get(i).getReturnTime();
                title = allData.tranHold.get(i).getTitle();
                output.setText(resNum + "  " + pickupTime + "  " + returnTime + "  " + title);
                //System.out.println( resNum +"\t" + pickupTime + "\t\t"
                // + returnTime + "\t\t" + title );
            }
        }
    }


    private boolean cancelHold2(Integer cancelNum){
        OLS allData = (OLS) this.getApplicationContext();

        for(Book superLoop: allData.bookList){
            if( title.equals(superLoop.getTitle() )){
                //add check to see if the book is on hold at another time
                superLoop.setNoHold(true);
            }
        }
        allData.tranCancel.add(new TransCancel(  stUser, title, pickupTime, returnTime, resNum ) );
        allData.tranHold.remove(indexCancelHold);

        for(TransHold holdLoop: allData.tranHold){
            if( title.equals(holdLoop.getTitle() )){
                for(Book superLoop: allData.bookList){
                    if( title.equals(superLoop.getTitle() )){
                        //add check to see if the book is on hold at another time
                        superLoop.setNoHold(false);
                    }
                }
            }
        }
        return true;
    }

}//EOF