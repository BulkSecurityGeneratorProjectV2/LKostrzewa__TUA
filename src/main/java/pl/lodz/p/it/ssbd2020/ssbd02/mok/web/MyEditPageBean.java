package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.EditUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@ViewScoped
public class MyEditPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;

    @Inject
    private FacesContext facesContext;

    private EditUserDto editUserDto;
    private Long userId;

    ResourceBundle language;

    public EditUserDto getEditUserDto() {
        return editUserDto;
    }

    public void setEditUserDto(EditUserDto editUserDto) {
        this.editUserDto = editUserDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ResourceBundle getLanguage() {
        return language;
    }

    public void setLanguage(ResourceBundle language) {
        this.language = language;
    }

    public void init() {
        this.editUserDto = userEndpoint.getEditUserDtoById(userId);
        language = ResourceBundle.getBundle("resource", getHttpRequestFromFacesContext().getLocale());
    }

    public String editUser() {
        try {
            userEndpoint.editUser(editUserDto, userId);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "account.xhtml?faces-redirect=true?includeViewParams=true";
    }

    public void displayMessage() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
        String msg = resourceBundle.getString("users.editInfo");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    private void displayError(String message) {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));

    }

    private HttpServletRequest getHttpRequestFromFacesContext() {
        return (HttpServletRequest) facesContext
                .getExternalContext()
                .getRequest();
    }
}
