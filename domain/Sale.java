package domain;

// Represents a sale transaction in the system

import java.util.ArrayList;
import java.time.LocalDate;

public class Sale {
    private float cost;
    private LocalDate date;
    private ArrayList<Videogame> purchase;

    // Constructor
    public Sale(ArrayList<Videogame> purchase) {
        for (Videogame game : purchase) {
            this.cost += game.getPrice();
        }
        this.date = LocalDate.now();
        this.purchase = purchase;
        Store.addSale(this);
    }

    // Getters
    public float getCost() {
        return cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<Videogame> getPurchase() {
        return purchase;
    }
}