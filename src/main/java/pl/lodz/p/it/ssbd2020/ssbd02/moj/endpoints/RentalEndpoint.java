package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.*;

import java.util.List;

public interface RentalEndpoint {

    /**
     * Metoda, służy do dodania nowego wypożyczenia
     *
     * @param addRentalDto obiekt DTO z danymi nowego wypożyczenia.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void addRental(AddRentalDto addRentalDto) throws AppBaseException;

    /**
     * Metoda, która zwraca wszystkie wypożyczenia
     *
     * @return lista wypożyczeń użytkownika o podanym loginie
     */
    List<ListAllRentalsDto> getAllRentals();

    /**
     * Metoda, która zwraca listę wypożyczeń danego klienta.
     *
     * @param userLogin login użytkownika
     * @return lista wypożyczeń użytkownika o podanym loginie
     */
    List<ListRentalsDto> getRentals(String userLogin);

    /**
     * Metoda, która zwraca wszystkie wypożyczenia na dany jacht.
     *
     * @param yachtName nazwa yachtu
     * @return lista wypożyczeń użytkownika o podanym loginie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    List<ListAllRentalsDto> getRentalsByYacht(String yachtName) throws AppBaseException;

    //TODO to chyba do usunięcia
    EditRentalDto getRentalById(Long rentalId) throws AppBaseException;
    //TODO to też
    void editRental(EditRentalDto editRentalDto) throws AppBaseException;

    /**
     * Metoda, która anuluje wypożyczenie
     *
     * @param rentalId identfikator wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void cancelRental(Long rentalId) throws AppBaseException;

    /**
     * Metoda, która pobiera szczegóły wypożyczenia o podanym ID
     *
     * @param rentalId identfikator wypożyczenia
     * @return obiekt Dto ze szczegółami
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    MyRentalDetailsDto getUserRentalDetails(Long rentalId) throws AppBaseException;
}
