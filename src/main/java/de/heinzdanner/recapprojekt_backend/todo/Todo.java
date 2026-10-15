package de.heinzdanner.recapprojekt_backend.todo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("todos")
public record Todo(
        @Id
        String id,
        String description,
        TodoStatus status
) {
}
