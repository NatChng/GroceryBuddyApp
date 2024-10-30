package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an Ingredient with a name, cost, and note that can be added to a dish
public class Ingredient implements Writable {
    private String name;
    private int cost;
    private String notes;

    public Ingredient(String name, int cost) {
        this.name = name;
        this.cost = cost;
        notes = "";
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getNotes() {
        return notes;
    }

    // MODIFIES: this
    // EFFECTS: adds String s as notes
    public void addNote(String s) {
        notes = s;
    }

    // EFFECTS: writes ingredient to JSon as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cost", cost);
        json.put("notes", notes);
        return json;
    }
}
