package softuni.gamestore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.gamestore.domain.dtos.UserLoginDto;
import softuni.gamestore.domain.dtos.UserLogoutDto;
import softuni.gamestore.domain.dtos.UserRegisterDto;
import softuni.gamestore.domain.entities.Role;
import softuni.gamestore.domain.entities.User;
import softuni.gamestore.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {
       Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();

       StringBuilder sb = new StringBuilder();

        if(!userRegisterDto.getConfirmPassword().equals(userRegisterDto.getPassword())){
           sb.append("Passwords don't match");
        }else if(validator.validate(userRegisterDto).size()>0){
            Set<ConstraintViolation<UserRegisterDto>>test = validator.validate(userRegisterDto);
            for (ConstraintViolation<UserRegisterDto> userRegisterDtoConstraintViolation : test) {
                sb.append(userRegisterDtoConstraintViolation.getMessage()).append(System.lineSeparator());
            }
        }else{
            User entity = this.userRepository.findUserByEmail(userRegisterDto.getEmail());
            if(entity != null){
                return sb.append("User already exists").toString();
            }
             entity = this.modelMapper.map(userRegisterDto, User.class);
            entity.setRole(this.userRepository.count() == 0 ? Role.ADMIN : Role.USER);

            this.userRepository.saveAndFlush(entity);

            sb.append(String.format("%s was registered",entity.getFullName()));
        }
        return sb.toString().trim();
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {
        Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();

        StringBuilder sb = new StringBuilder();

        Set<ConstraintViolation<Class<UserLoginDto>>> violations = validator.validate(UserLoginDto.class);

        if(violations.size()>0){
            for (ConstraintViolation<Class<UserLoginDto>> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        }else {
            User entity = this.userRepository.findUserByEmail(userLoginDto.getEmail());
            if(entity == null){
              return  sb.append("User does not exist").append(System.lineSeparator()).toString();

            }else if(!entity.getPassword().equals(userLoginDto.getPassword())){
                return sb.append("Passwords don't match").append(System.lineSeparator()).toString();
            }else {
                sb.append(String.format("Successfully logged in %s", entity.getFullName()));
            }
        }
        return sb.toString().trim();
    }

    @Override
    public String logoutUser(UserLogoutDto userLogoutDto) {
        StringBuilder sb = new StringBuilder();
        User entity = this.userRepository.findUserByEmail(userLogoutDto.getEmail());

        if(entity == null){
           return sb.append("User does not exist").append(System.lineSeparator()).toString();
        }
        sb.append(String.format("User %s successfully logged out", entity.getFullName()));
        return sb.toString();
    }
    @Override
    public boolean isAdmin(String email){
        User entity = this.userRepository.findUserByEmail(email);

        if(entity != null){
            return entity.getRole().equals(Role.ADMIN);
        }
        return false;
    }
}
