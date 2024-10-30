package ui;

import model.Dish;
import model.GroceryList;
import model.Ingredient;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Represents the GroceryBuddy App (console)
// Citation: TellerApp (thank you for helping me learn how to use scanner)
public class GroceryBuddyApp {
    private Scanner input;
    private GroceryList userlist;
    private GroceryList dishdatabase;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String SAVE_PATH = "./data/myFile.json";

    private Ingredient presetingredient1;
    private Ingredient presetingredient2;
    private Ingredient presetingredient3;
    private Ingredient presetingredient4;
    private Ingredient presetingredient5;
    private Ingredient presetingredient6;
    private Ingredient presetingredient7;
    private Ingredient presetingredient8;
    private Ingredient presetingredient9;

    private Dish presetdish1;
    private Dish presetdish2;
    private Dish presetdish3;

    // EFFECTS: runs GroceryBuddy
    public GroceryBuddyApp() {
        runGroceryBuddy();
    }

    private void runGroceryBuddy() {
        boolean keepGoing = true;
        String command;

        initializeApp();

        initializePresetIngredients();

        initializePresetDishes();

        while (keepGoing) {
            displayHomeMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepGoing = false;
            } else {
                parseInput(command);
            }
        }
        System.out.println("\nGreat job functioning today! See you soon!");
    }

    // MODIFIES: this
    // EFFECTS: instantiates most of the elements that will be used throughout app
    private void initializeApp() {
        userlist = new GroceryList("Guest");
        dishdatabase = new GroceryList("Dish Data");


        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(SAVE_PATH);
        jsonReader = new JsonReader(SAVE_PATH);
    }

    // MODIFIES: this
    // EFFECTS: instantiates a list of ingredients to be included in preset dishes
    private void initializePresetIngredients() {
        presetingredient1 = new Ingredient("Pasta", 400);
        presetingredient2 = new Ingredient("Tomato Sauce", 500);
        presetingredient3 = new Ingredient("Carrot", 100);


        presetingredient4 = new Ingredient("Tofu", 300);
        presetingredient5 = new Ingredient("Ground Meat", 400);
        presetingredient6 = new Ingredient("Mapo Tofu Sauce", 200);
        presetingredient7 = new Ingredient("Rice", 600);
        presetingredient6.addNote("Go to T&T");

        presetingredient8 = new Ingredient("Tomato", 600);
        presetingredient9 = new Ingredient("Egg", 1000);
    }

    // MODIFIES: this
    // EFFECTS: instantiates a preset database of dishes to be added to GroceryList
    private void initializePresetDishes() {
        presetdish1 = new Dish("Spaghetti Marinara", 8);
        presetdish1.addIngredient(presetingredient1);
        presetdish1.addIngredient(presetingredient2);
        presetdish1.addIngredient(presetingredient3);

        presetdish2 = new Dish("Mapo Tofu", 6);
        presetdish2.addIngredient(presetingredient4);
        presetdish2.addIngredient(presetingredient5);
        presetdish2.addIngredient(presetingredient6);
        presetdish2.addIngredient(presetingredient7);

        presetdish3 = new Dish("Tomato Egg", 12);
        presetdish3.addIngredient(presetingredient8);
        presetdish3.addIngredient(presetingredient9);

        dishdatabase.addDish(presetdish1);
        dishdatabase.addDish(presetdish2);
        dishdatabase.addDish(presetdish3);
    }

    // EFFECTS: displays main menu with all options available to user
    private void displayHomeMenu() {
        System.out.println("\nWhat would you like to do today?");
        System.out.println("\tgrocery -> Make a new Grocery List");
        System.out.println("\tmodify  -> Add or remove a dish from current Grocery List");
        System.out.println("\tprint   -> Print a Grocery List");
        System.out.println("\tdish    -> Add a new dish to database");
        System.out.println("\tsave    -> Save current GroceryList and dish database");
        System.out.println("\tload    -> Load a previous GroceryList and dish database");
        System.out.println("\tquit    -> Quit app");
    }

    // EFFECTS: processes user input
    private void parseInput(String command) {
        switch (command) {
            case "grocery":
                createGroceryList();
                break;
            case "modify":
                groceryListOptions();
                break;
            case "print":
                printGroceryList();
                break;
            case "dish":
                initializeDish();
                break;
            case "save":
                saveGroceryList();
                break;
            case "load":
                loadGroceryList();
                break;
            default:
                System.out.println("Invalid input, please try again!");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates new GroceryList based on user inputs
    private void createGroceryList() {
        String command;
        System.out.println("You've selected create grocery list!\n");
        System.out.println("Please name your grocery list:\n");
        command = input.next();
        userlist.setName(command);

        System.out.println("Adding dishes to Grocery List " + userlist.getName());
        addDish();

        verifyServings();

    }

    // EFFECTS: displays all dishes available in dishdatabase
    private void viewDishes() {
        System.out.println("\nDisplaying all saved dishes below:");
        for (Dish d : dishdatabase.getDishes()) {
            System.out.println(dishdatabase.getDishIndex(d) + ". " + d.getName()
                    + ", serves " + d.getServings() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds dish from dishdatabase to user grocerylist userlist
    private void addDish() {
        viewDishes();
        String command;
        Dish dish;

        System.out.println("Please enter the number of the dish you'd like to add:");

        command = input.next();

        dish = dishdatabase.getDish(Integer.parseInt(command));

        confirmDish(dish);

        userlist.addDish(dish);
        System.out.println("Added dish: " + dish.getName());

        groceryListOptions();
    }

    // MODIFIES: this
    // EFFECTS: confirms that the correct dish was added to userlist. If incorrect, removes dish and prompts to re-add.
    private void confirmDish(Dish d) {
        String confirm;
        System.out.println("You've added dish: " + d.getName() + ", serves " + d.getServings());
        System.out.println("Is this information correct? [y/n]");
        confirm = input.next();

        if (confirm.equals("n")) {
            System.out.println("Returning to add dish...");
            userlist.removeDish(d);
            addDish();
        }
    }

    // EFFECTS: displays options to user on how to modify existing grocerylist (add, remove, finish editing)
    private void groceryListOptions() {
        String command;
        System.out.println("What would you like to do with your grocery list? [add/remove/finish]");
        command = input.next();

        if (command.equals("remove")) {
            removeDish();
        } else if (command.equals("add")) {
            addDish();
        } else {
            verifyServings();
            printGroceryList();
        }
    }

    // EFFECTS: verifies that userlist contains enough servings of food to last a full week.
    //          If insufficient, prompts user to add more dishes.
    private void verifyServings() {
        System.out.println("Your list has " + userlist.getTotalServings() + " servings.");

        if (userlist.verifyServings() == false) {
            System.out.println("Please add more dishes!");
            addDish();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes dish from userlist
    private void removeDish() {
        String command;
        Dish dish;

        System.out.println("You've selected remove dish");
        for (Dish d : userlist.getDishes()) {
            System.out.println(userlist.getDishIndex(d) + ". " + d.getName() + ", serves " + d.getServings() + "\n");
        }
        System.out.println("Please enter the number of the dish you'd like to remove:");

        command = input.next();

        dish = userlist.getDish(Integer.parseInt(command));

        userlist.removeDish(dish);

        System.out.println("Removed dish: " + dish.getName());

        groceryListOptions();
    }

    // EFFECTS: prints grocerylist upon completing editing or from main menu at user's behest
    private void printGroceryList() {
        System.out.println(userlist.printGroceryList());
    }

    // EFFECTS: instantiates a new Dish to be added to user database at user behest.
    private void initializeDish() {

        Dish newDish = new Dish("", 0);

        addNewDish(newDish);

        dishdatabase.addDish(newDish);

        System.out.println("You've added " + newDish.getName());
    }

    // MODIFIES: this
    // EFFECTS: modifies this' (newDish) fields based on user input.
    private void addNewDish(Dish d) {
        String dishname;
        int servings;

        System.out.println("You've selected add dish! Please input the name of the dish:\n");
        dishname = input.next();
        d.setName(dishname);
        System.out.println("Please input the number of servings:\n");
        servings = Integer.parseInt(input.next());
        d.setServings(servings);

        addIngredients(d);

        confirmNewDish(d);
    }

    // MODIFIES: this
    // EFFECTS: adds ingredients to this (newDish) based on user input
    private void addIngredients(Dish d) {
        boolean keepGoing = true;
        String ingredientname;
        int ingredientcost;
        String ingredientnote;
        String command;
        while (keepGoing) {
            System.out.println("Please add an ingredient name below:");
            ingredientname = input.next();
            System.out.println("Please add the ingredient's cost below:");
            ingredientcost = Integer.parseInt(input.next());
            System.out.println("Please add in a note if applicable, or press [enter] to skip:");
            ingredientnote = input.next();

            Ingredient newIngredient = new Ingredient(ingredientname, ingredientcost);
            newIngredient.addNote(ingredientnote);

            d.addIngredient(newIngredient);

            System.out.println("Would you like to continue adding ingredients? y/n");
            command = input.next();

            if (command.equals("n")) {
                keepGoing = false;
            }
        }
    }

    // EFFECTS: confirms that this (newDish) is accurate. If inaccurate, prompts user to re-add dish.
    private void confirmNewDish(Dish d) {
        String command;
        System.out.println("You've trying to add dish: " + d.getName() + ", serves " + d.getServings()
                + "\n" + d.getIngredientsString());
        System.out.println("Is this information correct? [y/n]");
        command = input.next();

        if (command.equals("n")) {
            d.clearIngredients();
            System.out.println("Returning to add dish...");
            addNewDish(d);
        }
    }

    // EFFECTS: saves current GroceryList to file
    private void saveGroceryList() {
        try {
            jsonWriter.open();
            jsonWriter.write(userlist);
            jsonWriter.close();
            System.out.println("Saved " + userlist.getName() + " to " + SAVE_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + SAVE_PATH);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads GrocerList from file
    private void loadGroceryList() {
        try {
            userlist = jsonReader.read();
            System.out.println("Loaded " + userlist.getName() + " from " + SAVE_PATH);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + SAVE_PATH);
        }
    }
}
