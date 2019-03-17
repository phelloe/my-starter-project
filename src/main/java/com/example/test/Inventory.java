package com.example.test;

import javax.jdo.JDOHelper;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.lang.reflect.Field;

@PersistenceCapable
public class Inventory {
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    @PrimaryKey
    private long id;
    @Persistent
    private String name;
    @Persistent
    private String type;

    void update(Inventory inventory) {
        for (Field f : Inventory.class.getDeclaredFields()) {
            if (f.isAnnotationPresent(Persistent.class) && !f.isAnnotationPresent(PrimaryKey.class)) {
                try {
                    f.set(this, f.get(inventory));
                    JDOHelper.makeDirty(this, f.getName());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public String getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public void setType(final String type) {
        this.type = type;
    }
}
