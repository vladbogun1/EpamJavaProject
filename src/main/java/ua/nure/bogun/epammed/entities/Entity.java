package main.java.ua.nure.bogun.epammed.entities;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 4054026766183935891L;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity [id=" + id + "]";
    }


}
