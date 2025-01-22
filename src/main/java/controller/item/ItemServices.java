package controller.item;

import model.Item;
import model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public interface ItemServices {
    boolean addItem(Item item);
    boolean updateItem(Item item);
    boolean deleteItem(String id);
    Item searchItem(String id);
    List<Item> getItems();
    String getLastId();
    List<String> getItemCodes();
    boolean updateSellItem(ArrayList<OrderDetail> orderDetails);
    int getQtyOnHand(String itemId);
}
