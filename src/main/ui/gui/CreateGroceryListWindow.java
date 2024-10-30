package ui.gui;

import model.GroceryList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// Displays a graphical window with prompts to create a new GroceryList
public class CreateGroceryListWindow extends JFrame implements ActionListener, SoundPlayer {
    private final JPanel createListPanel;
    private final JFrame createListWindow;
    private JLabel enterName;
    private JButton enterButton;
    private static final int SCREEN_HEIGHT = 250;
    private static final int SCREEN_WIDTH = 400;

    private JTextField enterNameField;

    public CreateGroceryListWindow() {
        createListPanel = new JPanel();
        createListWindow = new JFrame();

        createListWindow.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        createListWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        createListWindow.setLocationRelativeTo(null);

        placeComponent(createListPanel);
        createListWindow.add(createListPanel);

        createListWindow.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: places components on JPanel
    public void placeComponent(JPanel panel) {
        panel.setLayout(null);

        enterName = new JLabel("Please enter the name of your grocery list:");
        enterName.setBounds(SCREEN_WIDTH / 5, 0, SCREEN_WIDTH, SCREEN_HEIGHT / 6);

        panel.add(enterName);

        enterNameField = new JTextField(20);
        enterNameField.setBounds(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4,
                SCREEN_WIDTH / 2, SCREEN_HEIGHT / 6);
        enterNameField.addActionListener(this);
        panel.add(enterNameField);

        enterButton = new JButton("Enter");
        enterButton.setBounds(SCREEN_WIDTH * 2 / 5, SCREEN_HEIGHT / 2, SCREEN_WIDTH / 5, 40);
        enterButton.addActionListener(this);
        panel.add(enterButton);

    }

    // MODIFIES: this
    // EFFECTS: processes user input
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = enterNameField.getText();

        GroceryList newGroceryList = new GroceryList(text);
        playSound("./data/buttonpress.wav");

        createListWindow.setVisible(false);

        new EditGroceryListWindow(newGroceryList);
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
