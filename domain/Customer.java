package domain;
// Represents a customer in the system

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Customer implements Serializable {
    private String name;
    private static int idCounter = 0;
    private int id;
    private List<Videogame> gameLibrary;
    private List<Sale> sales;

    // Constructor
    public Customer(String name, int id, List<Videogame> gameLibrary, List<Sale> sales) {
        this.name = name;
        this.id = id;
        this.gameLibrary = new ArrayList<>(gameLibrary);
        this.sales = new ArrayList<>(sales);
        Store.addCustomer(this);
    }

    public Customer(String name, int id) {
        this.name = name;
        this.id = id;
        this.gameLibrary = new ArrayList<>();
        this.sales = new ArrayList<>();
        Store.addCustomer(this);
    }

    //Getters of the variables

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Videogame> getLibrary() {
        return gameLibrary;
    }

    public List<Sale> getSales() {
        return sales;
    }

    //Method to add games purchased to the customer's library

    public void addGame(List<Videogame> game) {
        gameLibrary.addAll(game);
    }

    public void addSale(Sale sale) {
        sales.add(sale);
    }
}
