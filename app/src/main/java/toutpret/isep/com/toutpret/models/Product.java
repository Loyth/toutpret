package toutpret.isep.com.toutpret.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Product {
    public String name;
    public Long stock;
    public String categoryId;
    public int thumbnail;
    public double price;

    public int quantity;
    public String id;

    public Product() {
    }

    public Product(String name, Long stock, String categoryId, int thumbnail, double price) {
        this.name = name;
        this.stock = stock;
        this.categoryId = categoryId;
        this.thumbnail = thumbnail;
        this.price = price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
