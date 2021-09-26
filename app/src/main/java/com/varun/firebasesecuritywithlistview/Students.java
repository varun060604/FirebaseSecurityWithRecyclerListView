package com.varun.firebasesecuritywithlistview;

public class Students {

    String name;
    String contact;
    String city;

    public Students(String name, String contact, String city) {
        this.name = name;
        this.contact = contact;
        this.city = city;
    }


    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getCity() {
        return city;
    }

}
