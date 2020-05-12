package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;


import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.*;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserEndpoint implements Serializable {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Inject
    private UserManager userManager;
    private User userEditEntity;

    // tutaj permitAll
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
    /**
     * Metoda, służy do dodawania nowych użytkowników do bazy danych przez administratora
     *
     * @param userDTO obiekt DTO z danymi nowego użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addNewUser")
    public void addNewUser(AddUserDto userDTO) throws AppBaseException {
        User user = new User(userDTO.getLogin(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber());
        userManager.addNewUser(user);
    }

    // może zmienić nazwe na getReport
    public List<UserReportDto> getAllUserReportDto() {
        return ObjectMapperUtils.mapAll(userManager.getAll(), UserReportDto.class);
    }

    // po co pobierać DTO do zmiany hasła z bazy? do wywaleni xd
    public ChangePasswordDto getChangePasswordDtoById(Long id) throws AppBaseException {
        return ObjectMapperUtils.map(userManager.getUserById(id), ChangePasswordDto.class);
    }

    public EditUserDto getEditUserDtoById(Long userId) throws AppBaseException{
        this.userEditEntity = userManager.getUserById(userId);
        return ObjectMapperUtils.map(this.userEditEntity, EditUserDto.class);
    }

    public EditUserDto getEditUserDtoByLogin(String userLogin) throws AppBaseException {
        this.userEditEntity = userManager.getUserByLogin(userLogin);
        return ObjectMapperUtils.map(this.userEditEntity, EditUserDto.class);
    }

    public UserDetailsDto getUserDetailsDtoById(Long userId) throws AppBaseException{
        return ObjectMapperUtils.map(userManager.getUserById(userId), UserDetailsDto.class);
    }

    /**
     * Metoda, która zwraca login dto o podanym loginie.
     *
     * @param userLogin login użytkownika.
     * @return user login dot
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getLoginDtoByLogin")
    public UserLoginDto getLoginDtoByLogin(String userLogin) throws AppBaseException {
        return ObjectMapperUtils.map(userManager.getUserByLogin(userLogin), UserLoginDto.class);
    }

    public void editUser(EditUserDto editUserDto, Long userId) throws AppBaseException {
        // tutaj jakis wyjątek jak if nie jest spełniony
        if (userEditEntity.getId().equals(userId)) {
            userEditEntity.setFirstName(editUserDto.getFirstName());
            userEditEntity.setLastName(editUserDto.getLastName());
            userEditEntity.setPhoneNumber(editUserDto.getPhoneNumber());
            userManager.editUser(this.userEditEntity, userId);
        }
    }

    public void editOwnData(EditUserDto editUserDto, String userLogin) throws AppBaseException {
        if (userEditEntity.getLogin().equals(userLogin)) {
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

    /**
     * Metoda, która blokuje konto o podanym id.
     *
     * @param userId id użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("lockAccount")
    public void lockAccount(Long userId) throws AppBaseException {
        userManager.lockAccount(userId);
    }

    public void unlockAccount(Long userId) throws AppBaseException {
        userManager.unlockAccount(userId);
    }

    //TODO wyrzucić jeżeli okaże się niepotrzebna na pewno
    public UserDetailsDto getOwnDetailsDtoByLogin(String userLogin) throws AppBaseException {
        return ObjectMapperUtils.map(userManager.getUserByLogin(userLogin), UserDetailsDto.class);
    }

    // permit all??, zmienic nazwe na activeAccount
    public void confirmActivationCode(String code) throws AppBaseException {
        userManager.confirmActivationCode(code);
    }

    /**
     * Metoda, która zapisuje informacje o poprawnym uwierzytelnianiu( adres ip użytkownika, data logowania).
     *
     * @param login           login użytkownika
     * @param clientIpAddress adres ip użytkownika
     * @param date            data zalogowania użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("saveSuccessAuthenticate")
    public void saveSuccessAuthenticate(String login, String clientIpAddress, Date date) throws AppBaseException {
        userManager.saveSuccessAuthenticate(login, clientIpAddress, date);
    }

    /**
     * Metoda, która zapisuje informacje o niepoprawnym uwierzytelnianiu( adres ip użytkownika, data logowania).
     *
     * @param login login użytkownika
     * @param date  data zalogowania użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void saveFailureAuthenticate(String login, Date date) throws AppBaseException {
        userManager.saveFailureAuthenticate(login, date);
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
        userManager.resetPassword(resetPasswordCode, password);
    }
}
