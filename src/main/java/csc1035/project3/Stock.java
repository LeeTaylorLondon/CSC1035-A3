package csc1035.project3;
/**
 * @author Josh Davison
 * class for the Stock table
 */

import javax.persistence.*;
import java.util.List;

@Entity(name="Stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private int sid;

    @Column
    private String name;

    @Column
    private String category;

    @Column
    private boolean perishable;

    @Column
    private float cost;

    @Column
    private int stock;

    @Column
    private float sell_price;

    @OneToMany(mappedBy = "stockID")
        private List<ItemSale> sales;

    Stock(String name, String category, boolean perishable, float cost, int stock, float sell_price) {
        this.name = name;
        this.category = category;
        this.perishable = perishable;
        this.cost = cost;
        this.stock = stock;
        this.sell_price = sell_price;
    }

    public int getSid() {
        return sid;
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

    public float getCost() {
        return cost;
    }

    public int getStock() {
        return stock;
    }

    public float getSell_price() {
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


    public List<ItemSale> getSales() {
       return sales;
    }

    public void setSales(List<ItemSale> sales) {
        this.sales = sales;
    }

    public Stock() {

    }

}


