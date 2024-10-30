package persistence;

import model.Dish;
import model.GroceryList;
import model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
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

        testlist1 = new GroceryList("generic grocerylist");
        testlist1.addDish(testdish1);
        testlist1.addDish(testdish2);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            GroceryList g = new GroceryList("invalid file test");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterGroceryListEmpty() {
        try {
            GroceryList g = new GroceryList("empty writer list test");
            JsonWriter writer = new JsonWriter("./data/testGroceryListEmpty.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testGroceryListEmpty.json");
            g = reader.read();
            assertEquals("empty writer list test", g.getName());
            assertEquals(0, g.getNumDishes());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGenericGroceryList() {
        try {
            GroceryList g = new GroceryList("generic grocerylist");
            g.addDish(testdish1);
            g.addDish(testdish2);
            JsonWriter writer = new JsonWriter("./data/testWriterGenericGroceryList.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGenericGroceryList.json");
            g = reader.read();
            assertEquals("generic grocerylist", g.getName());
            assertEquals(2, g.getNumDishes());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
