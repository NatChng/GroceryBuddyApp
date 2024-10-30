package ui.gui;

import model.Dish;
import model.GroceryList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// TODO: (after project), add a wayto verify servings (like in console app)
// Opens a window displaying the dishes included in the current GroceryList and prompts the user to edit
public class EditGroceryListWindow extends JFrame implements ActionListener, SoundPlayer {
    private static final int SCREEN_HEIGHT = 720;
    private static final int SCREEN_WIDTH = 1280;
    private static final Color PANEL_COLOUR = new Color(255, 255, 255);
    private static final Color BUTTON_COLOUR = new Color(255, 247, 199);
    private static final Dimension BUTTON_DIMENSION = new Dimension(150, 75);

    private JFrame editGroceryWindow;

    private JPanel editingOptions;

    private JButton addDishButton;
    private JButton removeDishButton;
    private JButton returnButton;
    private JButton refreshButton;

    private JLabel editorBackground;

    private JScrollPane groceryListScroll;

    private DefaultListModel listModel;

    private JList viewableList;

    private GroceryList userList;

    public EditGroceryListWindow(GroceryList g) {
        userList = g;
        initializeStartingFields();
        initializeEditWindow();
    }

    // MODIFIES: this
    // EFFECTS: initializes all starting fields
    public void initializeStartingFields() {
        editGroceryWindow = new JFrame();

        addDishButton = new JButton("Add Dish");
        removeDishButton = new JButton("Remove Dish");
        refreshButton = new JButton("Refresh");
        returnButton = new JButton("Return to Home");

        editingOptions = new JPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes editing window
    public void initializeEditWindow() {
        editGroceryWindow.setLayout(null);
        editGroceryWindow.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        try {
            editorBackground = new JLabel(new ImageIcon(ImageIO.read(new File("./data/backgroundimage.jpg"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editGroceryWindow.setContentPane(editorBackground);
        editGroceryWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        editGroceryWindow.setLocationRelativeTo(null);

        constructScrollPane();

        constructPanels();

        constructButtons();

        configurePanels();

        configureButtons();

        editGroceryWindow.add(editingOptions);
        editGroceryWindow.add(groceryListScroll);

        editGroceryWindow.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: constructs scrollpane with data from g
    public void constructScrollPane() {
        ArrayList<Dish> listOfDishes = userList.getDishes();
        listModel = new DefaultListModel<>();
        for (Dish d : listOfDishes) {
            String s = listOfDishes.indexOf(d) + ". " + d.getName() + ", serves "
                    + d.getServings() + ": " + d.getIngredientsGUI();
            listModel.addElement(s);
        }
        viewableList = new JList(listModel);

        viewableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        viewableList.setSelectedIndex(0);
        viewableList.setVisibleRowCount(10);
        groceryListScroll = new JScrollPane(viewableList);
        groceryListScroll.setFont(new Font("Arial", Font.PLAIN, 24));
        groceryListScroll.setBounds(0, SCREEN_HEIGHT / 6, SCREEN_WIDTH,
                SCREEN_HEIGHT / 4);
    }

    // MODIFIES: this
    // EFFECTS: configures JPanel properties and visibility
    public void constructPanels() {
        editingOptions.setBounds(0, SCREEN_HEIGHT * 3 / 4, SCREEN_WIDTH, SCREEN_HEIGHT / 6);
        editingOptions.setLayout(new GridBagLayout());
        editingOptions.setBackground(PANEL_COLOUR);
        editingOptions.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: modifies buttons to have specific dimensions and backgrounds
    public void constructButtons() {
        addDishButton.setPreferredSize(BUTTON_DIMENSION);
        addDishButton.setBackground(BUTTON_COLOUR);
        removeDishButton.setPreferredSize(BUTTON_DIMENSION);
        removeDishButton.setBackground(BUTTON_COLOUR);
        refreshButton.setPreferredSize(BUTTON_DIMENSION);
        refreshButton.setBackground(BUTTON_COLOUR);
        returnButton.setPreferredSize(BUTTON_DIMENSION);
        returnButton.setBackground(BUTTON_COLOUR);
    }


    // CITATION: https://www.youtube.com/watch?v=eeE44RmE1FM
    // MODIFIES: this
    // EFFECTS: adds buttons to panels
    public void configurePanels() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        editingOptions.add(addDishButton, c);
        editingOptions.add(removeDishButton, c);
        editingOptions.add(refreshButton, c);
        editingOptions.add(returnButton, c);
    }

    // MODIFIES: this
    // EFFECTS: assigns roles to buttons
    public void configureButtons() {
        addDishButton.setActionCommand("add dish");
        addDishButton.addActionListener(this);
        removeDishButton.setActionCommand("remove dish");
        removeDishButton.addActionListener(this);
        refreshButton.setActionCommand("update");
        refreshButton.addActionListener(this);
        returnButton.setActionCommand("return");
        returnButton.addActionListener(this);
    }


    // MODIFIES: this
    // EFFECTS: processes button commands
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add dish")) {
            playSound("./data/buttonpress.wav");
            new AddDishWindow(userList);

        } else if (e.getActionCommand().equals("remove dish")) {
            playSound("./data/buttonpress.wav");
            new RemoveDishWindow(userList);

        } else if (e.getActionCommand().equals("return")) {
            playSound("./data/buttonpress.wav");
            editGroceryWindow.setVisible(false);
            new GroceryBuddyGUI();

        } else if (e.getActionCommand().equals("update")) {
            playSound("./data/buttonpress.wav");
            editGroceryWindow.setVisible(false);
            new EditGroceryListWindow(userList);
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
