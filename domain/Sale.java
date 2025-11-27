package domain;

// Represents a sale transaction in the system

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Sale implements Serializable {
    private float cost;
    private LocalDate date;
    private List<Videogame> purchase;
    private static int idCounter = 0;
    private int id;

    // Constructor for loading from storage
    public Sale(List<Videogame> purchase, int id, LocalDate date, float cost) {
        this.cost = cost;
        this.date = date;
        this.id = id;
        this.purchase = purchase;
        Store.addSale(this);
    }

    // Constructor for new sales
    public Sale(List<Videogame> purchase, Customer customer) {
        for (Videogame game : purchase) {
            this.cost += game.getPrice();
        }
        this.date = LocalDate.now();
        this.id = idCounter++;
        this.purchase = purchase;
        Store.addSale(this);
        customer.addSale(this);
        customer.addGame(purchase);
    }

    // Backwards-compatible constructor for sales without a customer
    public Sale(List<Videogame> purchase) {
        for (Videogame game : purchase) {
            this.cost += game.getPrice();
        }
        this.date = LocalDate.now();
        this.id = idCounter++;
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

    public List<Videogame> getPurchase() {
        return purchase;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return String.valueOf(id);
    }
}