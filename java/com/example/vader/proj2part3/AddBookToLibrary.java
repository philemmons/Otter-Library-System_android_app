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

public class AddBookToLibrary extends Activity implements View.OnClickListener {

    private EditText etTitle, etAuthor, etISBN, etFee;

    private String message;
    protected Button yes, no, upload;
    private TextView display;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book_display);

        yes = (Button) findViewById(R.id.b1);
        yes.setOnClickListener(this);
        no = (Button) findViewById(R.id.b2);
        no.setOnClickListener(this);
        upload = (Button) findViewById(R.id.b3);
        upload.setOnClickListener(this);

        yes.setEnabled(false);
        no.setEnabled(false);

        display = (TextView) findViewById(R.id.tvDisplay);

        etTitle = (EditText) findViewById(R.id.et1);
        etAuthor = (EditText) findViewById(R.id.et2);
        etISBN = (EditText)findViewById(R.id.et3);
        etFee = (EditText)findViewById(R.id.et4);

    }


    @Override
    public void onClick(View v) {
        //OLS allData = (OLS) this.getApplicationContext();

        String stTitle = etTitle.getText().toString();
        String stAuthor = etAuthor.getText().toString();
        String stISBN = etISBN.getText().toString();
        double dFee = Double.parseDouble(etFee.getText().toString());

        if (v.getId() == R.id.b3) {//Upload button
            yes.setEnabled(true);
            no.setEnabled(true);
            StringBuilder stBuild = new StringBuilder("Title: ").append(stTitle).append("\n")
                    .append("Author: ").append(stAuthor).append("\n")
                    .append("ISBN: ").append(stISBN).append("\n")
                    .append("Fee: ").append(String.format("$ %.2f", dFee)).append("\n");
            String stLog = stBuild.toString();
            display.setText(stLog);
            upload.setEnabled(false);

        }else if (v.getId() == R.id.b1) { //YES button
            Book temp = new Book(stTitle, stAuthor, stISBN, dFee);
            addBook(temp);
            popUp(message);
       }else if(v.getId() == R.id.b2) { //NO button
            popUp("Book information incorrect.");
        }
    }

    public boolean addBook(Book temp) {
        OLS allData = (OLS) this.getApplicationContext();

        Toast.makeText(AddBookToLibrary.this,"Checking ", Toast.LENGTH_SHORT).show();
        System.out.println("Checking to see if book already exist");
        for(Book novel: allData.bookList){
            if( temp.equals( novel ) ){
                System.out.println("Book is a duplicate.");
                message ="Book is a duplicate.";
                return false;
            }
        }
        if( temp.missInfo() ){
            System.out.println("Missing required information.");
            message ="Missing required information.";
            return false;
        }

        System.out.println("Book is added.");
        message = "Book is added.";
        allData.bookList.add(temp);
        return true;
    }

private void popUp(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(message)
                .setMessage("Returning to main menu.")
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(AddBookToLibrary.this, MainActivity.class);
                                startActivity(i);

                            }
                        });
        AlertDialog helpDialog = dialog.create();
        helpDialog.show();

    }

}//EOF