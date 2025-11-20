package domain;

public class Videogame {
    // Attributes of the videogame
    private String title;
    private String genre;
    private int score;
    private float price;
    // Constructors
    public Videogame(String title, String genre, float price, int score) {
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.score = score;
        Store.addGame(this);
    }
    public Videogame(String title, String genre, float price) {
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.score = 0;
        Store.addGame(this);
    }
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    public String getGenre() {
        return genre;
    }
    public float getPrice() {
        return price;
    }
    public int getScore() {
        return score;
    }
    public void setPrice(float price) {
        if (price >= 0) this.price = price;
    }
    public void setScore(int score) {
        if (score >= 0 && score <= 5)   this.score = score;
    }
}
