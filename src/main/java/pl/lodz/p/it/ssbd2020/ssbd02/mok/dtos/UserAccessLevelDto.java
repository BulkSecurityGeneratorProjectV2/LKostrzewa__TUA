package pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos;

public class UserAccessLevelDto {
    private Boolean admin = false;
    private Boolean manager = false;
    private Boolean client = false;

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getManager() {
        return manager;
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    public Boolean getClient() {
        return client;
    }

    public void setClient(Boolean client) {
        this.client = client;
    }
}