package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht;

public class YachtListDto {
    private Long id;
    private String name;
    private Long yachtModelId;
    private Long currentPortId;
    private Float avgRating;

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

    public Long getYachtModelId() {
        return yachtModelId;
    }

    public void setYachtModelId(Long yachtModelId) {
        this.yachtModelId = yachtModelId;
    }

    public Long getCurrentPortId() {
        return currentPortId;
    }

    public void setCurrentPortId(Long currentPortId) {
        this.currentPortId = currentPortId;
    }

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }
}