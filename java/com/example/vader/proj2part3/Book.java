package com.example.vader.proj2part3;

/**
 * Title: CST 338 Project 2 Part 3
 * Due December 9, 2016 at 11:55pm
 * Phillip T. Emmons
 *
 * NOTE: There is no SQLite. The OLS class contains all the datatypes for the program that is subclassed in the Application Layer.
 */

import java.util.Objects;

public class Book {

    private String title ="";
    private String author="";
    private String isbn="";
    private double fee=0;
    private boolean noHold = true;

    private int id;

    public Book(){
        setNoHold(true);
    }

    public Book( String title, String author, String isbn, double fee ){
        this.setTitle(title);
        this.setAuthor(author);
        this.setIsbn(isbn);
        this.setFee(fee);
        this.setNoHold(true);
    }

    public Book( Book aCopy ){
        this.setTitle( aCopy.getTitle() );
        this.setAuthor(aCopy.getAuthor() );
        this.setIsbn( aCopy.getIsbn() );
        this.setFee( aCopy.getFee() );
        this.setNoHold( aCopy.getNoHold());
    }


    public boolean getNoHold() {
        return noHold;
    }

    public void setNoHold(boolean noHold) {
        this.noHold = noHold;
    }



    public boolean equals( Book other ){
        if( other == null ){
            return false;
        }else if( other instanceof Book ){
            Book otherCopy = (Book) other;
            return ( this.title.equals( otherCopy.title )	&&
                    this.author.equals( otherCopy.author ) &&
                    this.isbn.equals( otherCopy.isbn ) 	&&
                    this.fee == otherCopy.fee );
        }else {
            return false;
        }
    }

    public int hashCode(){
        return Objects.hash( title, author, isbn, fee );
    }

    public boolean missInfo(){
        return ( this.title.equals( "" ) ||
                this.author.equals( "" ) ||
                this.isbn.equals( "" ) ||
                this.fee == 0 );
    }

    public void bookDisplay(){
        System.out.println( this.title +"  " + this.author +"  " + this.isbn +"  "+ this.fee );
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "Title: " + title + " "+
                "Author: " + author + " "+
                "ISBN: " + isbn + " "+
                "Fee: "+ fee + "\n";

    }
}
