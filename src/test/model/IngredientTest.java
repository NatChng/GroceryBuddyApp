package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Tests for Ingredient methods
public class IngredientTest {
    private Ingredient test1;

    @BeforeEach
    void setUp() {
        test1 = new Ingredient("Carrot", 89);
    }

    @Test
    void testAddNote() {
        assertEquals("", test1.getNotes());
        test1.addNote("T&T Richmond");
        assertEquals("T&T Richmond", test1.getNotes());
    }
}
