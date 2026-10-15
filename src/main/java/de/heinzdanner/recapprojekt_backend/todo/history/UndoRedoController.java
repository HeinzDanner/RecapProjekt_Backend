package de.heinzdanner.recapprojekt_backend.todo.history;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class UndoRedoController {

    private final UndoRedoService undoRedoService;

    public UndoRedoController(UndoRedoService undoRedoService) {
        this.undoRedoService = undoRedoService;
    }

    @PostMapping("/undo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void undo() {
        if (!undoRedoService.undo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nothing to undo");
        }
    }

    @PostMapping("/redo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void redo() {
        if (!undoRedoService.redo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nothing to redo");
        }
    }
}
