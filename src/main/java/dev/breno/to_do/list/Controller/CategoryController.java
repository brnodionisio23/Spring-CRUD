package dev.breno.to_do.list.Controller;

import dev.breno.to_do.list.model.Category;
import dev.breno.to_do.list.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "categoria")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(obj));
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Category> update(@PathVariable(name = "id") Long id, @RequestBody Category obj) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, obj));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        service.delete(id);
    }

}
