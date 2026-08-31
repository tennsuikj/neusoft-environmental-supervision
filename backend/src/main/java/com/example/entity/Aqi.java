package com.example.entity;

public class Aqi {
    private Integer aqiId;
    private String chineseExplain;
    private String aqiExplain;
    private String color;
    private String healthImpact;
    private String takeSteps;
    private Integer so2Min;
    private Integer so2Max;
    private Integer coMin;
    private Integer coMax;
    private Integer spmMin;
    private Integer spmMax;
    private String remarks;

    public Integer getAqiId() {
        return aqiId;
    }

    public void setAqiId(Integer aqiId) {
        this.aqiId = aqiId;
    }

    public String getChineseExplain() {
        return chineseExplain;
    }

    public void setChineseExplain(String chineseExplain) {
        this.chineseExplain = chineseExplain;
    }

    public String getAqiExplain() {
        return aqiExplain;
    }

    public void setAqiExplain(String aqiExplain) {
        this.aqiExplain = aqiExplain;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHealthImpact() {
        return healthImpact;
    }

    public void setHealthImpact(String healthImpact) {
        this.healthImpact = healthImpact;
    }

    public String getTakeSteps() {
        return takeSteps;
    }

    public void setTakeSteps(String takeSteps) {
        this.takeSteps = takeSteps;
    }

    public Integer getSo2Min() {
        return so2Min;
    }

    public void setSo2Min(Integer so2Min) {
        this.so2Min = so2Min;
    }

    public Integer getSo2Max() {
        return so2Max;
    }

    public void setSo2Max(Integer so2Max) {
        this.so2Max = so2Max;
    }

    public Integer getCoMin() {
        return coMin;
    }

    public void setCoMin(Integer coMin) {
        this.coMin = coMin;
    }

    public Integer getCoMax() {
        return coMax;
    }

    public void setCoMax(Integer coMax) {
        this.coMax = coMax;
    }

    public Integer getSpmMin() {
        return spmMin;
    }

    public void setSpmMin(Integer spmMin) {
        this.spmMin = spmMin;
    }

    public Integer getSpmMax() {
        return spmMax;
    }

    public void setSpmMax(Integer spmMax) {
        this.spmMax = spmMax;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}