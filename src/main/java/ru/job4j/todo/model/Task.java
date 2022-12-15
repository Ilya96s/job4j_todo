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

    /**
     * id задачи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Описание задачи.
     */
    private String description;

    /**
     * Пользователь связанный с заданием.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Дата создания задачи.
     */
    private LocalDateTime created;

    /**
     * Статус выполнения.
     */
    private boolean done;
}
