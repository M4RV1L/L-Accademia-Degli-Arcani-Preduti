/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progettoaccademiaarcani.model;

import java.io.Serializable;

/**
 *
 * @author marcovillano
 */
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private boolean canBePickedUp; // Se l'oggetto può essere aggiunto all'inventario
    private boolean isConsumable; // Se l'oggetto si consuma dopo l'uso

    public Item(String name, String description, boolean canBePickedUp) {
        this.name = name;
        this.description = description;
        this.canBePickedUp = canBePickedUp;
        this.isConsumable = false; // Di default non è consumabile
    }

    public Item(String name, String description, boolean canBePickedUp, boolean isConsumable) {
        this(name, description, canBePickedUp);
        this.isConsumable = isConsumable;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean canBePickedUp() {
        return canBePickedUp;
    }

    public boolean isConsumable() {
        return isConsumable;
    }

    // Possibili azioni sull'oggetto (es. "usa")
    // Questo metodo ora è più un placeholder. La logica di "uso" è principalmente nel GameController.
    public String use() {
        return "Non sai come usare " + name + ".";
    }
}