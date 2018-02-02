package com.example.jpoulin.jcpoulin_subbook;

import java.util.Date;

/**
 * Created by jpoulin on 2018-02-01.
 */

public abstract class Subscription  {
    private String name;
    private Date date;
    private Float amount;
    private String comment;

    Subscription() {

    }


    public Subscription(String name, Date date, Float amount, String comment) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
    }

    public Date getDate() { return date; }

    public String getName() { return name; }

    public Float getAmount() { return amount; }

    public String getComment() { return comment; }

    public void setName(String name) throws NameTooLongException {
        if (name.length() > 20) {
            // throw an error
            throw new NameTooLongException();
        }

        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setComment(String comment) throws CommentTooLongException {
        if (comment.length() > 30) {
            // throw an error
            throw new CommentTooLongException();
        }
        this.comment = comment;
    }
}
