package com.example.jpoulin.jcpoulin_subbook;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jpoulin on 2018-02-01.
 *
 * This class serves as a template/holder for all subscription objects in the project
 *
 */

public class Subscription  {
    private String name;
    private String date;
    private Float amount;
    private String comment;

    Subscription() {

    }


    public Subscription(String name, String date, Float amount, String comment) {
        // allows for external access of variables
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.comment = comment;
    }

    // The following are all getters and setters for the values contained in a subscription
    public String getDate() {
        return date;
    }

    public String getName() { return name; }

    public Float getAmount() {
        return amount;
    }

    public String getComment() { return comment; }

    public void setName(String name) throws NameTooLongException, FieldTooShortException {
        if (name.length() > 20) {
            // throw an error if name is too long
            throw new NameTooLongException();
        } else if (name.length() == 0) {
            // mandatory field, must be filled in
            // throws exception if empty
            throw new FieldTooShortException();
        }

        this.name = name;
    }

    public void setDate(String date) throws FieldTooShortException {
        // mandatory field, must be filled in
        // throws exception if empty
        if (date == null) {
            throw new FieldTooShortException();
        }
        this.date = date;
    }

    public void setAmount(Float amount) throws FieldTooShortException {
        if (amount == null) {
            // mandatory field, must be filled in
            // throws exception if empty
            throw new FieldTooShortException();
        }
        this.amount = amount;
    }

    public void setComment(String comment) throws CommentTooLongException {
        if (comment.length() > 30) {
            // throw an error if comment is too long
            throw new CommentTooLongException();
        }
        this.comment = comment;
    }
}
