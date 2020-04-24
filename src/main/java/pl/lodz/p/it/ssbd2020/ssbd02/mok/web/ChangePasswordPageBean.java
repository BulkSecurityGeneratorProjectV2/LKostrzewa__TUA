package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ChangePasswordDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ChangePasswordPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    private ChangePasswordDto changePasswordDto;
    private Long userId;

    public ChangePasswordDto getChangePasswordDto() {
        return changePasswordDto;
    }

    public void setChangePasswordDto(ChangePasswordDto changePasswordDto) {
        this.changePasswordDto = changePasswordDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void init() {
        this.changePasswordDto = userEndpoint.getChangePasswordDtoById(userId);
    }

    public void displayMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Message"));
    }

    public String changePassword() {
        userEndpoint.editUserPassword(changePasswordDto, userId);
        displayMessage();
        return "userDetails.xhtml?faces-redirect=true?includeViewParams=true";
    }
}
