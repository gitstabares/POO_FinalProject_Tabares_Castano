import data.StorageManager;
import domain.Customer;
import domain.Sale;
import domain.Store;
import domain.Videogame;
import java.util.List;

public class App {
    public static void main(String[] args) {
       //*  SwingUtilities.invokeLater(() -> {
      //      MainWindow mainWindow = new MainWindow();*//
        //});
        Videogame game1 = new Videogame("Elden Ring", "Adventure", 59.99f,4);
        Videogame game2 = new Videogame("God of War", "Action", 49.99f,4);
        Customer customer1 = new Customer("Alice", 1);
        Customer customer2 = new Customer("Bob", 2);
        Store.addCustomer(customer1);
        Store.addCustomer(customer2);
        List<Videogame> purchase1 = List.of(game1, game2);
        Sale sale1 = new Sale(purchase1, customer1);
        List<Videogame> purchase2 = List.of(game2);
        Sale sale2 = new Sale(purchase2, customer2);
        List<Videogame> purchase3 = List.of(game1);
        Sale sale3 = new Sale(purchase3, customer2);

        StorageManager.saveStore();
    }
}