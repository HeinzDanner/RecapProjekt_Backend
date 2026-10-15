package de.heinzdanner.recapprojekt_backend.todo;

public record TodoDto(
        String description,
        TodoStatus status
) {
}
