package utilities;

public class Drug {

    public Drug() {
    }


    private int id;
    private String name;
    public int quantity;
    public String expirationDate;
    public double temperature;

    /**
     * Constructeur utilisé par le client pour créer un médicament
     */
    public Drug(String name, int quantity, String expirationDate, double temperature) {
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.temperature = temperature;
    }

    /**
     * Constructeur utilisé par la BDD pour en extraire des données
     */
    public Drug(int id, String name, int quantity, String expirationDate, double temperature) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

}