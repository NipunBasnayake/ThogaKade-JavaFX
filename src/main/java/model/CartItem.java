package model;

import lombok.Data;

@Data

public class CartItem {
    private String itemCode;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;

    public CartItem(String itemCode, String description, int qty, double unitPrice) {
        this.itemCode = itemCode;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = qty * unitPrice;
    }

    public CartItem() {}
}
