package de.heinzdanner.recapprojekt_backend.todo.history;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

@Service
public class HistoryService {

    private final Deque<ChangeAction> undoStack = new ArrayDeque<>();
    private final Deque<ChangeAction> redoStack = new ArrayDeque<>();

    public void record(ChangeAction action) {
        undoStack.push(action);
        redoStack.clear();
    }

    public Optional<ChangeAction> popUndo() {
        if (undoStack.isEmpty()) return Optional.empty();
        return Optional.of(undoStack.pop());
    }

    public Optional<ChangeAction> popRedo() {
        if (redoStack.isEmpty()) return Optional.empty();
        return Optional.of(redoStack.pop());
    }

    public void pushUndo(ChangeAction action) {
        undoStack.push(action);
    }

    public void pushRedo(ChangeAction action) {
        redoStack.push(action);
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
    
}