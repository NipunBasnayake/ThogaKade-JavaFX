package controller.item;

import model.Item;

import java.util.List;

public interface ItemServices {
    boolean addItem(Item item);
    boolean updateItem(Item item);
    boolean deleteItem(String id);
    Item searchItem(String id);
    List<Item> getItems();
    String getLastId();
    List<String> getItemCodes();
}
