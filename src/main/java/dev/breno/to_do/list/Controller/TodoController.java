package dev.breno.to_do.list.Controller;

import dev.breno.to_do.list.model.Todo;
import dev.breno.to_do.list.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @PostMapping
    public ResponseEntity<Todo> save(@RequestBody Todo obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(obj));
    }

    @GetMapping
    public ResponseEntity<List<Todo>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Todo> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id, @RequestBody Todo obj) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, obj));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


}
