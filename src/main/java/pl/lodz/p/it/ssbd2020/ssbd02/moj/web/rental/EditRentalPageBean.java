package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.EditRentalDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class EditRentalPageBean implements Serializable {
    @Inject
    private Conversation conversation;
    @Inject
    private RentalEndpoint rentalEndpoint;
    private EditRentalDto editRentalDto;

    public EditRentalDto getEditRentalDto() {
        return editRentalDto;
    }

    public void setEditRentalDto(EditRentalDto editRentalDto) {
        this.editRentalDto = editRentalDto;
    }

    public String onClick(Long id) throws AppBaseException{
        conversation.begin();
        this.editRentalDto = rentalEndpoint.getRentalById(id);
        return "editRental.xhtml?faces-redirect=true";
    }

    public String onFinish() throws AppBaseException {
        rentalEndpoint.editRental(editRentalDto);
        conversation.end();
        return "listRentals.xhtml?faces-redirect=true";
    }
}
