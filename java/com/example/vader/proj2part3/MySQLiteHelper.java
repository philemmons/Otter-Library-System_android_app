package com.example.vader.proj2part3;

/**
 * Title: CST 338 Project 2 Part 3
 * Due December 9, 2016 at 11:55pm
 * Phillip T. Emmons
 *
 * NOTE: There is no SQLite. The OLS class contains all the datatypes for the program that is subclassed through the Application Layer.
 *
 * package edu.csumb.ybyun.sqliteapp
 *
 */

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Name - BookDB
    private static final String DATABASE_NAME = "BookDB";

    // Table Name - books
    private static final String TABLE_BOOKS = "books";

    // Columns Names of books Table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_ISBN = "isbn";
    private static final String KEY_FEE = "fee";
    private static final String KEY_NOHOLD = "hold";



    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Log TAG for debugging purpose
    private static final String TAG = "SQLiteAppLog";

    // Constructor
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create a table called "books"
        String CREATE_BOOK_TABLE = "CREATE TABLE books ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "author TEXT, " +
                "isbn TEXT, " +
                "fee REAL, " +
                "hold TEXT )";

        // execute an SQL statement to create the table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    // onUpdate() is invoked when you upgrade the database scheme.
    // Don’t consider it seriously for the sample app.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    public void addBook(Book book){
        Log.d(TAG, "addBook() - " + book.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, book.getTitle()); // get title
        values.put(KEY_AUTHOR, book.getAuthor()); // get author
        values.put(KEY_ISBN, book.getIsbn() ); // get isbn
        values.put(KEY_FEE, book.getFee() ); //get fee

        //using the db for adding book only
        values.put(KEY_NOHOLD, book.getNoHold() ); // get boolean hold



        // 3. insert
        db.insert(TABLE_BOOKS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close - release the reference of writable DB
        db.close();
    }

    // Get all books from the database
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<Book>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_BOOKS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));
                book.setIsbn(cursor.getString(3));
                book.setFee(cursor.getDouble(4));

                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }

        Log.d(TAG, "getAllBooks() - " + books.toString());

        // return books
        return books;
    }

    // Updating single book
    public int updateBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle()); // get title
        values.put("author", book.getAuthor()); // get author
        values.put("isbn", book.getIsbn() ); // get isbn
        values.put("fee", book.getFee() ); //get fee
        values.put("nohold", book.getNoHold() );

        // 3. updating row
        int i = db.update(TABLE_BOOKS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(book.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

//    // Deleting single book
//    public void deleteBook(Book book) {
//
//        // 1. get reference to writable DB
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // 2. delete
//        db.delete(TABLE_BOOKS,
//                KEY_ID+" = ?",
//                new String[] { String.valueOf(book.getId()) });
//
//        // 3. close
//        db.close();
//
//        Log.d(TAG, "deleteBook() - " + book.toString());
//    }
}