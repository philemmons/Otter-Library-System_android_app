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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Title: CST 338 Project 2 Part 3
 * Due December 9, 2016 at 11:55pm
 * Phillip T. Emmons
 *
 * NOTE: There is no SQLite. The OLS class contains all the datatypes for the program that is subclassed through the Application Layer.
 */

public class PlaceHoldDisplay extends Activity implements View.OnClickListener {

    private String message, stUser, stPass, custPick;

    protected Button bTimeDate, bTitle, bLogin, yes, no;
    private EditText etUser, etPass, etAmpmPU, etTimePU, etDatePU, etTitle, etAmpmRE, etTimeRE, etDateRE;
    private TextView display, attempts, selection, hide;

    private int counter = 2;


    Date pUpTime, reTime, sevenDays;
    Calendar sevenDayCal = Calendar.getInstance();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pl_hold_display);

        bTimeDate = (Button) findViewById(R.id.b1);
        bTimeDate.setOnClickListener(this);

        bTitle = (Button) findViewById(R.id.b2);
        bTitle.setOnClickListener(this);
        bTitle.setEnabled(false);

        bLogin = (Button) findViewById(R.id.b3);
        bLogin.setOnClickListener(this);
        bLogin.setEnabled(false);

//        yes = (Button) findViewById(R.id.b4);
//        yes.setOnClickListener(this);
//        yes.setEnabled(false);
//        yes.setVisibility(View.INVISIBLE);
//
//        no = (Button) findViewById(R.id.b5);
//        no.setOnClickListener(this);
//        no.setEnabled(false);
//        no.setVisibility(View.INVISIBLE);

        display = (TextView) findViewById(R.id.tv1);
        selection = (TextView) findViewById(R.id.tv2);
        attempts = (TextView) findViewById(R.id.tv3);

        etTimePU = (EditText) findViewById(R.id.et1);
        etAmpmPU = (EditText) findViewById(R.id.et2);
        etDatePU = (EditText) findViewById(R.id.et3);


        etTitle = (EditText) findViewById(R.id.et4);
        etUser = (EditText) findViewById(R.id.et5);
        etPass = (EditText) findViewById(R.id.et6);

        etTimeRE = (EditText) findViewById(R.id.et7);
        etAmpmRE = (EditText) findViewById(R.id.et8);
        etDateRE = (EditText) findViewById(R.id.et9);
    }

    @Override
    public void onClick(View v) {
        OLS allData = (OLS) this.getApplicationContext();

        if (v.getId() == R.id.b1) {//bTimeDate button

            if (placeHold()) {
                Toast.makeText(PlaceHoldDisplay.this, "Enter Title.", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.b2) { //bTitle button
            custPick = etTitle.getText().toString();
            bLogin.setEnabled(true);
            Toast.makeText(PlaceHoldDisplay.this, "Login Required.", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.b3) { //bLogin button
            stUser = etUser.getText().toString();
            stPass = etPass.getText().toString();

            if (custLogin(stUser, stPass)) {
                Toast.makeText(PlaceHoldDisplay.this, "Login Success.", Toast.LENGTH_SHORT).show();
                if ( selectBook(pUpTime, reTime)) {
                    popUp();

                }
            } else {
                counter--;
                attempts.setText(Integer.toString(counter));
                Toast.makeText(PlaceHoldDisplay.this, "Invalid Login.", Toast.LENGTH_SHORT).show();
                if (counter == 0) {
                    popUp();
                }
            }
        }
    }


    private boolean custLogin(String user, String pass) {
        OLS allData = (OLS) this.getApplicationContext();
        for (int i = 0; i < allData.cust.size(); i++) {
            if (user.equals(allData.cust.get(i).getUserName()) &&
                    pass.equals(allData.cust.get(i).getPassword())) {
                Toast.makeText(PlaceHoldDisplay.this, "Login successful.", Toast.LENGTH_SHORT).show();
                System.out.println("Login successful.");
                return true;
            }
        }
        return false;
    }


    public boolean placeHold() {
        OLS allData = (OLS) this.getApplicationContext();


        pUpTime = holdDropDate(etDatePU, etTimePU, etAmpmPU);
        reTime = holdDropDate(etDateRE, etTimeRE, etAmpmRE);

        sevenDayCal.setTime(pUpTime);
        sevenDayCal.add(Calendar.DAY_OF_MONTH, 8);
        //MUST BE EXCLUSIVE HENCE 8
        sevenDays = sevenDayCal.getTime();

        if (!pUpTime.before(reTime)) {
            Toast.makeText(PlaceHoldDisplay.this, "No time travelling allowed.", Toast.LENGTH_SHORT).show();
            System.out.println("No time travelling allowed.");
            return false;
        } else if (!reTime.before(sevenDays)) {
            Toast.makeText(PlaceHoldDisplay.this, "Maximum rental is seven days.", Toast.LENGTH_SHORT).show();
            System.out.println("Maximum rental is seven days.");
            return false;
        } else if (!availBook(pUpTime, reTime)) {
            Toast.makeText(PlaceHoldDisplay.this, "There are no book available for the dates/hours entered.", Toast.LENGTH_SHORT).show();
            System.out.println("There are no book available for the dates/hours entered.");

            System.out.println("Returning to main menu.");
            popUp();
        }return true;

    }


    public boolean selectBook(Date pUpTime, Date reTime){
        OLS allData = (OLS) this.getApplicationContext();

        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy hh:mm a");
        Random rand = new Random();
        String bTitle = null;
        double hourFee;
        String pUp = null;
        String rTime = null;
        int resNum = 0;
        String blingOwed = "";

        for( Book bookLoop: allData.bookList){
            if( bookLoop.getTitle().equals(custPick) ){
                bTitle = bookLoop.getTitle();
                hourFee = bookLoop.getFee();
                pUp = sdf.format(pUpTime);
                rTime = sdf.format(reTime);
                resNum = rand.nextInt(999) + 1000;
                blingOwed = calcFee( pUpTime,reTime, hourFee );
            }
        }
        String stOut = "User: " + stUser + " " + "Pick Up: " + pUp + " " + "Return: " +
                rTime + " " + "Title: " + bTitle + " " + "Reservation Number: " + resNum + " " + "Total Fee: " + blingOwed;
        selection.setText(stOut);
        allData.tranHold.add( new TransHold( stUser, bTitle, pUp, rTime, resNum, blingOwed));
        for( Book bookLoop: allData.bookList){
            if( bookLoop.getTitle().equals(bTitle) ){
                bookLoop.setNoHold(false);
            }
        }
        Toast.makeText(PlaceHoldDisplay.this, "Hold completed.", Toast.LENGTH_SHORT).show();
        System.out.println("Hold completed.");
        return true;
    }


    public String calcFee(Date pUpTime, Date reTime, double feePerHour){

        double milSecToHour = 1000*60*60;
        double bling = feePerHour * (( reTime.getTime()- pUpTime.getTime() )/ milSecToHour);
        if( bling == 169.0){ bling = 168.0; }
        return String.format("$ %.2f",bling);
    }


    public boolean availBook(Date pUpTime, Date reTime){
        OLS allData = (OLS) this.getApplicationContext();
        Date bookBegin, bookEnd;
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy hh:mm a");
        int test =0;
        StringBuilder sb = new StringBuilder();

        if(allData.tranHold.size() == 0){
            for( Book bookLoop: allData.bookList){
                sb.append( bookLoop.toString() );
            }
            String stOut = sb.toString();
            display.setText(stOut);
            bTitle.setEnabled(true);
            return true;
        }else{
            for( Book bookLoop: allData.bookList){
                if( bookLoop.getNoHold() ){
                    sb.append( bookLoop.toString());
                    test++;
                    System.out.println(test);
                }
            }
            if(allData.tranHold.size() > 0){
                try{
                    System.out.println(pUpTime);
                    System.out.println(reTime);
                    for( int i= 0; i< allData.tranHold.size(); i++ ){
                        bookBegin = sdf.parse( allData.tranHold.get(i).getPickupTime() );
                        System.out.println(bookBegin);
                        bookEnd = sdf.parse( allData.tranHold.get(i).getReturnTime() );
                        System.out.println(bookEnd);
                        if( (pUpTime.before(bookBegin) && reTime.before(bookBegin)) ||
                                (pUpTime.after(bookEnd) && reTime.after(bookEnd)) ){
                            for( Book bookLoop: allData.bookList){
                                if( (bookLoop.getTitle().equals(allData.tranHold.get(i).getTitle() ) ) ){
                                    sb.append(bookLoop.toString());
                                    test++;
                                    System.out.println(test +" tran loop");

                                }
                            }
                        }
                    }
                    String stOut = sb.toString();
                    display.setText(stOut);
                    bTitle.setEnabled(true);
                    if( test>0 )
                        return true;
                }catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public Date holdDropDate(EditText date,EditText thyme,EditText ampm){
        String day = date.getText().toString();
        String time = thyme.getText().toString();
        String dayNight = ampm.getText().toString();
        dayNight = dayNight.toUpperCase();
        String dayTime = day + " " + time +" "+ dayNight;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy hh:mm a");
            Date pUpTime = sdf.parse(dayTime);
            String pickUp = sdf.format(pUpTime);

            System.out.println(pUpTime);
            Toast.makeText(PlaceHoldDisplay.this, pickUp, Toast.LENGTH_SHORT).show();
            System.out.println(pickUp);

            return pUpTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(PlaceHoldDisplay.this, "Internal Error.", Toast.LENGTH_SHORT).show();
        System.out.println("Internal Error.");
        return null;
    }


    private void popUp() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Return to main menu.")
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(PlaceHoldDisplay.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });
        AlertDialog helpDialog = dialog.create();
        helpDialog.show();
    }

}//EOF
