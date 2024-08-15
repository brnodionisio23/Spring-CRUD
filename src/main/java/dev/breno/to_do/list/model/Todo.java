package dev.breno.to_do.list.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "To-do")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "Tarefa")
    String task;
    @Column(name = "Categoria")
    String category;
}
