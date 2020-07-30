package com.zcy.common.utils.mail;

import java.io.File;
import java.util.Date;

public class MailSenderInfo {
    private String user;
    private String password;
    private String sendMail;
    private String sendName;
    private String[] recipientMailTo;
    private String[] recipientNameTo;
    private String[] recipientMailCc;
    private String[] recipientNameCc;
    private String[] recipientMailBcc;
    private String[] recipientNameBcc;
    private String subject;
    private String content;
    private File accessory;
    private Date sendTime;

    public File getAccessory() {
        return accessory;
    }

    public void setAccessory(File accessory) {
        this.accessory = accessory;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendMail() {
        return sendMail;
    }

    public void setSendMail(String sendMail) {
        this.sendMail = sendMail;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String[] getRecipientMailTo() {
        return recipientMailTo;
    }

    public void setRecipientMailTo(String... recipientMailTo) {
        this.recipientMailTo = recipientMailTo;
    }

    public String[] getRecipientNameTo() {
        return recipientNameTo;
    }

    public void setRecipientNameTo(String... recipientNameTo) {
        this.recipientNameTo = recipientNameTo;
    }

    public String[] getRecipientMailCc() {
        return recipientMailCc;
    }

    public void setRecipientMailCc(String... recipientMailCc) {
        this.recipientMailCc = recipientMailCc;
    }

    public String[] getRecipientNameCc() {
        return recipientNameCc;
    }

    public void setRecipientNameCc(String... recipientNameCc) {
        this.recipientNameCc = recipientNameCc == null ? new String[0] : recipientNameCc;
    }

    public String[] getRecipientMailBcc() {
        return recipientMailBcc;
    }

    public void setRecipientMailBcc(String... recipientMailBcc) {
        this.recipientMailBcc = recipientMailBcc;
    }

    public String[] getRecipientNameBcc() {
        return recipientNameBcc;
    }

    public void setRecipientNameBcc(String... recipientNameBcc) {
        this.recipientNameBcc = recipientNameBcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
