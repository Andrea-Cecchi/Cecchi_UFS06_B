package org.example;

public class Hotel {
    private int id;
    private String name;
    private String description;
    private double price;
    private boolean suite;

    public Hotel(int id, String name, String description, double price, boolean suite) {
        setId(id);
        this.name = name;
        this.description = description;
        setPrice(price);
        this.suite = suite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id > 0 ? id : 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price > 0.0 ? price : 0.0;
    }

    public boolean isSuite() {
        return suite;
    }

    public void setSuite(boolean suite) {
        this.suite = suite;
    }
}
