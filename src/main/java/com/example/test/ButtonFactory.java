package com.example.test;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.List;

class ButtonFactory {
    private final TextField name = new TextField("Name");
    private final TextField type = new TextField("Type");
    private final Binder<Inventory> binder = new Binder<>(Inventory.class);
    private final Grid<Inventory> inventoryGrid;
    private final List<Inventory> inventoryList;

    ButtonFactory(final Grid<Inventory> inventoryGrid, final List<Inventory> inventoryList) {
        this.inventoryGrid = inventoryGrid;
        this.inventoryList = inventoryList;
        binder.bindInstanceFields(this);
    }

    Button createButton(final Inventory inventory, final String text) {
        return new Button(text, event -> {
            final Dialog dialog = new Dialog();
            final FormLayout formLayout = new FormLayout();
            binder.readBean(inventory);
            formLayout.add(name, type);
            if (inventory == null) {
                formLayout.add(
                        commitButton(binder, new Inventory(), dialog));
            } else {
                formLayout.add(
                        commitButton(binder, inventory, dialog),
                        deleteButton(inventory, dialog));
            }
            formLayout.add(cancelButton(dialog));
            dialog.add(formLayout);
            dialog.open();
        });
    }

    private Button cancelButton(final Dialog dialog) {
        return new Button("Cancel", event -> dialog.close());
    }

    private Button commitButton(final Binder<Inventory> binder, final Inventory inventory, final Dialog dialog) {
        return new Button("Commit", event -> {
            try {
                binder.writeBean(inventory);
                if (inventory.getId() == 0) {
                    inventoryList.add(PMF.INSTANCE.create(inventory));
                } else {
                    PMF.INSTANCE.update(inventory);
                }
                inventoryGrid.getDataProvider().refreshAll();
                dialog.close();

            } catch (ValidationException e) {
                e.printStackTrace();
            }
        });
    }

    private Button deleteButton(final Inventory inventory, final Dialog dialog) {
        return new Button("Delete", event -> {
            PMF.INSTANCE.delete(inventory.getId());
            inventoryList.remove(inventory);
            inventoryGrid.getDataProvider().refreshAll();
            dialog.close();
        });
    }
}
