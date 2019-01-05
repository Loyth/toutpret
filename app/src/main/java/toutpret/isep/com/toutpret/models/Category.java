package toutpret.isep.com.toutpret.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Category {
    public String name;
    public int thumbnail;


    public Category() {
    }

    public Category(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
