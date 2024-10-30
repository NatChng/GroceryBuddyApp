package persistence;

import model.Dish;
import model.GroceryList;
import model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    private GroceryList testlist1;
    private Dish testdish1;
    private Dish testdish2;
    private Ingredient testingredient1;
    private Ingredient testingredient2;

    @BeforeEach
    void setUp() {
        testingredient1 = new Ingredient("test ingredient 1", 8);
        testingredient1.addNote("test note");
        testingredient2 = new Ingredient("test ingredient 2", 2);

        testdish1 = new Dish("test dish 1", 8);
        testdish1.addIngredient(testingredient1);
        testdish1.addIngredient(testingredient2);

        testdish2 = new Dish("test dish 2", 2);

        testlist1 = new GroceryList("test grocery list");
        testlist1.addDish(testdish1);
        testlist1.addDish(testdish2);
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GroceryList g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGroceryList() {
        JsonReader reader = new JsonReader("./data/testGroceryListEmpty.json");
        try {
            GroceryList g = reader.read();
            assertEquals("test grocery list empty", g.getName());
            assertEquals(0, g.getNumDishes());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGroceryList() {
        JsonReader reader = new JsonReader("./data/testGenericGroceryList.json");
        try {
            GroceryList g = reader.read();
            assertEquals("test grocery list", g.getName());
            assertEquals(2, g.getNumDishes());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
