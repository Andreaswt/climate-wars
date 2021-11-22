package sdu.software.climatewars.Domain;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {}

    // Overloaded constructor to added initial item
    public Inventory(ArrayList<Item> items) {
        this.items = items;
    }

    public void add(Item item) {
        items.add(item);
    }

    public void remove(Item item) {
        items.remove(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        // Create empty string
        StringBuilder string = new StringBuilder();

        // Loop through all items and add their names to the string
        if (this.items != null) {
            for (Item item : this.items){
                string.append(item.getName() + " ");
            }
        }
        else {
            string.append("No inventory.");
        }

        return string.toString();
    }
}
