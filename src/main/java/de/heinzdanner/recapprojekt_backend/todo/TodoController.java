package de.heinzdanner.recapprojekt_backend.todo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo addTodo(@RequestBody TodoDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public Todo getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Todo update(@PathVariable String id, @RequestBody TodoDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}