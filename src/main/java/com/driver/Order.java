package com.driver;

public class Order {

    private String id;
    private int deliveryTime;
    private String timeInString;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        String arr[]=deliveryTime.split(":");
        int HH=Integer.parseInt(arr[0]);
        int MM=Integer.parseInt(arr[1]);
        this.deliveryTime=HH*60+MM;
        this.timeInString=deliveryTime;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public void setId(String id) {
        this.id = id;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getTimeInString() {
        return timeInString;
    }

    public void setTimeInString(String timeInString) {
        this.timeInString = timeInString;
    }
}
