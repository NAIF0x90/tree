package com.tree.entity;

public class Statement {

    private String id;
    private String accountId;
    private String dateField;
    private String amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDateField() {
        return dateField;
    }

    public void setDateField(String dateField) {
        this.dateField = dateField;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
