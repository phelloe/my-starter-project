package com.example.test;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.util.List;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {

    public MainView() {
        final List<Inventory> inventoryList = PMF.INSTANCE.read();
        final Grid<Inventory> inventoryGrid = new Grid<>(Inventory.class);
        final ButtonFactory buttonFactory = new ButtonFactory(inventoryGrid, inventoryList);
        setSizeFull();
        inventoryGrid.setItems(inventoryList);
        inventoryGrid.addComponentColumn(a -> buttonFactory.createButton(a, "Edit"));
        add(buttonFactory.createButton(null, "New"), inventoryGrid);
    }
}
