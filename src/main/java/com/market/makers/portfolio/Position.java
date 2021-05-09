package com.market.makers.portfolio;

import java.math.BigDecimal;

public class Position {

    private String ticker;
    private long qty;
    private BigDecimal investedAmount;
    private BigDecimal currentValue;
    private double avgFillPrice;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public BigDecimal getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(BigDecimal investedAmount) {
        this.investedAmount = investedAmount;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public double getAvgFillPrice() {
        return avgFillPrice;
    }

    public void setAvgFillPrice(double avgFillPrice) {
        this.avgFillPrice = avgFillPrice;
    }

    @Override
    public String toString() {
        return "Position{" +
                "ticker='" + ticker + '\'' +
                ", qty=" + qty +
                ", investedAmount=" + investedAmount +
                ", currentValue=" + currentValue +
                ", avgFillPrice=" + avgFillPrice +
                '}';
    }
}
