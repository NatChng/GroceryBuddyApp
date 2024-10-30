package ui.gui;


// Represents common behaviour across all GUI elements allowing them to play sounds
public interface SoundPlayer {
    // CITATION: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
    // MODIFIES: this
    // EFFECTS: plays audio clip
    public void playSound(String audioName);
}
