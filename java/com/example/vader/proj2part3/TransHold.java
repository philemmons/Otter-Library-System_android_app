package com.example.vader.proj2part3;

/**
 * Title: CST 338 Project 2 Part 3
 * Due December 9, 2016 at 11:55pm
 * Phillip T. Emmons
 *
 * NOTE: There is no SQLite. The OLS class contains all the datatypes for the program that is subclassed through the Application Layer.
 */

public class TransHold extends TransCancel{
    private String transType ="";
    //private int resNum =0;
    private String totalFee = "";


    public TransHold( String username, String title, String pickupTime, String returnTime, int resNum, String totalFee ){
        super(  username,  title,  pickupTime,  returnTime, resNum);
        transType = "place hold";
        this.setTotalFee(totalFee);
    }

    //public int getResNum() {
     //   return resNum;
    //}

   // public void setResNum(int resNum) {
     //   this.resNum = resNum;
   // }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
}
