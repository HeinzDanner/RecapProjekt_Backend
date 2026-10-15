package de.heinzdanner.recapprojekt_backend.todo;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    public List<Todo> getAll() {
        return repo.findAll();
    }

    public Todo create(TodoDto dto) {
        Todo todo = new Todo(null, dto.description(), dto.status());
        return repo.save(todo);
    }

    public Todo getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
    }

    public Todo update(String id, TodoDto dto) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        Todo updated = new Todo(id, dto.description(), dto.status());
        return repo.save(updated);
    }

    public void delete(String id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        repo.deleteById(id);
    }
}
