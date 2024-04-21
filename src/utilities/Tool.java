package utilities;

public class Tool {

    public Tool() {
    }

    private int id;
    private String name;
    public int quantity;

    public Tool(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Tool(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

}