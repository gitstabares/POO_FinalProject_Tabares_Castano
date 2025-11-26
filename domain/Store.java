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
    public static Videogame lookGameByTitle(String title) {
        for (Videogame game : inventory) {
            if (game.getTitle().equalsIgnoreCase(title)) {
                return game;
            }
        }
        return null;
    }
    public static List<Sale> lookSalesByClient(Customer customer) {
        return customer.getSales();
    }
    public static List<Sale> lookSalesByDate(LocalDate date) {
        List<Sale> foundSales = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getDate().equals(date)) {
                foundSales.add(sale);
            }
        }
        return foundSales;
    }
        public static Sale lookSalesById(int id) {
        for (Sale sale : sales) {
            if (sale.getId() == id) {
                return sale;
            }
        }
        return null;
    }
}
