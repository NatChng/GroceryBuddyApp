package ui.gui;

// should display different things based on add/remove clicked from editing window
// maybe have it take in a grocery list?

import model.Dish;
import model.GroceryList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// Opens a graphical window with prompts to remove a dish to a grocery list
public class RemoveDishWindow extends JFrame implements ActionListener, SoundPlayer {
    private JPanel removeDishPanel;
    private JFrame removeDishWindow;
    private JLabel enterIndex;
    private JButton enterButton;
    private static final int SCREEN_HEIGHT = 250;
    private static final int SCREEN_WIDTH = 400;

    private JTextField enterDishIndexField;
    private GroceryList userlist;

    public RemoveDishWindow(GroceryList g) {
        userlist = g;
        initializeStartingFields();

        removeDishWindow.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        removeDishWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        removeDishWindow.setLocationRelativeTo(null);

        placeComponent(removeDishPanel);
        removeDishWindow.add(removeDishPanel);
        removeDishWindow.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes starting fields
    public void initializeStartingFields() {
        removeDishPanel = new JPanel();
        removeDishWindow = new JFrame();
        enterButton = new JButton("Enter");
        enterDishIndexField = new JTextField(20);
        enterIndex = new JLabel("Please enter the index of the dish you wish to remove:");
    }

    // MODIFIES: this
    // EFFECTS: places component on jpanel
    public void placeComponent(JPanel panel) {
        panel.setLayout(null);

        enterIndex.setBounds(SCREEN_WIDTH / 8, 0, SCREEN_WIDTH, SCREEN_HEIGHT / 6);
        panel.add(enterIndex);

        enterDishIndexField.setBounds(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4,
                SCREEN_WIDTH / 2, SCREEN_HEIGHT / 6);
        enterDishIndexField.addActionListener(this);
        panel.add(enterDishIndexField);

        enterButton = new JButton("Enter");
        enterButton.setBounds(SCREEN_WIDTH * 2 / 5, SCREEN_HEIGHT / 2, SCREEN_WIDTH / 5, 40);
        enterButton.addActionListener(this);
        panel.add(enterButton);
    }

    // MODIFIES: this
    // EFFECTS: removes dish from userlist given dish's index
    public void removeDish(int i) {
        Dish d = userlist.getDish(i);
        userlist.removeDish(d);
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    @Override
    public void actionPerformed(ActionEvent e) {
        int dishnum = Integer.valueOf(enterDishIndexField.getText());

        removeDish(dishnum);
        playSound("./data/buttonpress.wav");

        removeDishWindow.setVisible(false);
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
