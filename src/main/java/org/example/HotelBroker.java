package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class HotelBroker {

    private List<Hotel> hotels = new ArrayList<>();

    private static HotelBroker INSTANCE;


    private HotelBroker() {
    }

    public static HotelBroker getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new HotelBroker();
        }

        return INSTANCE;
    }


    private HotelBroker(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    void addHotel(Hotel newHotel){
        hotels.add(newHotel);
    }

    void removeCar(Hotel newHotel){
        hotels.remove(newHotel);
    }

    public Hotel getMoreExpensive(){
        double max = 0.0;
        Hotel maxh = null;
        for (Hotel h:
                hotels) {
            if(h.getPrice() > max && h.isSuite()){
                max = h.getPrice();
                maxh = h;
            }
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(maxh);

        return maxh;
    }

    public String getMoreExpensiveTCP(){
        double max = 0.0;
        Hotel maxh = null;
        for (Hotel h:
                hotels) {
            if(h.getPrice() > max && h.isSuite()){
                max = h.getPrice();
                maxh = h;
            }
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(maxh);

        return s;
    }

    public List<Hotel> toJsonSorted(){
        List<Hotel> myObject = new ArrayList<>(hotels);
        myObject.sort((o1, o2) -> {
            return o1.getName().compareTo(o2.getName());
        });
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(myObject);
        return myObject;
    }

    public String toJsonSortedTCP(){
        List<Hotel> myObject = new ArrayList<>(hotels);
        myObject.sort((o1, o2) -> {
            return o1.getName().compareTo(o2.getName());
        });
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(myObject);
        return s;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String s = gson.toJson(hotels);

        return s;
    }

    public List<Hotel> getCars() {
        return hotels;
    }
}
