package softuni.gamestore.service;

import softuni.gamestore.domain.dtos.UserLoginDto;
import softuni.gamestore.domain.dtos.UserLogoutDto;
import softuni.gamestore.domain.dtos.UserRegisterDto;

public interface UserService {

    String registerUser(UserRegisterDto userRegisterDto);
    String loginUser(UserLoginDto userLoginDto);
    String logoutUser(UserLogoutDto userLogoutDto);
    boolean isAdmin(String email);


}
