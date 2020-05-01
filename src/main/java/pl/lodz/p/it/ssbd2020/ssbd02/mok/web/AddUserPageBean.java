package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.AddUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions.EmailNotUniqueException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions.LoginNotUniqueException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class AddUserPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    private AddUserDto addUserDto;

    @PostConstruct
    public void init() {
        addUserDto = new AddUserDto();
    }

    public AddUserDto getAddUserDto() {
        return addUserDto;
    }

    public void setAddUserDto(AddUserDto addUserDto) {
        this.addUserDto = addUserDto;
    }

    public String addUser() {
        try {
            userEndpoint.addNewUser(addUserDto);
        }
        catch (AppBaseException e){
            FacesContext context = FacesContext.getCurrentInstance();
            String msg = ResourceBundle.getBundle("resource", context.getViewRoot().getLocale()).getString(e.getLocalizedMessage());
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail", msg));
            return "addUser.xhtml?faces-redirect=true";
        }
        return "listUsers.xhtml?faces-redirect=true";
    }
}
