package dev.breno.to_do.list.service;

import dev.breno.to_do.list.model.Category;
import dev.breno.to_do.list.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category insert(Category obj) {
        return repository.save(obj);
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        return obj.get();
    }

    public Category update(Long id, Category category) {
        Optional<Category> entity = repository.findById(id);

        if (entity.isPresent()) {
            Category newCategory = entity.get();

            newCategory.setName(category.getName());
            newCategory.setTodo(category.getTodo());

            return repository.save(newCategory);

        } else {
            return null;
        }
    }

    public void delete(Long id){
        repository.findById(id).ifPresent(repository::delete);
    }

}
