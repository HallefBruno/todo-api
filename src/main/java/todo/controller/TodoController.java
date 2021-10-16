package todo.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import todo.model.Todo;
import todo.repository.TodoRepository;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin("*")
public class TodoController {
    
    private final TodoRepository repository;
    
    @Autowired
    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }
    
    @PostMapping
    public Todo save(@RequestBody Todo todo) {
        return repository.save(todo);
    }
    
    @GetMapping("{id}")
    public Todo getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public List<Todo> todos() {
        return repository.findAll();
    }
    
    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
    
    @PatchMapping("/done/{id}")
    public Todo markAsDone(@PathVariable Long id) {
        return repository.findById(id).map(todo -> {
            todo.setDone(Boolean.TRUE);
            todo.setDoneDate(LocalDateTime.now());
            repository.save(todo);
            return todo;
        }).orElse(null);
    }
    
}
