package com.example.xpensetracker;

public class ExpenseModel {

    private String expenseId;
    private String note;
    //private String category;

    private String type;


    private String amount;

    private String date;
    //private long time;

    // private String uid;


    public ExpenseModel() {
    }


    public ExpenseModel(String expenseId, String note,  String amount, String type, String date) {
        this.expenseId = expenseId;
        this.note = note;
        this.type = type;
        this.amount = amount;
        this.date=date;
        //this.time = time;
        // this.uid = uid;
        // this.category = category;
    }

    public String getDate(){
        return date;

    }
    public void setDate(String date)
    {
        this.date=date;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount() {
        this.amount = amount;
    }


}