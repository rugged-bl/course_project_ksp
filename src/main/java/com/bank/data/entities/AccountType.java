package com.bank.data.entities;

import javax.persistence.Embeddable;

@Embeddable
public class AccountType {
    public enum Type{
        CURRENT_ACCOUNT, ANOTHER_ACCOUNT
    }

    protected AccountType(){}

    public AccountType(String title, String creditInfo, String yieldInfo, Type yieldType, String operationsRestrictions) {
        this.title = title;
        this.creditInfo = creditInfo;
        this.yieldInfo = yieldInfo;
        this.yieldType = yieldType;
        this.operationsRestrictions = operationsRestrictions;
    }

    private String title;
    private String creditInfo;
    private String yieldInfo;
    private Type yieldType;
    private String operationsRestrictions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreditInfo() {
        return creditInfo;
    }

    public void setCreditInfo(String creditInfo) {
        this.creditInfo = creditInfo;
    }

    public String getYieldInfo() {
        return yieldInfo;
    }

    public void setYieldInfo(String yieldInfo) {
        this.yieldInfo = yieldInfo;
    }

    public Type getYieldType() {
        return yieldType;
    }

    public void setYieldType(Type yieldType) {
        this.yieldType = yieldType;
    }

    public String getOperationsRestrictions() {
        return operationsRestrictions;
    }

    public void setOperationsRestrictions(String operationsRestrictions) {
        this.operationsRestrictions = operationsRestrictions;
    }
}
