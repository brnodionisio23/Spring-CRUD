package dev.breno.to_do.list.service;

import dev.breno.to_do.list.model.Category;
import dev.breno.to_do.list.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category insert(Category obj){
        return repository.save(obj);
    }
}
