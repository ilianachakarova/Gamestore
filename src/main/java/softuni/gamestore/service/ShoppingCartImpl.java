package softuni.gamestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.gamestore.domain.entities.Game;
import softuni.gamestore.repository.GameRepository;

@Service
public class ShoppingCartImpl implements ShoppingCart {
    private final GameRepository gameRepository;
    @Autowired
    public ShoppingCartImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public String addItem(String gameTitle) {
        Game game = this.gameRepository.findGameByTitle(gameTitle);
        if(game!=null){

        }
        return null;
    }

    @Override
    public String removeItem(String gameTitle) {
        return null;
    }

    @Override
    public String BuyItem() {
        return null;
    }
}
