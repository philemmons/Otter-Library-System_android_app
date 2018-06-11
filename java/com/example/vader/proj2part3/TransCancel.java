package com.example.vader.proj2part3;

/**
 * Title: CST 338 Project 2 Part 3
 * Due December 9, 2016 at 11:55pm
 * Phillip T. Emmons
 *
 * NOTE: There is no SQLite. The OLS class contains all the datatypes for the program that is subclassed through the Application Layer.
 */

public class TransCancel extends Transaction{
    private String transType ="";
    private String title ="";
    private String pickupTime = "";
    private String returnTime = "";
    private int resNum =0;


    public TransCancel( String username, String title, String pickupTime, String returnTime, int resNum ){
        super( username ) ;
        transType = "cancellation";
        this.setTitle(title);
        this.setPickupTime(pickupTime);
        this.setReturnTime(returnTime);
        this.setResNum(resNum);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public void setResNum(int resNum) { this.resNum = resNum; }

    public int getResNum(){
        return resNum;
    }
}
