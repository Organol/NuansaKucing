package com.dartmedia.nuansakucing.Model;

import org.osmdroid.util.GeoPoint;

public class Model {
    private int image;
    private String title;
    private String desc;
    private String jar;
    private GeoPoint GP;
    private String photo;
    private double longi, lati,jarak;

    public GeoPoint getGP() {
        return GP;
    }

    public void setGP(GeoPoint GP) {
        this.GP = GP;
    }

    public String getPhoto() {   return photo;  }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public String getJar() {
        return jar;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getJarak() {
        return jarak;
    }

    public void setJarak(double jarak) {
        this.jarak = jarak;
    }

    public Model(int image, String title, String desc, double longi, double lati, double jarak) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.longi = longi;
        this.lati = lati;
        this.jarak = jarak;
    }

    public Model(int image, String title, String desc, double jarak) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.jarak = jarak;
    }
    public Model(String photo, String title, String desc, String jar, GeoPoint gp) {
        this.photo = photo;
        this.title = title;
        this.desc = desc;
        this.jar = jar;
        this.GP = gp;

    } public Model(String title, String desc, String jar, GeoPoint gp) {
        this.photo = photo;
        this.title = title;
        this.desc = desc;
        this.jar = jar;
        this.GP = gp;

    }
}
