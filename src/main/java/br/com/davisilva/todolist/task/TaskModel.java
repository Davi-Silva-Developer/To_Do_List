package br.com.davisilva.todolist.task;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@Table(name = "tb_tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String descricao;

    @Column(length = 50)
    private String title; // Essa anotação fala que essa coluna tera 50 caracteres no max

    private LocalDateTime hrInicio;
    private LocalDateTime hrfim;
    private String prioridade;
    private UUID idUser;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;




}
