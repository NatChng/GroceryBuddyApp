package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// tests for Dish methods
public class DishTest {
    private Dish testdish1;
    private Dish testdish2;
    private Ingredient testingredient1;
    private Ingredient testingredient2;
    private Ingredient testingredient3;

    @BeforeEach
    public void setUp() {
        testingredient1 = new Ingredient("Test 1", 350);
        testingredient2 = new Ingredient("Test 2", 4000);
        testingredient3 = new Ingredient("Test 3", 50);

        testingredient2.addNote("Test Note");

        testdish1 = new Dish("Test Dish 1", 4);
        testdish2 = new Dish("Test Dish 2", 8);

        testdish1.addIngredient(testingredient1);
        testdish1.addIngredient(testingredient2);
    }

    @Test
    void testGetNumIngredients(){
        assertEquals(2, testdish1.getNumIngredients());
        assertEquals(0, testdish2.getNumIngredients());
    }

    @Test
    void testaddIngredients(){
        testdish1.addIngredient(testingredient3);
        assertEquals(3, testdish1.getNumIngredients());
        testdish2.addIngredient(testingredient3);
        assertEquals(1, testdish2.getNumIngredients());
    }

    @Test
    void testRemoveIngredients(){
        testdish1.removeIngredient(testingredient1);
        assertEquals(1, testdish1.getNumIngredients());
    }

    @Test
    void testClearIngredients(){
        testdish1.clearIngredients();
        assertEquals(0, testdish1.getNumIngredients());
    }

    @Test
    void testGetIngredientsString(){
        assertEquals("", testdish2.getIngredientsString());
        assertEquals("Test 1, " + "\n" + "Test 2, Test Note" + "\n", testdish1.getIngredientsString());
    }

    @Test
    void testCalculateCost(){
        assertEquals(4350, testdish1.calculateCost());
        testdish1.removeIngredient(testingredient1);
        assertEquals(4000, testdish1.calculateCost());

        assertEquals(0, testdish2.calculateCost());
        testdish2.addIngredient(testingredient3);
        assertEquals(50, testdish2.calculateCost());
    }
}
