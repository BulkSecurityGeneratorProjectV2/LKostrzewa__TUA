package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.PortDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.RentalDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.YachtModelDetailsDto;

import java.util.Collection;

/**
 * DTO jachtu wykorzystywane podczas listowania portów przypisanych do konkretnego portu.
 */
public class YachtByPortDto {
    private Long id;
    private String name;
    private Integer productionYear;
    private Float priceMultiplier;
    private String equipment;
    private Float avgRating;
    private boolean active;
    private YachtModelDetailsDto yachtModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Float getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(Float priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public YachtModelDetailsDto getYachtModel() {
        return yachtModel;
    }

    public void setYachtModel(YachtModelDetailsDto yachtModel) {
        this.yachtModel = yachtModel;
    }
}
