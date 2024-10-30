package ui.gui;

import model.Dish;
import model.GroceryList;
import model.Ingredient;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class AddDishWindow extends JFrame implements ActionListener, SoundPlayer {
    private JPanel addDishPanel;
    private JFrame addDishWindow;
    private JLabel enterIndex;
    private JButton enterButton;
    private JTextField enterDishIndexField;

    private GroceryList userList;
    private GroceryList defaultDishes;

    private JScrollPane dishDatabaseScroll;

    private DefaultListModel listModel;

    private JList dishDatabase;

    private static final int SCREEN_HEIGHT = 250;
    private static final int SCREEN_WIDTH = 800;
    private static final Color PANEL_COLOUR = new Color(255, 255, 255);
    private static final Color BUTTON_COLOUR = new Color(255, 247, 199);

    // Opens a graphical window with prompts to add a dish to a grocery list
    public AddDishWindow(GroceryList g) {
        userList = g;
        initalizeStartingFields();
        initializeAddDishWindow();
    }

    public void initalizeStartingFields() {
        addDishWindow = new JFrame();
        addDishPanel = new JPanel();
        enterDishIndexField = new JTextField(3);
        enterIndex = new JLabel("Please enter the index of the dish you wish to add:");
        enterButton = new JButton("Enter");

        defaultDishes = new GroceryList("Saved Dishes");

        initializeDefaultDishes();
    }

    public void initializeDefaultDishes() {
        initializeDefaultDish1();
        initializeDefaultDish2();
        initializeDefaultDish3();
    }

    public void initializeDefaultDish1() {
        Ingredient presetingredient1 = new Ingredient("Pasta", 400);
        Ingredient presetingredient2 = new Ingredient("Tomato Sauce", 500);
        Ingredient presetingredient3 = new Ingredient("Carrot", 100);

        Dish presetdish1 = new Dish("Spaghetti Marinara", 8);
        presetdish1.addIngredient(presetingredient1);
        presetdish1.addIngredient(presetingredient2);
        presetdish1.addIngredient(presetingredient3);

        defaultDishes.addDish(presetdish1);
    }

    public void initializeDefaultDish2() {
        Ingredient presetingredient4 = new Ingredient("Tofu", 300);
        Ingredient presetingredient5 = new Ingredient("Ground Meat", 400);
        Ingredient presetingredient6 = new Ingredient("Mapo Tofu Sauce", 200);
        Ingredient presetingredient7 = new Ingredient("Rice", 600);
        presetingredient6.addNote("Go to T&T");

        Dish presetdish2 = new Dish("Mapo Tofu", 6);
        presetdish2.addIngredient(presetingredient4);
        presetdish2.addIngredient(presetingredient5);
        presetdish2.addIngredient(presetingredient6);
        presetdish2.addIngredient(presetingredient7);

        defaultDishes.addDish(presetdish2);
    }

    public void initializeDefaultDish3() {
        Ingredient presetingredient8 = new Ingredient("Tomato", 600);
        Ingredient presetingredient9 = new Ingredient("Egg", 1000);

        Dish presetdish3 = new Dish("Tomato Egg", 12);
        presetdish3.addIngredient(presetingredient8);
        presetdish3.addIngredient(presetingredient9);

        defaultDishes.addDish(presetdish3);
    }

    public void initializeAddDishWindow() {
        addDishWindow.setLayout(null);
        addDishWindow.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        addDishWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        addDishWindow.setLocationRelativeTo(null);

        constructScrollPane();

        configureButtons();

        placeComponent(addDishPanel);

        addDishWindow.add(addDishPanel);
        addDishWindow.add(dishDatabaseScroll);

        addDishWindow.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: constructs scrollpane with data from g
    public void constructScrollPane() {
        ArrayList<Dish> listOfDishes = defaultDishes.getDishes();
        listModel = new DefaultListModel<>();
        for (Dish d : listOfDishes) {
            String s = listOfDishes.indexOf(d) + ". " + d.getName() + ", serves "
                    + d.getServings() + ": " + d.getIngredientsGUI();
            listModel.addElement(s);
        }
        dishDatabase = new JList(listModel);

        dishDatabase.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dishDatabase.setSelectedIndex(0);
        dishDatabase.setVisibleRowCount(10);
        dishDatabaseScroll = new JScrollPane(dishDatabase);
        dishDatabaseScroll.setFont(new Font("Arial", Font.PLAIN, 24));
        dishDatabaseScroll.setBounds(0, 0, SCREEN_WIDTH,
                SCREEN_HEIGHT / 4);
    }

    // MODIFIES: this
    // EFFECTS: adds components to panels
    public void placeComponent(JPanel panel) {
        panel.setBounds(0, SCREEN_HEIGHT / 4, SCREEN_WIDTH, SCREEN_HEIGHT * 3 / 4);
        panel.setLayout(null);
        panel.setBackground(PANEL_COLOUR);

        enterIndex.setBounds(SCREEN_WIDTH / 3, 0, SCREEN_WIDTH, SCREEN_HEIGHT / 12);
        panel.add(enterIndex);

        enterDishIndexField.setBounds(SCREEN_WIDTH * 2 / 5, SCREEN_HEIGHT / 8,
                SCREEN_WIDTH / 5, SCREEN_HEIGHT / 8);
        enterDishIndexField.addActionListener(this);
        panel.add(enterDishIndexField);

        enterButton.setBounds(SCREEN_WIDTH * 2 / 5, SCREEN_HEIGHT / 3, SCREEN_WIDTH / 5, 40);
        enterButton.setBackground(BUTTON_COLOUR);
        enterButton.addActionListener(this);

        panel.add(enterButton);
        panel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: assigns roles to buttons
    public void configureButtons() {
        enterButton.setActionCommand("enter");
        enterButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    @Override
    public void actionPerformed(ActionEvent e) {
        int dishnum = Integer.valueOf(enterDishIndexField.getText());

        addDish(dishnum);
        playSound("./data/buttonpress.wav");
        enterDishIndexField.setText("");            // TODO: figure out why this is called twice instead of once
        addDishWindow.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: adds dish from defaultdishes to userlist given index
    public void addDish(int i) {
        Dish d = defaultDishes.getDish(i);
        userList.addDish(d);
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
