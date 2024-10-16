package com.vaadin.tutorial.addressbook;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.tutorial.addressbook.backend.Contact;

public class ContactForm extends FormLayout {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField phone = new TextField("Phone");
    private TextField email = new TextField("Email");
    private DatePicker birthDate = new DatePicker("Birth date");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private Binder<Contact> binder = new Binder<>(Contact.class);
    private Contact contact;
    private AddressBookView parentView;

    public ContactForm(AddressBookView parentView) {
        this.parentView = parentView;
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        binder.forField(firstName)
                .asRequired("First name is required")
                .bind(Contact::getFirstName, Contact::setFirstName);

        binder.forField(lastName)
                .asRequired("Last name is required")
                .bind(Contact::getLastName, Contact::setLastName);

        binder.forField(phone)
                .asRequired("Phone number is required")
                .bind(Contact::getPhone, Contact::setPhone);

        binder.forField(email)
                .asRequired("Email is required")
                .withValidator(new EmailValidator("Invalid email address"))
                .bind(Contact::getEmail, Contact::setEmail);

        binder.forField(birthDate)
                .bind(Contact::getBirthDate, Contact::setBirthDate);

        save.addClickListener(event -> save());
        cancel.addClickListener(event -> cancel());

        setVisible(false);
    }

    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        add(firstName, lastName, phone, email, birthDate, actions);
    }

    public void editContact(Contact contact) {
        this.contact = contact;
        if (contact != null) {
            binder.readBean(contact);
            setVisible(true);
        } else {
            setVisible(false);
        }
    }

    private void save() {
        try {
            binder.writeBean(contact);
            parentView.getContactService().save(contact);  // Ensure this works
            Notification.show("Contact saved");
            parentView.refreshGrid();
            setVisible(false);
        } catch (ValidationException e) {
            Notification.show("Please fill out the form correctly");
        }
    }

    private void cancel() {
        setVisible(false);
    }
}





