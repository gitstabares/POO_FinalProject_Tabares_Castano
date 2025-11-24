package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Store {
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
    public static List<Videogame> lookGameByGenre(String genre) {
        List<Videogame> foundGames = new ArrayList<>();
        for (Videogame game : inventory) {
            if (game.getGenre().equalsIgnoreCase(genre)) {
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
}
