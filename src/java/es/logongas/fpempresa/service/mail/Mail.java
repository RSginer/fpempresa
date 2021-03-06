/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.logongas.fpempresa.service.mail;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GnommoStudios
 */
public class Mail {

    private List<String> to;

    private String from;

    private String subject;

    private String htmlBody;

    private String textBody;

    public Mail() {
        this.to = new ArrayList();
    }

    public Mail(List<String> to, String from, String subject, String htmlBody, String textBody) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.htmlBody = htmlBody;
        this.textBody = textBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public void addTo(String to) {
        this.to.add(to);
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
