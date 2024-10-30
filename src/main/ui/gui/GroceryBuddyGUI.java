package ui.gui;

import model.Event;
import model.EventLog;
import model.GroceryList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the graphical interface of the GroceryBuddyApp
public class GroceryBuddyGUI extends JFrame implements ActionListener, SoundPlayer {
    private static final int SCREEN_HEIGHT = 720;
    private static final int SCREEN_WIDTH = 1280;
    private static final Color PANEL_COLOUR = new Color(255, 255, 255);
    private static final Color BUTTON_COLOUR = new Color(255, 247, 199);
    private static final Dimension BUTTON_DIMENSION = new Dimension(150, 75);
    private static final String SAVE_PATH = "./data/myFile.json";
    private static final String TITLE_MUSIC = "./data/pkmn emerald theme.wav"; // set this to your favourite track

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JButton newGroceryListButton;
    private JButton editGroceryListButton;
    private JButton loadButton;
    private JButton promptSaveButton;
    private JButton quitButton;
    private JButton addDishButton;

    private JButton saveButton;
    private JButton noSaveButton;

    private JPanel homeOptionsPanel;
    private JPanel savePanel;
    private JPanel messagePanel;
    private JPanel welcomePanel;

    private JFrame homeScreen;

    private JLabel homeBackground;
    private JLabel homeGreeting;
    private JLabel saveInfo;

    private static GroceryList groceryList;

    public GroceryBuddyGUI() {
        super("Grocery Buddy");
        initializeStartingFields();
        initializeHomeScreen();
    }

    // MODIFIES: this
    // EFFECTS: initialize all starting fields
    public void initializeStartingFields() {
        newGroceryListButton = new JButton("New Grocery List");
        editGroceryListButton = new JButton("View/Edit Grocery List");
        addDishButton = new JButton("Add New Dish");
        promptSaveButton = new JButton("Save Grocery List");
        saveButton = new JButton("Save");
        noSaveButton = new JButton("Don't Save");
        loadButton = new JButton("Load Grocery List");
        quitButton = new JButton("Quit");

        homeScreen = new JFrame();

        homeGreeting = new JLabel("Welcome to Grocery Buddy!", SwingConstants.CENTER);
        homeGreeting.setFont(new Font("Arial", Font.PLAIN, 24));
        saveInfo = new JLabel("Would you like to save your Grocery List?", SwingConstants.CENTER);
        saveInfo.setFont(new Font("Arial", Font.PLAIN, 18));

        homeOptionsPanel = new JPanel();
        welcomePanel = new JPanel();

        savePanel = new JPanel();
        messagePanel = new JPanel();

        jsonReader = new JsonReader(SAVE_PATH);
        jsonWriter = new JsonWriter(SAVE_PATH);
    }

    // CITATION: https://stackoverflow.com/questions/1064977/setting-background-images-in-jframe
    // MODIFIES: this
    // EFFECTS: initializes home screen
    public void initializeHomeScreen() {
        homeScreen.setLayout(null);

        homeScreen.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        try {
            homeBackground = new JLabel(new ImageIcon(ImageIO.read(new File("./data/backgroundimage.jpg"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        homeScreen.setContentPane(homeBackground);
        homeScreen.setDefaultCloseOperation(EXIT_ON_CLOSE);
        homeScreen.setLocationRelativeTo(null);

        constructPanels();

        constructButtons();

        configurePanels();

        configureButtons();

        homeScreen.add(homeOptionsPanel);
        homeScreen.add(welcomePanel);
        homeScreen.add(savePanel);
        homeScreen.add(messagePanel);

        playSound(TITLE_MUSIC);

        homeScreen.setVisible(true);
//        homeScreen.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: configures JPanel properties and visibility
    public void constructPanels() {
        homeOptionsPanel.setBounds(0, SCREEN_HEIGHT * 3 / 4, SCREEN_WIDTH, SCREEN_HEIGHT / 6);
        homeOptionsPanel.setLayout(new GridBagLayout());
        homeOptionsPanel.setBackground(PANEL_COLOUR);
        homeOptionsPanel.setVisible(true);

        welcomePanel.setBounds(0, SCREEN_HEIGHT * 8 / 12, SCREEN_WIDTH, SCREEN_HEIGHT / 12);
        welcomePanel.setLayout(new GridLayout());
        welcomePanel.setBackground(PANEL_COLOUR);
        welcomePanel.setVisible(true);

        savePanel.setBounds(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 3,
                SCREEN_WIDTH / 3, SCREEN_HEIGHT / 4);
        savePanel.setLayout(new GridBagLayout());
        savePanel.setVisible(false);
        savePanel.setBackground(PANEL_COLOUR);

        // TODO: make the save panel look prettier and add in a text description

        messagePanel.setBounds(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 3,
                SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4);
        messagePanel.setLayout(new GridBagLayout());
        messagePanel.setVisible(false);
        messagePanel.setBackground(PANEL_COLOUR);
    }

    // MODIFIES: this
    // EFFECTS: modifies buttons to have specific dimensions and backgrounds
    public void constructButtons() {
        newGroceryListButton.setPreferredSize(BUTTON_DIMENSION);
        newGroceryListButton.setBackground(BUTTON_COLOUR);
        editGroceryListButton.setPreferredSize(BUTTON_DIMENSION);
        editGroceryListButton.setBackground(BUTTON_COLOUR);
        addDishButton.setPreferredSize(BUTTON_DIMENSION);
        addDishButton.setBackground(BUTTON_COLOUR);
        promptSaveButton.setPreferredSize(BUTTON_DIMENSION);
        promptSaveButton.setBackground(BUTTON_COLOUR);
        loadButton.setPreferredSize(BUTTON_DIMENSION);
        loadButton.setBackground(BUTTON_COLOUR);
        quitButton.setPreferredSize(BUTTON_DIMENSION);
        quitButton.setBackground(BUTTON_COLOUR);

        saveButton.setPreferredSize(BUTTON_DIMENSION);
        saveButton.setBackground(BUTTON_COLOUR);
        noSaveButton.setPreferredSize(BUTTON_DIMENSION);
        noSaveButton.setBackground(BUTTON_COLOUR);
    }


    // CITATION: https://www.youtube.com/watch?v=eeE44RmE1FM
    // MODIFIES: this
    // EFFECTS: add components (buttons) to JPanels
    public void configurePanels() {
        // NOTE: so buttons are spaced apart
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);

        homeOptionsPanel.add(loadButton, c);
        homeOptionsPanel.add(newGroceryListButton, c);
        homeOptionsPanel.add(editGroceryListButton, c);
        homeOptionsPanel.add(addDishButton, c);
        homeOptionsPanel.add(promptSaveButton, c);
        homeOptionsPanel.add(quitButton, c);

        savePanel.add(saveButton, c);
        savePanel.add(noSaveButton);

        welcomePanel.add(homeGreeting);

        homeScreen.add(homeOptionsPanel);
        homeScreen.add(welcomePanel);
        homeScreen.add(savePanel);
    }

    // MODIFIES: this
    // EFFECTS: assigns roles to buttons
    public void configureButtons() {
        newGroceryListButton.setActionCommand("create new grocery list");
        newGroceryListButton.addActionListener(this);

        editGroceryListButton.setActionCommand("edit grocery list");
        editGroceryListButton.addActionListener(this);

        addDishButton.setActionCommand("add dish");
        addDishButton.addActionListener(this);

        promptSaveButton.setActionCommand("save grocery list");
        promptSaveButton.addActionListener(this);

        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);

        noSaveButton.setActionCommand("no save");
        noSaveButton.addActionListener(this);

        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);

        quitButton.setActionCommand("quit");
        quitButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: processes button commands
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("create new grocery list")) {
            groceryList = new GroceryList("filler name");
            playSound("./data/buttonpress.wav");
            homeScreen.setVisible(false);
            new CreateGroceryListWindow();

        } else if (e.getActionCommand().equals("edit grocery list")) {
            playSound("./data/buttonpress.wav");
            homeScreen.setVisible(false);
            new EditGroceryListWindow(groceryList);

        } else if (e.getActionCommand().equals("view grocery list")) {
            playSound("./data/buttonpress.wav");
            homeScreen.setVisible(false);

        } else if (e.getActionCommand().equals("add dish")) {
            playSound("./data/buttonpress.wav");
            homeScreen.setVisible(false);
            new AddNewDishWindow();
        } else {
            actionPerformedCont(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes button commands
    public void actionPerformedCont(ActionEvent e) {
        if (e.getActionCommand().equals("save grocery list")) {
            playSound("./data/buttonpress.wav");
            savePanel.setVisible(true);
        } else if (e.getActionCommand().equals("save")) {
            playSound("./data/buttonpress.wav");
            saveGroceryList();
            savePanel.setVisible(false);
        } else if (e.getActionCommand().equals("no save")) {
            playSound("./data/buttonpress.wav");
            savePanel.setVisible(false);
        } else if (e.getActionCommand().equals("load")) {
            playSound("./data/buttonpress.wav");
            loadGroceryList();
        } else if (e.getActionCommand().equals("quit")) {
            playSound("./data/buttonpress.wav");

            for (Event event : EventLog.getInstance()) {
                System.out.println(event);
            }
            System.exit(0);
        }
    }

//    for (model.Event e : model.EventLog.getInstance()) {
//        System.out.println(e);
//    }

    // MODIFIES: this
    // EFFECTS: loads Grocery List from json file
    public void loadGroceryList() {
        try {
            GroceryList loadedList = jsonReader.read();
            System.out.println("Successfully loaded Grocery List!");
            groceryList = loadedList;
        } catch (IOException e) {
            System.out.println("Unable to read from file " + SAVE_PATH);
            // TODO: if you have time, maybe make an error window?
        }
    }

    // MODIFIES: this
    // EFFECTS: saves groceryList to JSon file
    public void saveGroceryList() {
        try {
            jsonWriter.open();
            jsonWriter.write(groceryList);
            jsonWriter.close();
            System.out.println("Saved to " + SAVE_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + SAVE_PATH);
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
