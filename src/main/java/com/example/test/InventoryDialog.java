package com.example.test;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

class InventoryDialog {
    private final TextField name = new TextField("Name");
    private final TextField type = new TextField("Type");
    private final TextField serialNumber = new TextField("Serial Number");
    private final FormLayout formLayout = new FormLayout();
    private final Dialog dialog = new Dialog();
    private final Binder<Inventory> binder = new Binder<>(Inventory.class);

    InventoryDialog(Inventory inventory) {
        formLayout.add(name, type, serialNumber);
        dialog.add(formLayout);
        binder.bindInstanceFields(this);
        binder.readBean(inventory);
        formLayout.add(new Button("Cancel", buttonClickEvent -> dialog.close()));
    }

    void add(Component... components) {
        formLayout.add(components);
    }

    void open() {
        dialog.open();
    }

    void close() {
        dialog.close();
    }

    Inventory writeBean(Inventory inventory) {
        try {
            binder.writeBean(inventory);
            return inventory;
        } catch (ValidationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
