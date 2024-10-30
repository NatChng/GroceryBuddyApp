package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.*;
import org.json.*;

// Citation: JsonSerializationDemo
public class JsonReader {
    private String source;
    private EventLog events;

    public JsonReader(String source) {
        this.source = source;
        events = new EventLog();
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GroceryList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGroceryList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        EventLog.getInstance().logEvent(new Event("Loaded GroceryList from json."));
        return contentBuilder.toString();
    }

    // EFFECTS: parses GroceryList from JSON object and returns it
    private GroceryList parseGroceryList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        GroceryList g = new GroceryList(name);
        addDishes(g, jsonObject);
        return g;
    }

    // MODIFIES: g
    // EFFECTS: parses Dishes from JSON object and adds them to GroceryList
    private void addDishes(GroceryList g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("dishes");
        for (Object json : jsonArray) {
            JSONObject nextDish = (JSONObject) json;
            addDish(g, nextDish);
        }
    }

    // MODIFIES: g
    // EFFECTS: parses Dish from JSON object and adds it to GroceryList
    private void addDish(GroceryList g, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int servings = jsonObject.getInt("servings");
        Dish d = new Dish(name, servings);
        JSONArray jsonArray = jsonObject.getJSONArray("ingredients");
        for (Object json : jsonArray) {
            JSONObject nextIngredient = (JSONObject) json;
            addIngredient(d, nextIngredient);
        }
        g.addDish(d);
    }

    // MODIFIES: d
    // EFFECTS: parses ingredient from JSON object and adds it to Dish
    private void addIngredient(Dish d, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int cost = jsonObject.getInt("cost");
        Ingredient i = new Ingredient(name, cost);
        String notes = jsonObject.getString("notes");
        i.addNote(notes);

        d.addIngredient(i);
    }
}
