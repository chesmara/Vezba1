package com.example.sninkovic_ns.vezba1.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sninkovic_ns on 8.3.2017.
 */
@DatabaseTable(tableName = Glumac.TABLE_NAME_USERS)
public class Glumac {

    public static final String TABLE_NAME_USERS = "glumci";

    public static final String FIELD_NAME_ID  =  "id";
    public static final String FIELD_NAME_NAME = "name";
    public static final String FIELD_NAME_DESCRIBE = "describe";
    public static final String FIELD_NAME_RATING = "rating";
    public static final String FIELD_NAME_IMAGE = "image";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String name;

    @DatabaseField(columnName = FIELD_NAME_DESCRIBE)
    private String describe;

    @DatabaseField(columnName = FIELD_NAME_RATING)
    private String rating;

    @DatabaseField(columnName = FIELD_NAME_IMAGE)
    private String image;

    public Glumac(){
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Glumac{" +
                "name='" + name + '\'' +
                '}';
    }
}
