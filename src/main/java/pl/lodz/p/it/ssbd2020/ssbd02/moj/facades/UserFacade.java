package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Klasa fasadowa powiązana z encją User
 */
@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "ssbd02mokPU")
    private EntityManager entityManager;

    public UserFacade() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<User> find(Object id) {
        return super.find(id);
    }

    @Override
    public void edit(User user) throws AppBaseException {
        super.edit(user);
    }
}
