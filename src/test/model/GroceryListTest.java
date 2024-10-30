package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// test for GroceryList methods
public class GroceryListTest {
    private Dish testdish1;
    private Dish testdish2;
    private Dish testdish3;
    private Ingredient testingredient1;
    private Ingredient testingredient2;
    private Ingredient testingredient3;
    private GroceryList testlist1;
    private GroceryList testlist2;

    @BeforeEach
    public void setUp() {
        testingredient1 = new Ingredient("Test 1", 350);
        testingredient2 = new Ingredient("Test 2", 4000);
        testingredient3 = new Ingredient("Test 3", 50);

        testingredient2.addNote("Test Note");

        testdish1 = new Dish("Test Dish 1", 4);
        testdish2 = new Dish("Test Dish 2", 8);
        testdish3 = new Dish("Test Dish 3", 8);

        testdish1.addIngredient(testingredient1);
        testdish1.addIngredient(testingredient2);

        testdish2.addIngredient(testingredient3);

        testlist1 = new GroceryList("Test GroceryList 1");
        testlist2 = new GroceryList("Test GroceryList 2");

        testlist1.addDish(testdish1);
        testlist1.addDish(testdish2);
    }

    @Test
    void testGetNumDishes() {
        assertEquals(2, testlist1.getNumDishes());
        assertEquals(0, testlist2.getNumDishes());
    }

    @Test
    void testAddDish() {
        testlist1.addDish(testdish3);
        assertEquals(3, testlist1.getNumDishes());
        testlist2.addDish(testdish3);
        assertEquals(1, testlist2.getNumDishes());
    }

    @Test
    void testRemoveDish() {
        testlist1.removeDish(testdish1);
        assertEquals(1, testlist1.getNumDishes());
    }

    @Test
    void testGetDishIndex() {
        assertEquals(1, testlist1.getDishIndex(testdish2));
    }

    @Test
    void testCalculateCost() {
        assertEquals(4400, testlist1.calculateCost());
        testlist1.removeDish(testdish2);
        assertEquals(4350, testlist1.calculateCost());
        testlist1.addDish(testdish1);
        assertEquals(8700, testlist1.calculateCost());

        assertEquals(0, testlist2.calculateCost());

    }

    @Test
    void testVerifyServings() {
        assertFalse(testlist1.verifyServings());        // servings < WEEKLY_SERVINGS
        testlist1.addDish(testdish3);
        assertTrue(testlist1.verifyServings());         // servings = WEEKLY_SERVINGS
        testlist1.addDish(testdish1);
        assertTrue(testlist1.verifyServings());         // servings > WEEKLY_SERVINGS
    }

    @Test
    void testPrintGroceryList(){
        assertEquals("Test Dish 1, serves 4:\n" + "Test 1, \n" + "Test 2, Test Note\n"
                + "Test Dish 2, serves 8:\n" + "Test 3, \n" + "Total Cost: 4400", testlist1.printGroceryList());
        testlist1.addDish(testdish3);
        assertEquals("Test Dish 1, serves 4:\n" + "Test 1, \n" + "Test 2, Test Note\n"
                + "Test Dish 2, serves 8:\n" + "Test 3, \n" + "Test Dish 3, serves 8:\n" + "Total Cost: 4400",
                testlist1.printGroceryList());
        testlist1.removeDish(testdish2);
        assertEquals("Test Dish 1, serves 4:\n" + "Test 1, \n" + "Test 2, Test Note\n"
                + "Test Dish 3, serves 8:\n" + "Total Cost: 4350", testlist1.printGroceryList());
        assertEquals("Total Cost: 0", testlist2.printGroceryList());
    }
}
