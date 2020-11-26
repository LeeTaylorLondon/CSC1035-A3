package csc1035.project3;
/**
 * @author Josh Davison
 * class for the ItemSale table
 */

import javax.persistence.*;

@Entity(name = "ItemSale")
public class ItemSale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int saleid;

    @ManyToOne
    @JoinColumn(nullable = false, name = "stockID")
    private Stock stockID;

    @ManyToOne
    @JoinColumn(nullable = false, name = "transactionID")
    private Transaction transactionID;

    @Column
    private int QuantitySold;

    @Column
    private float TotalPrice;

    public ItemSale(Stock sid, int QuantitySold) {
        this.stockID = sid;
        this.QuantitySold = QuantitySold;
    }

    public int getSaleid() {
        return saleid;
    }

    public Stock getSid() {
        return stockID;
    }

    public Transaction getTid() {
        return transactionID;
    }

    public int getQuantitySold() {
        return QuantitySold;
    }

    public float getTotalPrice() {
        return TotalPrice;
    }

    public void setSid(Stock sid) {
        this.stockID = sid;
    }

    public void setTid(Transaction tid) {
        this.transactionID = tid;
    }

    public void setQuantitySold(int quantitySold) {
        QuantitySold = quantitySold;
    }

    public void setTotalPrice(float totalPrice) {
        TotalPrice = totalPrice;
    }

    public ItemSale() { }
}
