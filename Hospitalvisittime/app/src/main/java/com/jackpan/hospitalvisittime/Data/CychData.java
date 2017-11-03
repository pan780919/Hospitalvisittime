package com.jackpan.hospitalvisittime.Data;

import android.util.Log;

/**
 * 嘉義基督
 */

public class CychData {
    public String state = "";
    public String division = "";
    public String number = "";
    public String lastNumber ="";
    public String watingNum = "";
    public String registrationState = "";
    public String docName = "";
    public String address = "";

    public  CychData(String state ,String division ,String number ,String lastNumber
                     ,String watingNum , String registrationState,String docName ,String address){
        this.state = state;
        this.division = division;
        this.number = number;
        this.lastNumber = lastNumber;
        this.watingNum = watingNum;
        this.registrationState = registrationState;
        this.docName = docName ;
        this.address = address;

    }

}
