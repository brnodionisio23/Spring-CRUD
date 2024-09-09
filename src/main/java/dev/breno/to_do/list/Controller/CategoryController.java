package dev.breno.to_do.list.Controller;

import dev.breno.to_do.list.model.Category;
import dev.breno.to_do.list.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping("/add")
    public ResponseEntity<Category> insert(@RequestBody Category obj) {
        Category newObj = service.insert(obj);
        return new ResponseEntity<>(newObj, HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = service.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
