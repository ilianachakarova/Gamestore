package softuni.gamestore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.gamestore.domain.dtos.AddGameDto;
import softuni.gamestore.domain.dtos.DetailedGameDto;
import softuni.gamestore.domain.entities.Game;
import softuni.gamestore.repository.GameRepository;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public GameServiceImpl(GameRepository gameRepository, EntityManager entityManager) {
        this.gameRepository = gameRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void addGame(AddGameDto addGameDto) {
        Game entity = this.modelMapper.map(addGameDto, Game.class);
        this.gameRepository.saveAndFlush(entity);

        Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();


        Set<ConstraintViolation<Class<AddGameDto>>> violations = validator.validate(AddGameDto.class);

        if (violations.size() > 0) {
            for (ConstraintViolation<Class<AddGameDto>> violation : violations) {
                System.out.println(violation.getMessage());
            }
        } else {
            this.gameRepository.saveAndFlush(entity);
        }
    }

    @Override
    public Game validateGame(long id) {
        return this.gameRepository.findGameById(id).orElse(null);
    }

    @Override
    @Transactional
    public void editGameTitle(String field, String value, Game game) {
        int n = this.gameRepository.updateGameSetTitle(value);
        if (n > 0) {
            System.out.println("Edited " + game.getTitle());
        }
    }

    @Override
    @Transactional
    public void editGamePrice(BigDecimal price, Game game) {
        int n = this.gameRepository.updateGameSetPrice(price);
        if (n > 0) {
            System.out.println("Edited " + game.getTitle());
        }
    }

    @Override
    @Transactional
    public void editGameTrailer(String trailer, Game game) {
        if(this.gameRepository.updateGameSetTrailer(trailer)>0){
            System.out.println("Edited " + game.getTitle());
        }
    }

    @Override
    @Transactional
    public void editGameImageThumbnail(String imageThumbnail, Game game) {
        if(this.gameRepository.updateGameSetImageThumbnail(imageThumbnail)>0){
            System.out.println("Edited " + game.getTitle());
        }
    }

    @Override
    @Transactional
    public void editGameSize(double size, Game game) {
        if(this.gameRepository.updateGameSetSize(size)>0){
            System.out.println("Edited " + game.getTitle());
        }
    }

    @Override
    @Transactional
    public void editGameDescription(String description, Game game) {
        if(this.gameRepository.updateGameSetDescription(description)>0){
            System.out.println("Edited " + game.getTitle());
        }
    }

    @Override
    @Transactional
    public void editGameReleaseDate(LocalDate releaseDate, Game game) {
        if(this.gameRepository.updateGameSetReleaseDate(releaseDate)>0){
            System.out.println("Edited " + game.getTitle());
        }
    }

    @Override
    public void getDetailedGameInfo(DetailedGameDto detailedGameDto) {
        Game entity = this.gameRepository.findGameByTitle(detailedGameDto.getTitle());
        if(entity==null){
            System.out.println("This game is not present in our bookstore");
        }else{
            System.out.println(String.format("Title: %s%nPrice: %.2f%nDescription: %s%nRelease date: %s",
                    entity.getTitle(), entity.getPrice(), entity.getDescription(),entity.getReleaseDate().toString()));
        }

    }

    @Override
    public void printTitleAndPriceAllGames() {
        List<Game> games = this.gameRepository.findAll();
        games.forEach(game-> System.out.println(String.format("%s %.2f",game.getTitle(),game.getPrice())));
    }


    @Override
    @Transactional
    public void setParameters(String[] inputParams) {
        Long id = Long.parseLong(inputParams[1]);
        Game entity = this.validateGame(id);
        if (entity != null) {
            this.setNewValues(inputParams, entity);

        } else {
            System.out.println("Your game id is invalid");
        }


    }

    private void setNewValues(String[] inputParams, Game game) {
        for (int i = 2; i < inputParams.length; i++) {
            String[] tokens = inputParams[i].split("=");
            String field = tokens[0];
            String value = tokens[1];

            switch (field) {
                case "title":
                    this.editGameTitle(field, value, game);
                    break;
                case "price":
                    this.editGamePrice(new BigDecimal(value), game);
                    break;
                case "trailer":
                    this.editGameTrailer(value,game);
                    break;
                case "imageThumbnail":
                    this.editGameImageThumbnail(value, game);
                    break;
                case "size":
                    this.editGameSize(Double.parseDouble(value), game);
                    break;
                case "description":
                    this.editGameDescription(value,game);
                    break;
                case "releaseDate":
                    this.editGameReleaseDate(LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy")),game);
                    break;
            }
        }
    }




}
