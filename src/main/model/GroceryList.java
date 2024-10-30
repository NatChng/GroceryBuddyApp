package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a GroceryList with dishes
public class GroceryList implements Writable {

    private String name;
    private int cost;
    private ArrayList<Dish> dishes;
    private static int WEEKLY_SERVINGS = 20;

    public GroceryList(String n) {
        this.name = n;
        cost = 0;
        dishes = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Created GroceryList " + n + "."));
    }

    // getters
    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    // MODIFIES: this
    // EFFECTS: sets name of grocerylist to name
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns the size of dishes
    public int getNumDishes() {
        return dishes.size();
    }

    // MODIFIES: this
    // EFFECTS: adds a Dish to Dishes
    public void addDish(Dish d) {
        dishes.add(d);
        EventLog.getInstance().logEvent(new Event("Added dish " + d.getName() + "."));
    }

    // MODIFIES: this
    // EFFECTS: removes a Dish from Dishes
    public void removeDish(Dish d) {
        dishes.remove(d);
        EventLog.getInstance().logEvent(new Event("Remove dish " + d.getName() + "."));
    }

    public Dish getDish(int i) {
        return dishes.get(i);
    }

    public int getDishIndex(Dish d) {
        return dishes.indexOf(d);
    }

    // MODIFIES: this
    // EFFECTS: calculates the total cost of all ingredients in each dish
    public int calculateCost() {
        cost = 0;
        for (Dish d : dishes) {
            this.cost += d.calculateCost();
        }
        return getCost();
    }

    public int getTotalServings() {
        int servings = 0;
        for (Dish d: dishes) {
            servings += d.getServings();
        }
        return servings;
    }

    // EFFECTS: verifies that the quantity of ingredients can last for a week
    public boolean verifyServings() {
        return (getTotalServings() >= WEEKLY_SERVINGS);
    }

    // EFFECTS: prints grocery list (string) in form
    //      DishName:
    //      ingredient, note
    //      ingredient 2, note2 ...
    public String printGroceryList() {
        String s = "";
        for (Dish d : dishes) {
            s += d.getName() + ", serves " + d.getServings() + ":\n" + d.getIngredientsString();
        }
        s += "Total Cost: " + calculateCost();
        return s;
    }

    // EFFECTS: Writes GroceryList as JSonObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
//        json.put("cost", cost);
        json.put("dishes", dishesToJson());
        return json;
    }

    // EFFECTS: returns dishes in this grocerylist as a JSON array
    private JSONArray dishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Dish d : dishes) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }
}
