package csc1035.project3;
/**
 * @author Josh Davison
 * class for the Transaction table
 */

import javax.persistence.*;
import java.util.List;

@Entity(name = "Transaction")
public class Transaction {

    @Id
    @Column
    private int tid;

    @Column
    private float totalPrice;

    @Column
    private float moneyGiven;

    @Column
    private float changeGiven;

    @OneToMany(mappedBy = "transactionID")
    private List<ItemSale> items;

    Transaction(float totalPrice, float moneyGiven, float changeGiven) {
        this.tid = ID.returnNewId();
        this.totalPrice = totalPrice;
        this.moneyGiven = moneyGiven;
        this.changeGiven = changeGiven;
    }

    int getTid(){
        return this.tid;
    }

    public float getTotalPrice() {return  this.totalPrice;}

    public float getMoneyGiven() {return  this.moneyGiven;}

    public float getChangeGiven() {return  this.changeGiven;}

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setMoneyGiven(float moneyGiven) {
        this.moneyGiven = moneyGiven;
    }

    public void setChangeGiven(float changeGiven) {
        this.changeGiven = changeGiven;
    }


    public List<ItemSale> getItems() {
        return items;
    }

    public void setItems(List<ItemSale> items) {
        this.items = items;
    }

    public Transaction() {
        this.tid = ID.returnNewId();
    }
}
