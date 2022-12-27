package ru.job4j.todo.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Приоритет.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    /**
     * Список категорий
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tasks_categories",
            joinColumns = { @JoinColumn(name = "task_id")},
            inverseJoinColumns = { @JoinColumn(name = "category_id")}
    )
    private List<Category> categoryList = new ArrayList<>();
}
