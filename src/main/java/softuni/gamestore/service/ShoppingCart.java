package softuni.gamestore.service;

public interface ShoppingCart {
    String addItem(String gameTitle);
    String removeItem(String gameTitle);
    String BuyItem();
}
