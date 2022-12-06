package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Task - модель данных, описывающая задание
 *
 * @author Ilya Kaltygin
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String description;

    private LocalDateTime created;

    private boolean done;
}
