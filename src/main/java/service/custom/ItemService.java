package service.custom;

import dto.Item;
import dto.OrderDetail;
import service.SuperService;

import java.util.ArrayList;
import java.util.List;

public interface ItemService extends SuperService {
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
