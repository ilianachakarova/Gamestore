package softuni.gamestore.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.gamestore.domain.dtos.*;
import softuni.gamestore.service.GameService;
import softuni.gamestore.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;
    private String loggedInUser;

    @Autowired
    public GameStoreController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String inputLine = scanner.nextLine();
        while (true) {
            if ("END".trim().equals(inputLine)) {
                return;
            }

            String[] inputParams = inputLine.split("\\|");

            switch (inputParams[0]) {
                case "RegisterUser":
                    UserRegisterDto userRegisterDto = new UserRegisterDto(inputParams[1], inputParams[2], inputParams[3], inputParams[4]);
                    System.out.println(this.userService.registerUser(userRegisterDto));
                    break;
                case "LoginUser":
                    if (this.loggedInUser == null) {
                        UserLoginDto userLoginDto = new UserLoginDto(inputParams[1], inputParams[2]);
                        String logInResult = this.userService.loginUser(userLoginDto);
                        System.out.println(logInResult);

                        if (logInResult.contains("Successfully logged in")) {
                            System.out.println(logInResult);
                            this.loggedInUser = userLoginDto.getEmail();
                        }
                    } else {
                        System.out.println("Session is taken");
                    }
                    break;
                case "Logout":
                    if (this.loggedInUser == null) {
                        System.out.println("Cannot log out user. No user logged in");
                    } else {
                        String logoutResult = this.userService.logoutUser(new UserLogoutDto((this.loggedInUser)));
                        System.out.println(logoutResult);

                        this.loggedInUser = null;
                    }
                    break;
                case "AddGame":
                    if (this.loggedInUser != null && this.userService.isAdmin(this.loggedInUser)) {
                        AddGameDto addGameDto = new AddGameDto(inputParams[1], new BigDecimal(inputParams[2]),
                                Double.parseDouble(inputParams[3]), inputParams[4], inputParams[5], inputParams[6],
                                LocalDate.parse(inputParams[7], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        this.gameService.addGame(addGameDto);
                        System.out.println("Added " + addGameDto.getTitle());
                    } else {
                        System.out.println("No user logged in");
                    }
                    break;
                case "EditGame":
                    if (this.loggedInUser != null && this.userService.isAdmin(this.loggedInUser)) {
                        this.gameService.setParameters(inputParams);
                    } else {
                        System.out.println("Only admins can edit games");
                    }
                    break;
                case"AllGame":
                    this.gameService.printTitleAndPriceAllGames();
                    break;
                case"DetailGame":
                    String gameName = inputParams[1];
                    DetailedGameDto detailedGameDto = new DetailedGameDto(gameName);
                    this.gameService.getDetailedGameInfo(detailedGameDto);
                    break;
            }
            inputLine = scanner.nextLine();
        }

    }
}
