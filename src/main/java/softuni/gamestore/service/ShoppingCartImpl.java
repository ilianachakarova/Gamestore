package softuni.gamestore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.gamestore.domain.dtos.UserAddGameDto;
import softuni.gamestore.domain.entities.Game;
import softuni.gamestore.domain.entities.User;
import softuni.gamestore.repository.GameRepository;
import softuni.gamestore.repository.UserRepository;

@Service
public class ShoppingCartImpl implements ShoppingCart {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    @Autowired
    public ShoppingCartImpl(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public String addItem(String gameTitle, UserAddGameDto userAddGameDto) {
        Game game = this.gameRepository.findGameByTitle(gameTitle);
        if(game!=null){
            User user = this.userRepository.findUserByEmail(userAddGameDto.getEmail());
            this.modelMapper.map(userAddGameDto, User.class);
            user.getGames().add(game);
            userAddGameDto.setGames(user.getGames());
            this.userRepository.saveAndFlush(user);
            return game.getTitle()+ " added to cart.";
        }
        return "Adding game unsuccessful. Game does not exist";
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
