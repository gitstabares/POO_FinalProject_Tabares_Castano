package data;

import domain.Customer;
import domain.Sale;
import domain.Store;
import domain.Videogame;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class StorageManager {

    private static final String CUSTOMER_FILE = "data/customers.csv";
    private static final String SALE_FILE = "data/sales.csv";
    private static final String VIDEOGAME_FILE = "data/videogames.csv";

    // ------------------- SAVE -------------------

    public static void saveStore() {
        saveCustomers(Store.getCustomers());
        saveSales(Store.getSales());
        saveVideogames(Store.getVideoGames());
    }

    private static void saveCustomers(List<Customer> customers) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CUSTOMER_FILE))) {
            for (Customer c : customers) {
                pw.println(encodeCustomer(c));
            }
        } catch (IOException e) {
            System.err.println("Error guardando customers: " + e.getMessage());
        }
    }

    private static void saveSales(List<Sale> sales) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SALE_FILE))) {
            for (Sale s : sales) {
                pw.println(encodeSale(s));
            }
        } catch (IOException e) {
            System.err.println("Error guardando sales: " + e.getMessage());
        }
    }

    private static void saveVideogames(List<Videogame> videogames) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(VIDEOGAME_FILE))) {
            for (Videogame v : videogames) {
                pw.println(encodeVideogame(v));
            }
        } catch (IOException e) {
            System.err.println("Error guardando videogames: " + e.getMessage());
        }
    }

    // ------------------- LOAD -------------------

    public static void loadStore() {
        List<Videogame> videogames = loadVideogames();
        for (Videogame v : videogames) {
            Store.addGame(v);
        }
        List<Sale> sales = loadSales();
        for (Sale s : sales) {
            Store.addSale(s);
        }
        List<Customer> customers = loadCustomers();
        for (Customer c : customers) {
            Store.addCustomer(c);
        }
    }


    private static List<Videogame> loadVideogames() {
        List<Videogame> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(VIDEOGAME_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(decodeVideogame(line));
            }
        } catch (IOException e) {
            System.err.println("Error cargando videogames: " + e.getMessage());
        }

        return list;
    }


    private static List<Sale> loadSales() {
        List<Sale> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(SALE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(decodeSale(line));
            }
        } catch (IOException e) {
            System.err.println("Error cargando sales: " + e.getMessage());
        }

        return list;
    }

    private static List<Customer> loadCustomers() {
        List<Customer> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(decodeCustomer(line));
            }
        } catch (IOException e) {
            System.err.println("Error cargando customers: " + e.getMessage());
        }

        return list;
    }

    // ------------------- ENCODE -------------------

    private static String encodeCustomer(Customer c) {
        return String.join(";",
                String.valueOf(c.getId()),
                c.getName(),
                String.valueOf(c.getLibrary()),
                String.valueOf(c.getSales())
        );
    }

    private static String encodeSale(Sale s) {
        return String.join(";",
                String.valueOf(s.getId()),
                String.valueOf(s.getCost()),
                s.getDate().toString(),
                String.valueOf(s.getPurchase())
        );
    }

    private static String encodeVideogame(Videogame v) {
        return String.join(";",
                v.getTitle(),
                v.getGenre(),
                String.valueOf(v.getScore()),
                String.valueOf(v.getPrice())
        );
    }

    // ------------------- DECODE -------------------

    private static Videogame decodeVideogame(String csv) {
        String[] p = csv.split(";");
        String title = p[0];
        String genre = p[1];
        int score = Integer.parseInt(p[2]);
        float price = Float.parseFloat(p[3]);
        return new Videogame(title, genre, score, price);
    }

     

    private static Sale decodeSale(String csv) {
        String[] p = csv.split(";");
        int id = Integer.parseInt(p[0]);
        float cost = Float.parseFloat(p[1]);
        LocalDate date = LocalDate.parse(p[2]);
        String rawListVideogame = p[3];
        rawListVideogame = rawListVideogame.substring(1, rawListVideogame.length()-1);

        List<Videogame> library = new ArrayList<>();
        if (!rawListVideogame.isEmpty()) {
            for (String item : rawListVideogame.split(",")) {
                library.add(Store.lookGameByTitle(item.trim()));
            }
        }
        return new Sale(library, id, date, cost);
    }

    private static Customer decodeCustomer(String csv) {
        String[] p = csv.split(";");
        int id = Integer.parseInt(p[0]);
        String name = p[1];
        String rawListVideogame = p[2];
        rawListVideogame = rawListVideogame.substring(1, rawListVideogame.length()-1);

        List<Videogame> library = new ArrayList<>();
        if (!rawListVideogame.isEmpty()) {
            for (String item : rawListVideogame.split(",")) {
                library.add(Store.lookGameByTitle(item.trim()));
            }
        }
        String rawListSale = p[3];               
        rawListSale = rawListSale.substring(1, rawListSale.length()-1);  

        List<Sale> sales = new ArrayList<>();
        if (!rawListSale.isEmpty()) {
            for (String item : rawListSale.split(",")) {
                sales.add(Store.lookSalesById(Integer.parseInt(item.trim())));
            }
        }
        return new Customer(name, id, library, sales);
    }
    
}
