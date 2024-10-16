package com.vaadin.tutorial.addressbook;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.addressbook.backend.Contact;
import com.vaadin.tutorial.addressbook.backend.ContactService;
import org.springframework.stereotype.Component;

@Component  // Mark this class as a Spring-managed component
@Route("")  // Default route for the AddressBook view
public class AddressBookView extends VerticalLayout {

    private final ContactService contactService;
    private final Grid<Contact> grid = new Grid<>(Contact.class);
    private final TextField filterText = new TextField();
    private final ContactForm contactForm;

    public AddressBookView(ContactService contactService) {
        this.contactService = contactService; 
        this.contactForm = new ContactForm(this);

        configureGrid();
        configureFilter();

        Button newContactButton = new Button("New Contact", click -> addNewContact());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, newContactButton);
        toolbar.setWidthFull();

        HorizontalLayout mainContent = new HorizontalLayout(grid, contactForm);
        mainContent.setSizeFull();
        contactForm.setVisible(false);

        add(toolbar, mainContent);
        setSizeFull();
        refreshGrid();
    }

    private void configureGrid() {
        grid.setColumns("firstName", "lastName", "email", "phone", "birthDate");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.asSingleSelect().addValueChangeListener(event -> 
            contactForm.editContact(event.getValue()));
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...");
        filterText.addValueChangeListener(event -> refreshGrid());
    }

    public void refreshGrid() {
        grid.setItems(contactService.findAll(filterText.getValue()));
    }

    private void addNewContact() {
        grid.asSingleSelect().clear();
        contactForm.editContact(new Contact());
    }
}



