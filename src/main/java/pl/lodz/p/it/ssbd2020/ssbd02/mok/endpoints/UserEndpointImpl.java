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
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Stateful
@Interceptors(LoggerInterceptor.class)
public class UserEndpointImpl implements Serializable, UserEndpoint {
    @Inject
    private UserManager userManager;
    private User userEditEntity;

    /**
     * Metoda służąca do rejestracji użytkownika
     * @param userDTO obiekt DTO z danymi rejestrowanego użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @PermitAll
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

    /**
     * Metoda, która pobiera z bazy listę obiektów.
     *
     * @return lista obiektów
     */
    @RolesAllowed("getUserReport")
    public List<UserReportDto> getUserReport() {
        return ObjectMapperUtils.mapAll(userManager.getAll(), UserReportDto.class);
    }

    /**
     * Metoda, która pobiera użytkownika do edycji przez administratora po identyfikatorze użytkownika
     *
     * @param userId identyfikator użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getEditUserDtoById")
    public EditUserDto getEditUserDtoById(Long userId) throws AppBaseException{
        this.userEditEntity = userManager.getUserById(userId);
        return ObjectMapperUtils.map(this.userEditEntity, EditUserDto.class);
    }

    /**
     * Metoda, która pobiera użytkownika do edycji własnych danych osobowych
     *
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getEditUserDtoByLogin")
    public EditUserDto getEditUserDtoByLogin() throws AppBaseException {
        this.userEditEntity = userManager.getUserByLogin();
        return ObjectMapperUtils.map(this.userEditEntity, EditUserDto.class);
    }

    //TODO wyrzucić jeżeli okaże się niepotrzebna na pewno
    public UserDetailsDto getUserDetailsDtoById(Long userId) throws AppBaseException{
        return ObjectMapperUtils.map(userManager.getUserById(userId), UserDetailsDto.class);
    }

    /**
     * Metoda, która zwraca login dto o aktualnie zalogowanego użytkownika
     *
     * @return user login dot
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getLoginDtoByLogin")
    public UserLoginDto getLoginDtoByLogin() throws AppBaseException {
        return ObjectMapperUtils.map(userManager.getUserByLogin(), UserLoginDto.class);
    }

    /**
     * Metoda, która zapisuje wprowadzone przez administratora zmiany w danych konta użytkownika
     *
     * @param editUserDto  obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editUser")
    public void editUser(EditUserDto editUserDto) throws AppBaseException {
        userEditEntity.setFirstName(editUserDto.getFirstName());
        userEditEntity.setLastName(editUserDto.getLastName());
        userEditEntity.setPhoneNumber(editUserDto.getPhoneNumber());
        userManager.editUser(this.userEditEntity);
    }

    /**
     * Metoda, która zapisuje wprowadzone zmiany w danych swojego konta
     *
     * @param editUserDto  obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editOwnData")
    public void editOwnData(EditUserDto editUserDto) throws AppBaseException {
        userEditEntity.setFirstName(editUserDto.getFirstName());
        userEditEntity.setLastName(editUserDto.getLastName());
        userEditEntity.setPhoneNumber(editUserDto.getPhoneNumber());
        userManager.editUser(this.userEditEntity);
    }

    /**
     * Metoda wykorzystywana do zmiany hasła innego użytkownika zgodnie z przekazanymi parametrami.
     *
     * @param changePasswordDto obiekt przechowujący dane wprowadzone w formularzu
     * @param userId            id użytkownika, którego hasło ulegnie modyfikacji
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("changeUserPassword")
    public void changeUserPassword(ChangePasswordDto changePasswordDto, Long userId) throws AppBaseException {
        User user = ObjectMapperUtils.map(changePasswordDto, User.class);
        userManager.changeUserPassword(user, userId);
    }

    /**
     * Metoda wykorzystywana do zmiany własnego hasła zgodnie z przekazanymi parametrami.
     *
     * @param changeOwnPasswordDto obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("changeOwnPassword")
    public void changeOwnPassword(ChangeOwnPasswordDto changeOwnPasswordDto) throws AppBaseException {
        User user = ObjectMapperUtils.map(changeOwnPasswordDto, User.class);
        userManager.changeOwnPassword(user, changeOwnPasswordDto.getOldPassword());
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

    /**
     * Metoda, która odblokowywuje konto o podanym id.
     *
     * @param userId id użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("unlockAccount")
    public void unlockAccount(Long userId) throws AppBaseException {
        userManager.unlockAccount(userId);
    }

    //TODO wyrzucić jeżeli okaże się niepotrzebna na pewno
    public UserDetailsDto getOwnDetailsDtoByLogin() throws AppBaseException {
        return ObjectMapperUtils.map(userManager.getUserByLogin(), UserDetailsDto.class);
    }

    /**
     * Metoda która aktywuje dane konto po kliknięciu w link aktywacyjny
     * @param code kod aktywacyjny użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void activateAccount(String code) throws AppBaseException {
        userManager.confirmActivationCode(code);
    }

    /**
     * Metoda, która zapisuje informacje o poprawnym uwierzytelnianiu( adres ip użytkownika, data logowania).
     *
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("saveSuccessAuthenticate")
    public void saveSuccessAuthenticate() throws AppBaseException {
        userManager.saveSuccessAuthenticate();
    }

    /**
     * Metoda, która zapisuje informacje o niepoprawnym uwierzytelnianiu( adres ip użytkownika, data logowania).
     *
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void saveFailureAuthenticate() throws AppBaseException {
        userManager.saveFailureAuthenticate();
    }


    /**
     * Metoda, która pobiera z bazy liczbę filtrowanych obiektów.
     *
     * @param filters para filtrowanych pól i ich wartości
     * @return liczba obiektów poddanych filtrowaniu
     */
    @RolesAllowed("getFilteredRowCount")
    public int getFilteredRowCount(Map<String, FilterMeta> filters) {
        return userManager.getFilteredRowCount(filters);
    }

    /**
     * Metoda, która pobiera z bazy listę filtrowanych obiektów.
     *
     * @param first    numer pierwszego obiektu
     * @param pageSize rozmiar strony
     * @param filters  para filtrowanych pól i ich wartości
     * @return lista filtrowanych obiektów
     */
    @RolesAllowed("getResultList")
    public List<ListUsersDto> getResultList(int first, int pageSize, Map<String, FilterMeta> filters) {
        return ObjectMapperUtils.mapAll(userManager.getResultList(first, pageSize, filters), ListUsersDto.class);
    }

    /**
     * Metoda, która na podany email wysyła wiadomość z linkiem, pod którym można zresetować zapomniane hasło
     *
     * @param email adres email
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void sendResetPasswordEmail(String email) throws AppBaseException {
        userManager.sendResetPasswordEmail(email);
    }

    /**
     * Metoda, która zmienia zapomniane hasło
     *
     * @param resetPasswordDto  obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void resetPassword(ResetPasswordDto resetPasswordDto) throws AppBaseException {
        userManager.resetPassword(resetPasswordDto);
    }
}