package com.vaadin.tutorial.addressbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AddressBookApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class, args);
    }
}



