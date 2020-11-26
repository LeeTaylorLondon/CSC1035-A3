package testEPOS.JD;
import javax.persistence.*;

@Entity
@Table (name = "testTable1") // Table name
public class testTable1 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private int id;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private boolean perishable;

    @Column
    private int cost;

    @Column
    private int stock;

    @Column
    private int sell_price;


    testTable1(String name, String category, boolean perishable, int cost, int stock, int sell_price) {
        this.name = name;
        this.category = category;
        this.perishable = perishable;
        this.cost = cost;
        this.stock = stock;
        this.sell_price = sell_price;
    }

    public testTable1() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public boolean getPerishable() {
        return perishable;
    }

    public int getCost() {
        return cost;
    }

    public int getStock() {
        return stock;
    }

    public int getSell_price() {
        return sell_price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setSell_price(int sell_price) {
        this.sell_price = sell_price;
    }
}

