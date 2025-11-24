package domain;
// Represents a customer in the system

import java.util.ArrayList;
public class Customer {
    private String name;
    private int id;
    private ArrayList<Videogame> gameLibrary;
    private ArrayList<Sale> sales;

    // Constructor

    public Customer(String name, int id) {
        this.name = name;
        this.id = id;
        this.gameLibrary = new ArrayList<>();
        Store.addCustomer(this);
    }

    //Getters of the variables

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Videogame> getLibrary() {
        return gameLibrary;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    //Method to add games purchased to the customer's library

    public void addGame(ArrayList<Videogame> game) {
        gameLibrary.addAll(game);
    }

    public void addSale(Sale sale) {
        sales.add(sale);
    }
}
