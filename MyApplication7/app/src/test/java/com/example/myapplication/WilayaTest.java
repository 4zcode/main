package com.example.myapplication;

import junit.framework.TestCase;

public class WilayaTest extends TestCase {

    public void testTestGetName() {
        String[] communes = new String[]{"akram","bensalem"};
        Wilaya medea = new Wilaya(26,"medea batna",communes);
        assertEquals(medea.getWilayaName(),"Medea Batna");
    }
    public void testGetCommunes(){
        String[] communes = new String[]{"akram","bensalem"};
        Wilaya medea = new Wilaya(26,"MEDEA BATNA",communes);
        String[] Newcommunes = new String[]{"Akram","Bensalem"};
        String name0 = medea.getCommunes()[0];
        String name1 = medea.getCommunes()[1];
        assertEquals(medea.getCommunes()[0],Newcommunes[0]);
        assertEquals(medea.getCommunes()[1],Newcommunes[1]);

    }
/*
@RunWith(AndroidJUnit4.class)
@SmallTest
public class ConnectivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testIsConnected() throws Exception {
        Context context = mActivityRule.getActivity().getBaseContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = cm.getActiveNetworkInfo().isConnectedOrConnecting();
        Assert.assertEquals(connected, ConnectionUtils.isConnected(context));
    }
}
 */
}