package com.vaadin.tutorial.addressbook.backend;

import java.io.Serializable;
import java.time.LocalDate;

public class Contact implements Serializable, Cloneable {

    private Long id;
    private String firstName = "";
    private String lastName = "";
    private String phone = "";
    private String email = "";
    private LocalDate birthDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    @Override
    public Contact clone() throws CloneNotSupportedException {
        return (Contact) super.clone();
    }

    @Override
    public String toString() {
        return String.format("Contact{id=%d, firstName='%s', lastName='%s', phone='%s', email='%s', birthDate='%s'}", 
            id, firstName, lastName, phone, email, birthDate);
    }
}


