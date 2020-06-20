package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.EditOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.NewOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.OpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.OpinionManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

/**
 * Implementacja interfejsu OpinionEndpoint.
 */
@Stateful
@Interceptors(LoggerInterceptor.class)
public class OpinionEndpointImpl implements Serializable, OpinionEndpoint {
    @Inject
    private OpinionManager opinionManager;

    private Opinion opinionEditEntity;

    /**
     * Metoda, która dodaje nową opinię.
     *
     * @param newOpinionDto obiekt DTO z danymi nowej opinii.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addOpinion")
    public void addOpinion(NewOpinionDto newOpinionDto, String rentalBusinessKey) throws AppBaseException {
        Opinion opinion = new Opinion(newOpinionDto.getRating(),newOpinionDto.getComment(),null,null);
        opinionManager.addOpinion(opinion, rentalBusinessKey);
    }

    /**
     * Metoda pobierająca wszystkie opinie przypisane do danego jachtu.
     *
     * @param yachtId identyfikator jachtu
     * @return lista opini dla danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getAllOpinionsByYacht")
    public List<OpinionDto> getAllOpinionsByYacht(Long yachtId) throws AppBaseException {
        return ObjectMapperUtils.mapAll(opinionManager.getAllOpinionsByYacht(yachtId), OpinionDto.class);
    }

    /**
     * Metoda zwracająca opinię do edycji na podstawie przekazanego klucza biznesowego.
     *
     * @param rentalBusinessKey klucz biznesowy opinii
     * @return opinia do edycji
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getOpinionByBusinessKey")
    public EditOpinionDto getOpinionByRentalBusinessKey(String rentalBusinessKey) throws AppBaseException {
        this.opinionEditEntity = opinionManager.getOpinionByRentalBusinessKey(rentalBusinessKey);
        return ObjectMapperUtils.map(opinionEditEntity, EditOpinionDto.class);
    }

    /**
     * Metoda służąca do zapisu nowej wersji opinii.
     *
     * @param editOpinionDto obiekt DTO z danymi edytowanej opinii.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editOpinion")
    public void editOpinion(EditOpinionDto editOpinionDto) throws AppBaseException {
        opinionEditEntity.setRating(editOpinionDto.getRating());
        opinionEditEntity.setComment(editOpinionDto.getComment());
        opinionManager.editOpinion(opinionEditEntity);
    }
}
