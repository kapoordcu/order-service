package de.service.order.application.service;

import org.springframework.stereotype.Service;
//a simple service to print a message
@Service
public class MessageService {

    public void print(String msg){
        System.out.println(msg);
    }
}
