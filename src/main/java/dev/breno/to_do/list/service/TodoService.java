package dev.breno.to_do.list.service;

import dev.breno.to_do.list.model.Todo;
import dev.breno.to_do.list.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public Todo save(Todo todo) {
        return repository.save(todo);
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public Todo findById(Long id) {
        Optional<Todo> obj = repository.findById(id);
        return obj.get();
    }

    public Todo update(Long id, Todo todo) {
        Optional<Todo> entity = repository.findById(id);

        if (entity.isPresent()) {
            Todo obj = entity.get();

            obj.setCategory(todo.getCategory());
            obj.setTask(todo.getTask());

            return repository.save(obj);
        } else {
            return null;
        }

    }

    public void delete(Long id) {
        repository.findById(id).ifPresent(repository::delete);
    }
}
