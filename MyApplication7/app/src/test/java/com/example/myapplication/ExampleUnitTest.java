package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String[] communes = new String[]{"akram","bensalem"};
        Wilaya medea = new Wilaya("26","MEDEA",communes);
        assertEquals(medea.getWilayaName(),"Medea");
      //  assertEquals(4, 2 + 2);
    }
}