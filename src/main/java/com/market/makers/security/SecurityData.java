package com.market.makers.security;

import java.util.Date;

public class SecurityData {

    private long securityId;
    private String securityName;
    private ProductType productType;
    private double strikePrice;
    private Date maturityDate;
    private String isinCode;
    private String bbCode;
    private String ricCode;
    private String securityDetails;
    private SecurityType securityType;
    private double adv;

    public SecurityData() {
    }

    public SecurityData(long securityId, String securityName, ProductType productType, double strikePrice, Date maturityDate, String isinCode, String bbCode, String ricCode, String securityDetails, SecurityType securityType, double adv) {
        this.securityId = securityId;
        this.securityName = securityName;
        this.productType = productType;
        this.strikePrice = strikePrice;
        this.maturityDate = maturityDate;
        this.isinCode = isinCode;
        this.bbCode = bbCode;
        this.ricCode = ricCode;
        this.securityDetails = securityDetails;
        this.securityType = securityType;
        this.adv = adv;
    }

    public SecurityData(double adv) {
        this.adv = adv;
    }

    public long getSecurityId() {
        return securityId;
    }

    public void setSecurityId(long securityId) {
        this.securityId = securityId;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getIsinCode() {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    public String getBbCode() {
        return bbCode;
    }

    public void setBbCode(String bbCode) {
        this.bbCode = bbCode;
    }

    public String getRicCode() {
        return ricCode;
    }

    public void setRicCode(String ricCode) {
        this.ricCode = ricCode;
    }

    public String getSecurityDetails() {
        return securityDetails;
    }

    public void setSecurityDetails(String securityDetails) {
        this.securityDetails = securityDetails;
    }

    public SecurityType getSecurityType() {
        return securityType;
    }

    public void setSecurityType(SecurityType securityType) {
        this.securityType = securityType;
    }

    public double getAdv() {
        return adv;
    }

    public void setAdv(double adv) {
        this.adv = adv;
    }
}
