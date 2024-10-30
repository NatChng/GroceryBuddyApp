package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a Dish with a name, ingredients, and servings that can be added to a GroceryList
public class Dish implements Writable {
    private String name;
    private int servings;
    private int cost;
    private List<Ingredient> ingredients;

    public Dish(String name, int servings) {
        this.name = name;
        this.servings = servings;
        ingredients = new ArrayList<>();
        cost = 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public int getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    // MODIFIES: this
    // EFFECTS: adds Ingredient i to ingredients
    public void addIngredient(Ingredient i) {
        ingredients.add(i);
    }

    // REQUIRES: ingredients is not empty
    // MODIFIES: this
    // EFFECTS: removes Ingredient i to ingredients
    public void removeIngredient(Ingredient i) {
        ingredients.remove(i);
    }

    public void clearIngredients() {
        ingredients.clear();
    }

    // EFFECTS: returns the number of ingredients in a dish
    public int getNumIngredients() {
        return ingredients.size();
    }

    // EFFECTS: returns ingredients dish in format {ingredient}, {note}
    public String getIngredientsString() {
        String s = "";
        for (Ingredient i : ingredients) {
            s += i.getName() + ", " + i.getNotes() + "\n";
        }
        return s;
    }

    // TODO: fix this as it's displaying () as default
    // EFFECTS: returns ingredients dish in format {ingredient}, {note}
    public String getIngredientsGUI() {
        String s = "";
        String ingredientNote = "";
        for (Ingredient i : ingredients) {
            if (i.getNotes() != "") {
                ingredientNote = "(" + i.getNotes() + "), ";
            } else {
                ingredientNote = ", ";
            }
            s += i.getName() + " " + ingredientNote + "\n";
        }
        return s;
    }

    // MODIFIES: this
    // EFFECTS: calculates the total cost of all ingredients
    public int calculateCost() {
        cost = 0;
        for (Ingredient i : ingredients) {
            cost += i.getCost();
        }
        return getCost();
    }


    // EFFECTS: writes Dish as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("servings", servings);
        json.put("ingredients", ingredientsToJson());
        return json;
    }

    // EFFECTS: returns ingredients in this dish as a JSON array
    private JSONArray ingredientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Ingredient i : ingredients) {
            jsonArray.put(i.toJson());
        }
        return jsonArray;
    }
}
