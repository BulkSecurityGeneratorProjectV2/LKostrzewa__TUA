package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ChangeAccessLevelPageBean implements Serializable {
    @Inject
    private UserAccessLevelEndpoint userAccessLevelEndpoint;
    private UserAccessLevelDto userDto;
    private Long userId;

    public UserAccessLevelDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserAccessLevelDto userDto) {
        this.userDto = userDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void init() {
        this.userDto = userAccessLevelEndpoint.findAccessLevelById(userId);
    }

    public String changeAccessLevel() {
        userAccessLevelEndpoint.editAccessLevels(userDto, userId);
        return "userDetails.xhtml?faces-redirect=true?includeViewParams=true";
    }
}
