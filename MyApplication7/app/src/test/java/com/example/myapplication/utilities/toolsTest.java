package com.example.myapplication.utilities;

import junit.framework.TestCase;

import org.junit.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.myapplication.utilities.tools.DiffrenceDate;

public class toolsTest extends TestCase {

    public void testDiffrenceDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        DateFormat date = new SimpleDateFormat("d MMM yyyy, HH:mm:ss.SSS");
        Date date1,date2;
        String datedate="";
        try {
             date1 = simpleDateFormat.parse("10/10/2013 20:35:10");
             date2 = simpleDateFormat.parse("10/10/2013 20:35:55");
             datedate = DiffrenceDate(date1,date2);
        }catch (Exception e){
            e.printStackTrace();
        }

        Assert.assertEquals(datedate,"Depuis l'instant");
    }
}