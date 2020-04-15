package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Locale;
import java.util.ResourceBundle;

@FacesValidator("nameValidator")
public class NameValidator implements Validator{

    ResourceBundle bundle = ResourceBundle.getBundle("resource", Locale.getDefault());

    @Override
    public void validate(FacesContext context, UIComponent component, Object obj) throws ValidatorException {
        String name = obj.toString();

        if (name.length() < 2 || name.length() > 50) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("validation.name.size"), null));
        }

        if (!name.matches("^[a-zA-Z]+$")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("validation.name.pattern"), null));
        }

    }
}