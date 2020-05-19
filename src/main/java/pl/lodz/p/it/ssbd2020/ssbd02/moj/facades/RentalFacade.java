package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class RentalFacade extends AbstractFacade<Rental> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    public RentalFacade() {
        super(Rental.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Metoda, która zwraca listę wypożyczeń.
     *
     * @return lista wypożyczeń
     */
    @Override
    @RolesAllowed("getRentals")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Rental> findAll() {
        return super.findAll();
    }

    /**
     * Metoda, która służy do wyszukania obiektu po kluczu głównym.
     *
     * @param id wartość klucza głównego
     * @return optional z wyszukanym obiektem encji lub pusty, jeśli poszukiwany obiekt encji nie istnieje
     */
    @Override
    @RolesAllowed({"getRentalById", "getUserRentalDetails", "cancelRental"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<Rental> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda, która edytuje encję wypożyczenia.
     *
     * @param rental encja wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed("cancelRental")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void edit(Rental rental) throws AppBaseException {
        super.edit(rental);
    }

    @Override
    @DenyAll
    public void create(Rental entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    @DenyAll
    public void remove(Rental entity) {
        super.remove(entity);
    }
}
