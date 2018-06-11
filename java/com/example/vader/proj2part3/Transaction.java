package com.example.vader.proj2part3;

/**
 * Title: CST 338 Project 2 Part 3
 * Due December 9, 2016 at 11:55pm
 * Phillip T. Emmons
 *
 * NOTE: There is no SQLite. The OLS class contains all the datatypes for the program that is subclassed through the Application Layer.
 */
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    private String transType = "";
    private String username = "";
    private String transTime = "";
    private Date timeStamp = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy',' hh:mm a");

    public Transaction() {
    }

    //new account
    public Transaction(String username) {
        this.username = username;
        transType = "new account";
        transTime = sdf.format(timeStamp);
    }

    public String getTransType() {
        return transType;
    }

    public String getUsername() {
        return username;
    }

    public String getTransTime() {
        return transTime;
    }
}