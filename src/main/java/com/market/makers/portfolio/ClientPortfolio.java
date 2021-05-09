package com.market.makers.portfolio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ClientPortfolio {

    private String portfolioNumber;
    private String portfolioName;
    private BigDecimal capital;
    private BigDecimal currentValue;
    private BigDecimal totalGainORLoss;
    private BigDecimal totalGainORLossPercent;
    private List<Position> positionList;
    private Date date;

    public String getPortfolioNumber() {
        return portfolioNumber;
    }

    public void setPortfolioNumber(String portfolioNumber) {
        this.portfolioNumber = portfolioNumber;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public BigDecimal getTotalGainORLoss() {
        return totalGainORLoss;
    }

    public void setTotalGainORLoss(BigDecimal totalGainORLoss) {
        this.totalGainORLoss = totalGainORLoss;
    }

    public BigDecimal getTotalGainORLossPercent() {
        return totalGainORLossPercent;
    }

    public void setTotalGainORLossPercent(BigDecimal totalGainORLossPercent) {
        this.totalGainORLossPercent = totalGainORLossPercent;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ClientPortfolio{" +
                "portfolioNumber='" + portfolioNumber + '\'' +
                ", portfolioName='" + portfolioName + '\'' +
                ", capital=" + capital +
                ", currentValue=" + currentValue +
                ", totalGainORLoss=" + totalGainORLoss +
                ", totalGainORLossPercent=" + totalGainORLossPercent +
                ", positionList=" + positionList +
                ", date=" + date +
                '}';
    }
}
