package com.vaadin.tutorial.addressbook.backend;

import java.time.LocalDate;  // Added the missing import
import java.util.*;
import java.util.stream.Collectors;

public class ContactService {

    private static ContactService instance;
    private final Map<Long, Contact> contacts = new HashMap<>();
    private long nextId = 1;

    public static ContactService getInstance() {
        if (instance == null) {
            instance = new ContactService();
            instance.generateDemoContacts();
        }
        return instance;
    }

    private void generateDemoContacts() {
        Random random = new Random();
        List<String> firstNames = List.of("Peter", "Alice", "John", "Olivia", "Rita");
        List<String> lastNames = List.of("Smith", "Johnson", "Brown", "Taylor", "White");

        for (int i = 0; i < 20; i++) {
            Contact contact = new Contact();
            contact.setFirstName(firstNames.get(random.nextInt(firstNames.size())));
            contact.setLastName(lastNames.get(random.nextInt(lastNames.size())));
            contact.setEmail(contact.getFirstName().toLowerCase() + "@" + contact.getLastName().toLowerCase() + ".com");
            contact.setPhone("+358 555 " + (100 + random.nextInt(900)));
            contact.setBirthDate(LocalDate.now().minusYears(20 + random.nextInt(30)));  // Updated to use LocalDate
            save(contact);
        }
    }

    public List<Contact> findAll(String filter) {
        return contacts.values().stream()
                .filter(contact -> filter == null || contact.toString().toLowerCase().contains(filter.toLowerCase()))
                .sorted(Comparator.comparingLong(Contact::getId).reversed())
                .collect(Collectors.toList());
    }

    public void save(Contact contact) {
        if (contact.getId() == null) {
            contact.setId(nextId++);
        }
        contacts.put(contact.getId(), contact);
    }

    public void delete(Contact contact) {
        contacts.remove(contact.getId());
    }
}


