package com.example.test;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import java.util.ArrayList;
import java.util.List;

public enum PMF {
    INSTANCE;

    private final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Tutorial");

    public Inventory create(final Inventory inventory) {
        try (PersistenceManager pm = pmf.getPersistenceManager()) {
            return pm.makePersistent(inventory);
        }
    }

    public List<Inventory> read() {
        List<Inventory> inventoryList = new ArrayList<>();
        try (PersistenceManager pm = pmf.getPersistenceManager()) {
            pm.getExtent(Inventory.class).forEach(inventoryList::add);
            return inventoryList;
        }
    }

    public void update(final Inventory inventory) {
        try (PersistenceManager pm = pmf.getPersistenceManager()) {
            pm.getObjectById(Inventory.class, inventory.getId()).update(inventory);
        }
    }

    public void delete(final long id) {
        try (PersistenceManager pm = pmf.getPersistenceManager()) {
            pm.deletePersistent(pm.getObjectById(Inventory.class, id));
        }
    }
}
