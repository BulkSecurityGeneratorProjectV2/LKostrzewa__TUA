package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;


import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.*;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserEndpoint implements Serializable {
    @Inject
    private UserManager userManager;

    public void registerNewUser(AddUserDto userDTO) {
        User user = new User(userDTO.getLogin(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber());
        userManager.registerNewUser(user);
    }

    public void addNewUser(AddUserDto userDTO) {
        User user = new User(userDTO.getLogin(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber());
        userManager.addNewUser(user);
    }

    public List<ListUsersDto> getAllUsers() {
        return ObjectMapperUtils.mapAll(userManager.getAll(), ListUsersDto.class);
    }

    public ChangePasswordDto getChangePasswordDtoById(Long id) {
        return ObjectMapperUtils.map(userManager.getUserById(id), ChangePasswordDto.class);
    }

    public EditUserDto getEditUserDtoById(Long userId) {
        return ObjectMapperUtils.map(userManager.getUserById(userId), EditUserDto.class);
    }

    public UserDetailsDto getUserDetailsDtoById(Long userId) {
        return ObjectMapperUtils.map(userManager.getUserById(userId), UserDetailsDto.class);
    }

    public void editUser(EditUserDto editUserDto, Long userId) {
        User user = ObjectMapperUtils.map(editUserDto, User.class);
        System.out.println(user);
        userManager.editUser(user, userId);
    }

    public void editUserPassword(ChangePasswordDto changePasswordDto, Long userId) {
        User user = ObjectMapperUtils.map(changePasswordDto, User.class);
        userManager.editUserPassword(user, userId);
    }

    public void lockAccount(UserDetailsDto userDetailsDto, Long userId) {
        User user = ObjectMapperUtils.map(userDetailsDto, User.class);
        userManager.editUser(user, userId);
    }

    public void unlockAccount(UserDetailsDto userDetailsDto, Long userId) {
        User user = ObjectMapperUtils.map(userDetailsDto, User.class);
        userManager.editUser(user, userId);
    }
}
