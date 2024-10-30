package ui.gui;

import model.Dish;
import model.Ingredient;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// Opens a graphical window with prompts to add a new dish to the list of dishes that can be added to a GroceryList
public class AddNewDishWindow extends JFrame implements ActionListener, SoundPlayer {
    private static final int SCREEN_HEIGHT = 400;
    private static final int SCREEN_WIDTH = 600;

    private JPanel addDishPanel1;
    private JPanel addDishPanel2;

    private JFrame addDishWindow1;
    private JFrame addDishWindow2;

    private JLabel dishName;
    private JLabel dishServings;
    private JLabel numIngredients;
    private JLabel ingredientName;
    private JLabel ingredientCost;
    private JLabel ingredientNote;

    private JButton enterButton;
    private JButton addIngredientButton;

    private JTextField enterNameField;
    private JTextField enterServingsField;
    private JTextField numIngredientsField;
    private JTextField ingredientNameField;
    private JTextField ingredientCostField;
    private JTextField ingredientNotesField;

    private int currentIngredientCounter;

    private Dish newDish;

    // TODO: if there's time, reshuffle code to include an initializeStartingFields() method for consistency
    public AddNewDishWindow() {
        initializeStartingFields();
        initializeNewDishWindow();
    }

    // MODIFIES: this
    // EFFECTS: initializes new dish window
    public void initializeNewDishWindow() {
        addDishWindow1.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        addDishWindow1.setDefaultCloseOperation(EXIT_ON_CLOSE);
        addDishWindow1.setLocationRelativeTo(null);

        addDishWindow2.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        addDishWindow2.setDefaultCloseOperation(EXIT_ON_CLOSE);
        addDishWindow2.setLocationRelativeTo(null);
        addDishWindow2.setVisible(false);

        configureLabels();
        configureTextEntries();

        configurePanel1();
        configurePanel2();

        addDishWindow1.add(addDishPanel1);
        addDishWindow2.add(addDishPanel2);

        addDishWindow1.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes starting fields
    public void initializeStartingFields() {
        newDish = new Dish("", 0);

        addDishPanel1 = new JPanel();
        addDishPanel2 = new JPanel();
        addDishWindow1 = new JFrame();
        addDishWindow2 = new JFrame();

        enterNameField = new JTextField(20);
        enterServingsField = new JTextField(20);
        numIngredientsField = new JTextField(20);
        ingredientNameField = new JTextField(20);
        ingredientCostField = new JTextField(20);
        ingredientNotesField = new JTextField(20);

        dishName = new JLabel("Please enter the name of your dish:");
        dishServings = new JLabel("Please enter the total servings of your dish:");
        numIngredients = new JLabel("Please enter the number of ingredients of your dish:");
        ingredientName = new JLabel("Please enter the name of your ingredient:");
        ingredientCost = new JLabel("Please enter the cost of your ingredient:");
        ingredientNote = new JLabel("Optional: Enter any notes for your ingredient:");
    }

    // MODIFIES: this
    // EFFECTS: instantiates and configures properties of JTextFields
    public void configureTextEntries() {
        enterNameField.setBounds(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 8,SCREEN_WIDTH / 2, SCREEN_HEIGHT / 10);
        enterNameField.addActionListener(this);

        enterServingsField.setBounds(SCREEN_WIDTH / 4, SCREEN_HEIGHT * 13 / 40,
                SCREEN_WIDTH / 2, SCREEN_HEIGHT / 10);
        enterServingsField.addActionListener(this);

        numIngredientsField.setBounds(SCREEN_WIDTH / 4, SCREEN_HEIGHT * 21 / 40,
                SCREEN_WIDTH / 2, SCREEN_HEIGHT / 10);
        numIngredientsField.addActionListener(this);

        ingredientNameField.setBounds(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 8,SCREEN_WIDTH / 2, SCREEN_HEIGHT / 10);
        ingredientNameField.addActionListener(this);

        ingredientCostField.setBounds(SCREEN_WIDTH / 4, SCREEN_HEIGHT * 13 / 40,
                SCREEN_WIDTH / 2, SCREEN_HEIGHT / 10);
        ingredientCostField.addActionListener(this);

        ingredientNotesField.setBounds(SCREEN_WIDTH / 4, SCREEN_HEIGHT * 21 / 40,
                SCREEN_WIDTH / 2, SCREEN_HEIGHT / 10);
        ingredientNotesField.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: instantiates and configures properties of JLabels
    public void configureLabels() {
        dishName.setBounds(SCREEN_WIDTH / 3, 0, SCREEN_WIDTH, SCREEN_HEIGHT / 8);

        dishServings.setBounds(SCREEN_WIDTH * 3 / 10, SCREEN_HEIGHT / 5, SCREEN_WIDTH, SCREEN_HEIGHT / 8);

        numIngredients.setBounds(SCREEN_WIDTH / 4, SCREEN_HEIGHT * 2 / 5, SCREEN_WIDTH, SCREEN_HEIGHT / 8);

        ingredientName.setBounds(SCREEN_WIDTH * 3 / 10, 0, SCREEN_WIDTH, SCREEN_HEIGHT / 8);

        ingredientCost.setBounds(SCREEN_WIDTH * 3 / 10, SCREEN_HEIGHT / 5, SCREEN_WIDTH, SCREEN_HEIGHT / 8);

        ingredientNote.setBounds(SCREEN_WIDTH * 28 / 100, SCREEN_HEIGHT * 2 / 5,
                SCREEN_WIDTH, SCREEN_HEIGHT / 8);
    }

    // MODIFIES: this
    // EFFECTS: places components on Panel1 (dish properties)
    public void configurePanel1() {
        addDishPanel1.setLayout(null);

        addDishPanel1.add(dishName);
        addDishPanel1.add(numIngredients);
        addDishPanel1.add(dishServings);

        addDishPanel1.add(enterNameField);
        addDishPanel1.add(enterServingsField);
        addDishPanel1.add(numIngredientsField);

        enterButton = new JButton("Enter");
        enterButton.setBounds(SCREEN_WIDTH * 2 / 5, SCREEN_HEIGHT * 3 / 4, SCREEN_WIDTH / 5, 40);
        enterButton.setActionCommand("enter");
        enterButton.addActionListener(this);
        addDishPanel1.add(enterButton);
    }

    // MODIFIES: this
    // EFFECTS: places components on Panel2 (list of ingredients and their properties)
    public void configurePanel2() {
        addDishPanel2.setLayout(null);

        addDishPanel2.add(ingredientName);
        addDishPanel2.add(ingredientCost);
        addDishPanel2.add(ingredientNote);

        addDishPanel2.add(ingredientNameField);
        addDishPanel2.add(ingredientCostField);
        addDishPanel2.add(ingredientNotesField);

        addIngredientButton = new JButton("Add Ingredient");
        addIngredientButton.setBounds(SCREEN_WIDTH * 2 / 5, SCREEN_HEIGHT * 3 / 4, SCREEN_WIDTH / 5, 40);
        addIngredientButton.setActionCommand("add");
        addIngredientButton.addActionListener(this);
        addDishPanel2.add(addIngredientButton);
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("enter")) {
            playSound("./data/buttonpress.wav");
            createDish(newDish);
            currentIngredientCounter = 0;
            addDishWindow1.setVisible(false);
            addDishWindow2.setVisible(true);

        } else if (e.getActionCommand().equals("add")) {
            playSound("./data/buttonpress.wav");
            addIngredients(newDish);
            addDishWindow2.setVisible(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: modifies Dish d according to user input
    public void createDish(Dish d) {
        d.setName(enterNameField.getText());
        d.setServings(Integer.valueOf(enterServingsField.getText()));
    }

    // MODIFIES: this
    // EFFECTS: adds ingredient to Dish d
    public void addIngredients(Dish d) {
        for (int i = currentIngredientCounter; i < Integer.valueOf(numIngredientsField.getText()); i++) {
            Ingredient ingredient = new Ingredient(ingredientNameField.getText(),
                    Integer.valueOf(ingredientCostField.getText()));
            ingredient.addNote(ingredientNotesField.getText());

            d.addIngredient(ingredient);
            currentIngredientCounter++;

            ingredientNameField.setText("");
            ingredientCostField.setText("");
            ingredientNotesField.setText("");
        }
    }

    // CITATION: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
    // MODIFIES: this
    // EFFECTS: plays audio clip
    public void playSound(String audioName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audioName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Audio error");
            ex.printStackTrace();
        }
    }
}
