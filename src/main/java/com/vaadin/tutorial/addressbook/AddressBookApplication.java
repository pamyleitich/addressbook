package com.vaadin.tutorial.addressbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.vaadin.tutorial.addressbook"})  // Ensure package scanning
public class AddressBookApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class, args);
    }
}


