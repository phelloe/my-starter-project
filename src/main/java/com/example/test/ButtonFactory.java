package com.example.test;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;

import java.util.List;

class ButtonFactory {
    private final Grid<Inventory> inventoryGrid;
    private final List<Inventory> inventoryList;

    ButtonFactory(final Grid<Inventory> inventoryGrid, final List<Inventory> inventoryList) {
        this.inventoryGrid = inventoryGrid;
        this.inventoryList = inventoryList;
    }

    Button createButton(final Inventory inventory, final String text) {
        return new Button(text, event -> {
            final InventoryDialog dialog = new InventoryDialog(inventory);
            dialog.add(commitButton(inventory, dialog));
            if (inventory != null) {
                dialog.add(deleteButton(inventory, dialog));
            }
            dialog.open();
        });
    }

    private Button commitButton(final Inventory inventory, final InventoryDialog dialog) {
        return new Button("Commit", event -> {
            if (inventory == null) {
                inventoryList.add(PMF.INSTANCE.create(dialog));
            } else {
                inventoryList.set(inventoryList.indexOf(inventory), PMF.INSTANCE.update(inventory, dialog));
            }
            inventoryGrid.getDataProvider().refreshAll();
            dialog.close();
        });
    }

    private Button deleteButton(final Inventory inventory, final InventoryDialog dialog) {
        return new Button("Delete", event -> {
            PMF.INSTANCE.delete(inventory.getId());
            inventoryList.remove(inventory);
            inventoryGrid.getDataProvider().refreshAll();
            dialog.close();
        });
    }
}
