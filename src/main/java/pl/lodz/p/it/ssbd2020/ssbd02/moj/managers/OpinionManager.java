package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.OpinionFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class OpinionManager {
    @Inject
    private RentalFacade rentalFacade;
    @Inject
    private OpinionFacade opinionFacade;
    @Inject
    private YachtFacade yachtFacade;

    public void addOpinion(Opinion opinion) {
        opinionFacade.create(opinion);
        calculateAvgRating(opinion.getRental().getYacht().getId());
    }

    public List<Opinion> getAllOpinionsByYacht(Long yachtId) {
        return rentalFacade.findAll().stream()
                .filter(rental -> rental.getYacht().getId().equals(yachtId))
                .map(Rental::getOpinion)
                .collect(Collectors.toList());
    }

    public Opinion getOpinionById(Long opinionId) {
        return opinionFacade.find(opinionId);
    }

    public void editOpinion(Long opinionId, Opinion opinionToEdit) {
        //opinionToEdit.setId(opinionId);
        opinionToEdit.setEdited(true);
        opinionFacade.edit(opinionToEdit);
        calculateAvgRating(opinionToEdit.getRental().getYacht().getId());
    }

    public void calculateAvgRating(Long yachtId){
        Yacht yacht = yachtFacade.find(yachtId);
        BigDecimal tmp = BigDecimal.valueOf(yacht.getRentals().stream()
                .map(Rental::getOpinion)
                .mapToDouble(Opinion::getRating)
                .average().orElse(Double.NaN));
        yacht.setAvgRating(tmp);
        yachtFacade.edit(yacht);
    }
}
