package de.heinzdanner.recapprojekt_backend.todo.history;

import de.heinzdanner.recapprojekt_backend.todo.Todo;
import de.heinzdanner.recapprojekt_backend.todo.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class UndoRedoService {

    private final HistoryService historyService;
    private final TodoRepository todoRepository;

    public UndoRedoService(HistoryService historyService, TodoRepository todoRepository) {
        this.historyService = historyService;
        this.todoRepository = todoRepository;
    }

    public boolean undo() {
        var actionOpt = historyService.popUndo();
        if (actionOpt.isEmpty()) return false;

        ChangeAction action = actionOpt.get();

        switch (action.type()) {
            case CREATE -> {
                if (action.after() != null) {
                    todoRepository.deleteById(action.after().id());
                }
            }
            case UPDATE -> {
                if (action.before() != null) {
                    todoRepository.save(action.before());
                }
            }
            case DELETE -> {
                if (action.before() != null) {
                    todoRepository.save(action.before());
                }
            }
        }

        historyService.pushRedo(action);
        return true;
    }

    public boolean redo() {
        var actionOpt = historyService.popRedo();
        if (actionOpt.isEmpty()) return false;

        ChangeAction action = actionOpt.get();

        switch (action.type()) {
            case CREATE -> {
                if (action.after() != null) {
                    todoRepository.save(action.after());
                }
            }
            case UPDATE -> {
                if (action.after() != null) {
                    todoRepository.save(action.after());
                }
            }
            case DELETE -> {
                if (action.before() != null) {
                    todoRepository.deleteById(action.before().id());
                }
            }
        }

        historyService.pushUndo(action);
        return true;
    }
}
