package com.co.unibox;

public class EmprendimientosDomain {
    private String tittle;
    private String location;
    private String picPath;
    private String logo;

    public EmprendimientosDomain(String location, String tittle, String picPath, String logo) {
        this.location = location;
        this.tittle = tittle;
        this.picPath = picPath;
        this.logo = logo;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}