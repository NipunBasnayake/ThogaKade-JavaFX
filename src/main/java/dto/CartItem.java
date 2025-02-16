package dto;

import lombok.Data;

@Data

public class CartItem {
    private String itemCode;
    private String description;
    private int quantity;
    private double unitPrice;
    private double total;

    public CartItem(String itemCode, String description, int quantity, double unitPrice) {
        this.itemCode = itemCode;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = quantity * unitPrice;
    }

    public CartItem() {

    }

    public boolean equals(Object obj) {
        CartItem cartItem = (CartItem) obj;
        if(this.itemCode == cartItem.getItemCode()) {
            return true;
        }
        return false;
    }

}
