package toutpret.isep.com.toutpret.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Category {
    public String name;
    public int thumbnail;
    public ArrayList products;


    public Category() {
    }

    public Category(String name, ArrayList products, int thumbnail) {
        this.name = name;
        this.products = products;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getProducts() {
        return products;
    }

    public void setProducts(ArrayList products) {
        this.products = products;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
