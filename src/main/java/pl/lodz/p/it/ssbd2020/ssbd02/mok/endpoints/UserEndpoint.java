package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;


import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.RepeatedRollBackException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.*;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;


import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserEndpoint implements Serializable {
    @Inject
    private UserManager userManager;

    private User userEditEntity;

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public void registerNewUser(AddUserDto userDTO) throws AppBaseException {
        try {
            do {
                User user = new User(userDTO.getLogin(), userDTO.getPassword(),
                        userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(),
                        userDTO.getPhoneNumber());
                userManager.registerNewUser(user);
            } while (userManager.isLastTransactionRollback());
        } catch (EJBTransactionRolledbackException ex) {
            registerNewUser(userDTO);
        }
    }

    public void addNewUser(AddUserDto userDTO) throws AppBaseException {
        User user = new User(userDTO.getLogin(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber());
        userManager.addNewUser(user);
    }

    public Integer getUserInvalidLoginAttempts(Long ID) {
        return userManager.getUserInvalidLoginAttempts(ID);
    }

    public List<ListUsersDto> getAllListUsersDto() {
        return ObjectMapperUtils.mapAll(userManager.getAll(), ListUsersDto.class);
    }

    public List<UserReportDto> getAllUserReportDto() {
        return ObjectMapperUtils.mapAll(userManager.getAll(), UserReportDto.class);
    }

    public ChangePasswordDto getChangePasswordDtoById(Long id) {
        return ObjectMapperUtils.map(userManager.getUserById(id), ChangePasswordDto.class);
    }

    public EditUserDto getEditUserDtoById(Long userId) {
        this.userEditEntity = userManager.getUserById(userId);
        return ObjectMapperUtils.map(this.userEditEntity, EditUserDto.class);
    }

    public EditUserDto getEditUserDtoByLogin(String userLogin) throws AppBaseException {
        this.userEditEntity = userManager.getUserByLogin(userLogin);
        return ObjectMapperUtils.map(this.userEditEntity, EditUserDto.class);
    }

    public UserDetailsDto getUserDetailsDtoById(Long userId) {
        return ObjectMapperUtils.map(userManager.getUserById(userId), UserDetailsDto.class);
    }

    public UserLoginDto getLoginDtoByLogin(String userLogin) throws AppBaseException {
        return ObjectMapperUtils.map(userManager.getUserByLogin(userLogin), UserLoginDto.class);
    }

    public void editUser(EditUserDto editUserDto, Long userId) throws AppBaseException {
            if(userEditEntity.getId().equals(userId)){
                userEditEntity.setFirstName(editUserDto.getFirstName());
                userEditEntity.setLastName(editUserDto.getLastName());
                userEditEntity.setPhoneNumber(editUserDto.getPhoneNumber());
                userManager.editUser(this.userEditEntity, userId);
            }
    }

    public void editOwnData(EditUserDto editUserDto, String userLogin) throws AppBaseException {
            if(userEditEntity.getLogin().equals(userLogin)){
                userEditEntity.setFirstName(editUserDto.getFirstName());
                userEditEntity.setLastName(editUserDto.getLastName());
                userEditEntity.setPhoneNumber(editUserDto.getPhoneNumber());
                userManager.editUser(this.userEditEntity, userEditEntity.getId());
            }
    }

    public void editUserPassword(ChangePasswordDto changePasswordDto, Long userId) throws AppBaseException {
        User user = ObjectMapperUtils.map(changePasswordDto, User.class);
        userManager.editUserPassword(user, userId);
    }

    public void editOwnPassword(ChangeOwnPasswordDto changeOwnPasswordDto, String userLogin) throws AppBaseException {
        User user = ObjectMapperUtils.map(changeOwnPasswordDto, User.class);
        userManager.editOwnPassword(user, userLogin, changeOwnPasswordDto.getOldPassword());
    }

    public void lockAccount(Long userId) throws AppBaseException{
        userManager.lockAccount(userId);
    }

    public void unlockAccount(Long userId) throws AppBaseException{
        userManager.unlockAccount(userId);
    }

    public UserDetailsDto getOwnDetailsDtoByLogin(String userLogin) throws AppBaseException {
        return ObjectMapperUtils.map(userManager.getUserByLogin(userLogin), UserDetailsDto.class);
    }

    public void confirmActivationCode(String code) throws AppBaseException{
        userManager.confirmActivationCode(code);
    }

    public void editUserLastLoginAndInvalidLoginAttempts(UserLoginDto userLoginDto, Long userId,Integer attempts) throws AppBaseException {
        User user = ObjectMapperUtils.map(userLoginDto, User.class);
        userManager.editUserLastLoginAndInvalidLoginAttempts(user, userId,attempts);
    }

    public int getFilteredRowCount(Map<String, FilterMeta> filters) {
        return userManager.getFilteredRowCount(filters);
    }

    public List<ListUsersDto> getResultList(int first, int pageSize, Map<String, FilterMeta> filters) {
        List<ListUsersDto> users = ObjectMapperUtils.mapAll(userManager.getResultList(first, pageSize, filters), ListUsersDto.class);
        Collections.sort(users);
        return users;
    }

    public void sendResetPasswordEmail(String email) throws AppBaseException {
        userManager.sendResetPasswordEmail(email);
    }

    public void resetPassword(String resetPasswordCode, String password) throws AppBaseException {
        userManager.resetPassword(resetPasswordCode,password);
    }
}
