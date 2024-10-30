package persistence;

import model.Event;
import model.EventLog;
import model.GroceryList;
import org.json.JSONObject;

import java.io.*;

// Citation: JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;
    private EventLog events;

    public JsonWriter(String destination) {
        this.destination = destination;
        events = new EventLog();
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(GroceryList g) {
        JSONObject json = g.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
        EventLog.getInstance().logEvent(new Event("Saved current GroceryList to file."));
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}