package data;

import domain.Store;
import java.io.*;

// Manages storage of Store objects to and from files

public class StorageManager {

// Handles saving and loading Store objects from files

    public static void save(Store store, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(store);
            System.out.println("Store data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving store data: " + e.getMessage());
        }
    }

    public static Store load(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Store) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading store data: " + e.getMessage());
            return null;
        }
    }
}

