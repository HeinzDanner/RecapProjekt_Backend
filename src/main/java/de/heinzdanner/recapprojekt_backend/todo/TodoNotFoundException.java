package de.heinzdanner.recapprojekt_backend.todo;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(String id) {
        super("Todo with id " + id + " not found");
    }
}
