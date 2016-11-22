package com.example.sunwo.money_book;

/**
 * Created by sunwo on 2016-11-17.
 */
public class ExpenseStruct {
    public String id = "";
    public String amount = "";
    public String date = "";
    public String category = "";
    public String method = "";
    public String description = "";

    public ExpenseStruct(){};

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getAmount() {

        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }

    public ExpenseStruct(String id, String amount, String date, String category, String method, String description) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.method = method;
        this.description = description;
    }
}
