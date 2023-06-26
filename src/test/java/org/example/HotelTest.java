package org.example;

import junit.framework.TestCase;

public class HotelTest extends TestCase {

    public void testSetId() {
        Hotel h1 = new Hotel(-8, "hotel mirafiori", "hotel sul mare di rimini", 1300.0,true);
        assertTrue(h1.getId() == 0);
    }

    public void testSetPrice() {
        Hotel h1 = new Hotel(12, "hotel mirafiori", "hotel sul mare di rimini", -1300.0,true);
        assertTrue(h1.getPrice() == 0.0);
    }
}