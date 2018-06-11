package com.example.vader.proj2part3;

import android.app.Application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * Title: CST 338 Project 2 Part 3
 * Due December 9, 2016 at 11:55pm
 * Phillip T. Emmons
 *
 * NOTE: There is no SQLite. The OLS class contains all the datatypes for the program that is subclassed through the Application Layer.
 */

public class OLS extends Application{

    public  ArrayList< Customer > cust = new ArrayList< Customer >();
    public  ArrayList< Book > bookList = new ArrayList< Book >();
    public  ArrayList< Transaction > tranNewAcc = new ArrayList< Transaction >();
    public  ArrayList< TransHold > tranHold = new ArrayList< TransHold >();
    public  ArrayList< TransCancel > tranCancel = new ArrayList< TransCancel >();
    public  boolean exit = false;

    public OLS() {

        cust.add(new Customer("a@lice5", "@csit100"));
        tranNewAcc.add(new Transaction("a@lice5"));

        cust.add(new Customer("$brian7", "123abc##"));
        tranNewAcc.add(new Transaction("$brian7"));

        cust.add(new Customer("!chris12!", "CHRIS12!!"));
        tranNewAcc.add(new Transaction("!chris12!"));


        bookList.add(new Book("Hot Java", "S. Narayanan", "123-ABC-101", 0.05));
        bookList.add(new Book("Fun Java", "Y. Byun", "ABCDEF-09", 1.0));
        bookList.add(new Book("Algorithm for Java", "K. Alice", "CDE-777-123", 0.25));
    }

}
