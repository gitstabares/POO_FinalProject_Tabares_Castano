package domain;

// Represents a sale transaction in the system

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Sale {
    private float cost;
    private GregorianCalendar date;
    private ArrayList<Videogame> purchase;

    // Constructor
    public Sale(float cost, GregorianCalendar date, ArrayList<Videogame> purchase) {
        this.cost = cost;
        this.date = date;
        this.purchase = purchase;
    }

    // Getters
    public float getCost() {
        return cost;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public ArrayList<Videogame> getPurchase() {
        return purchase;
    }
}