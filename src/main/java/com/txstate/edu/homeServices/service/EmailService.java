package com.txstate.edu.homeServices.service;

import org.springframework.mail.SimpleMailMessage;

public interface  EmailService {
    public void sendEmail(SimpleMailMessage email);

}
