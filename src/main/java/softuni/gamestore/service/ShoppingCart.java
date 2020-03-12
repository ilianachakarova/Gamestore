package softuni.gamestore.service;

import softuni.gamestore.domain.dtos.UserAddGameDto;

public interface ShoppingCart {
    String addItem(String gameTitle, UserAddGameDto userAddGameDto);
    String removeItem(String gameTitle);
    String BuyItem();
}
