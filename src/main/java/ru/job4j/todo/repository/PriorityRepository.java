package ru.job4j.todo.repository;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

/**
 * PriorityRepository - хранилище приоритетов.
 *
 * @author Ilya Kaltygin
 */
public interface PriorityRepository {

    /**
     * Найти все приоритеты из базы данных.
     * @return список приоритетов.
     */
    List<Priority> getAllPriorities();
}
