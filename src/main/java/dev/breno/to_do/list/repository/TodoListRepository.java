package dev.breno.to_do.list.repository;

import dev.breno.to_do.list.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {}
