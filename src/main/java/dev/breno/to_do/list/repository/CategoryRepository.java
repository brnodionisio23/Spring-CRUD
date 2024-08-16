package dev.breno.to_do.list.repository;

import dev.breno.to_do.list.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
