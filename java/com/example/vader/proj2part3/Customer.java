package com.example.vader.proj2part3;

/**
 * Title: CST 338 Project 2 Part 3
 * Due December 9, 2016 at 11:55pm
 * Phillip T. Emmons
 *
 * NOTE: There is no SQLite. The OLS class contains all the datatypes for the program that is subclassed through the Application Layer.
 */


public class Customer {


    private String userName = "";
    private String password = "";
    private double balance =0;
    public Customer(){
    }

    public Customer( String user, String pass ){
        setUserName(user);
        setPassword(pass);
        balance = 0;
    }

    public Customer( Customer aCopy ){
        setUserName( aCopy.getUserName() );
        setPassword( aCopy.getPassword() );
        setBalance( aCopy.getBalance() );
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
