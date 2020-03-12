package softuni.gamestore.service;

import softuni.gamestore.domain.dtos.AddGameDto;
import softuni.gamestore.domain.dtos.DetailedGameDto;
import softuni.gamestore.domain.entities.Game;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface GameService {
    void addGame(AddGameDto addGameDto);
    Game validateGame(long id);
    void editGameTitle(String field, String value, Game game);
    void editGamePrice(BigDecimal price, Game game);
    void editGameTrailer(String trailer, Game game);
    void editGameImageThumbnail(String imageThumbnail, Game game);
    void editGameSize(double size, Game game);
    void editGameDescription(String description, Game game);
    void editGameReleaseDate(LocalDate releaseDate, Game game);
    void getDetailedGameInfo (DetailedGameDto detailedGameDto);
    void printTitleAndPriceAllGames();
    void setParameters(String[] inputParams);
}
