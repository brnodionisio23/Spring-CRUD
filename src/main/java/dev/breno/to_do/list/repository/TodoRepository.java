package dev.breno.to_do.list.repository;

import dev.breno.to_do.list.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {}
