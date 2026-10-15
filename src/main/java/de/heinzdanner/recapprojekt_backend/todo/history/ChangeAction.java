package de.heinzdanner.recapprojekt_backend.todo.history;

import de.heinzdanner.recapprojekt_backend.todo.Todo;

public record ChangeAction(
        ActionType type,
        Todo before,
        Todo after
) {
}