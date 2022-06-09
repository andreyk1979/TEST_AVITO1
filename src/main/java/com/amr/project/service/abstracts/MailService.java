package com.amr.project.service.abstracts;

public interface MailService {

    public void send(String emailTo, String subject, String message);
}
