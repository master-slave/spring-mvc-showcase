package org.springframework.samples.mvc.data.custom;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Test {
    @ParamName("new_kiosk")
    private String name;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public void setDate(Date date) {
        this.date = date;
    }
}