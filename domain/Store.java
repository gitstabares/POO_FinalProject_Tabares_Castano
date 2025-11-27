package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Store implements Serializable {
    // Static list for videogames, sales, and customers
    private static List<Videogame> inventory = new ArrayList<>();
    private static List<Sale> sales = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    // Total income of the store
    private static float income = 0;

    // Setters functions to add data to the static lists
    public static void addCustomer(Customer customer) {
        if (!customers.contains(customer)) customers.add(customer);
    }
    public static void addGame(Videogame videogame) {
        if (!inventory.contains(videogame)) inventory.add(videogame);
    }
    public static void addGame(List<Videogame> videogames) {
        for (Videogame game : videogames) {
            addGame(game);
        }
    }
    public static void addSale(Sale sale) {
        sales.add(sale);
        income += sale.getCost();
    }
    // Getters functions to retrieve data from the static lists
    public static List<Videogame> getVideoGames() {
        return inventory;
    }
    public static List<Customer> getCustomers() {
        return customers;
    }
    public static List<Sale> getSales() {
        return sales;
    }
    public static float getIncome() {
        return income;
    }
    // Look videogames by genre (partial match)
    public static List<Videogame> lookGameByGenre(String genre) {
        List<Videogame> foundGames = new ArrayList<>();
        genre = genre.toLowerCase();
        for (Videogame game : inventory) {
            if (game.getGenre().toLowerCase().contains(genre)) {
                foundGames.add(game);
            }
        }
        return foundGames;
    }
    // Look videogames by title (partial match)
    public static List<Videogame> lookGamesByTitle(String title) {
        List<Videogame> foundGames = new ArrayList<>();
        title = title.toLowerCase();
        for (Videogame game : inventory) {
            if (game.getTitle().toLowerCase().contains(title)) {
                foundGames.add(game);
            }
        }
        return foundGames;
    }
    // Look videogame by exact title
    public static Videogame lookGameByTitle(String title) {
        for (Videogame game : inventory) {
            if (game.getTitle().equalsIgnoreCase(title)) {
                return game;
            }
        }
        return null;
    }
    // Look sales by customer
    public static List<Sale> lookSalesByClient(List<Customer> customers) {
        List<Sale> foundSales = new ArrayList<>();
        for (Customer customer : customers) {
            foundSales.addAll(customer.getSales());
        }
        return foundSales;
    }
    // Look sales by date
    public static List<Sale> lookSalesByDate(LocalDate date) {
        List<Sale> foundSales = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getDate().equals(date)) {
                foundSales.add(sale);
            }
        }
        return foundSales;
    }
    // Look sales by id
        public static Sale lookSalesById(int id) {
        for (Sale sale : sales) {
            if (sale.getId() == id) {
                return sale;
            }
        }
        return null;
    }
    // Look customer by id (partial match)
    public static List<Customer> lookCustomerById(String id) {
        List<Customer> foundCustomers = new ArrayList<>();
        id = id.toLowerCase();
        for (Customer customer : customers) {
            if (Integer.toString(customer.getId()).toLowerCase().contains(id)) {
                foundCustomers.add(customer);
            }
        }
        return foundCustomers;
    }
    // Look customer by name (partial match)
    public static List<Customer> lookCustomersByName(String name) {
        List<Customer> foundCustomers = new ArrayList<>();
        name = name.toLowerCase();
        for (Customer customer : customers) {
            if (customer.getName().toLowerCase().contains(name)) {
                foundCustomers.add(customer);
            }
        }
        return foundCustomers;
    }
    // Look customer by exact name
    public static Customer lookCustomerByName(String name) {
        name = name.toLowerCase();
        for (Customer customer : customers) {
            if (customer.getName().toLowerCase().contains(name)) {
                return customer;
            }
        }
        return null;
    }
}
