package com.scrat.zhuhaibus.data.modle;

public class Feedback {
    private String contact;
    private String content;

    public String getContact() {
        return contact;
    }

    public Feedback setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Feedback setContent(String content) {
        this.content = content;
        return this;
    }
}
