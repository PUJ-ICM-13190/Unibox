package com.co.unibox;

public class BoxesDomain {

    private String nameBox;
    private String location;
    private String like;

    public BoxesDomain(String nameBox, String location, String like) {
        this.nameBox = nameBox;
        this.location = location;
        this.like = like;
    }

    public String getNameBox() {
        return nameBox;
    }

    public void setNameBox(String nameBox) {
        this.nameBox = nameBox;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
