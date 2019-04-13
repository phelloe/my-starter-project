package com.example.test;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import java.util.ArrayList;
import java.util.List;

public enum PMF {
    INSTANCE;

    private final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Tutorial");

    public Inventory create(final InventoryDialog dialog) {
        try (PersistenceManager pm = pmf.getPersistenceManager()) {
            return pm.makePersistent(dialog.writeBean(new Inventory()));
        }
    }

    public List<Inventory> read() {
        List<Inventory> inventoryList = new ArrayList<>();
        try (PersistenceManager pm = pmf.getPersistenceManager()) {
            pm.getExtent(Inventory.class).forEach(inventoryList::add);
            return inventoryList;
        }
    }

    public Inventory update(final Inventory inventory, final InventoryDialog dialog) {
        try (PersistenceManager pm = pmf.getPersistenceManager()) {
            return dialog.writeBean(pm.getObjectById(Inventory.class, inventory.getId()));
        }
    }

    public void delete(final long id) {
        try (PersistenceManager pm = pmf.getPersistenceManager()) {
            pm.deletePersistent(pm.getObjectById(Inventory.class, id));
        }
    }
}
