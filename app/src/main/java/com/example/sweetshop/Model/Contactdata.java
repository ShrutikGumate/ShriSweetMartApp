package com.example.sweetshop.Model;

public class Contactdata {
    private String email,website,youtube,instagram,twitter;
    private String phonenumber;
    public Contactdata(){

    }

    public Contactdata(String phonenumber, String email, String website, String youtube, String instagram, String twitter) {
        this.phonenumber = phonenumber;
        this.email = email;
        this.website = website;
        this.youtube = youtube;
        this.instagram = instagram;
        this.twitter = twitter;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
