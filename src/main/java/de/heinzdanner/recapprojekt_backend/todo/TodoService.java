package de.heinzdanner.recapprojekt_backend.todo;

import de.heinzdanner.recapprojekt_backend.todo.history.ActionType;
import de.heinzdanner.recapprojekt_backend.todo.history.ChangeAction;
import de.heinzdanner.recapprojekt_backend.todo.history.HistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repo;
    private final HistoryService historyService;

    public TodoService(TodoRepository repo, HistoryService historyService) {
        this.repo = repo;
        this.historyService = historyService;
    }

    public List<Todo> getAll() {
        return repo.findAll();
    }

    public Todo create(TodoDto dto) {
        Todo todo = new Todo(null, dto.description(), dto.status());
        Todo saved = repo.save(todo);

        historyService.record(new ChangeAction(
                ActionType.CREATE,
                null,
                saved
        ));

        return saved;
    }

    public Todo getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    public Todo update(String id, TodoDto dto) {
        Todo before = repo.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        Todo after = new Todo(id, dto.description(), dto.status());
        Todo saved = repo.save(after);

        historyService.record(new ChangeAction(
                ActionType.UPDATE,
                before,
                saved
        ));

        return saved;
    }

    public void delete(String id) {
        Todo before = repo.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        repo.deleteById(id);

        historyService.record(new ChangeAction(
                ActionType.DELETE,
                before,
                null
        ));
    }
}