package com.co.unibox;

public class Shopper_Activity_Favorite_Product_Domain {

    private String nameProduct;
    private String categoriaProduct;
    private String pic;
    private String price;
    private String picLike;

    public Shopper_Activity_Favorite_Product_Domain(String nameProduct, String categoriaProduct, String pic, String price, String picLike) {
        this.nameProduct = nameProduct;
        this.categoriaProduct = categoriaProduct;
        this.pic = pic;
        this.price = price;
        this.picLike = picLike;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getCategoriaProduct() {
        return categoriaProduct;
    }

    public void setCategoriaProduct(String categoriaProduct) {
        this.categoriaProduct = categoriaProduct;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicLike() {
        return picLike;
    }

    public void setPicLike(String picLike) {
        this.picLike = picLike;
    }
}
