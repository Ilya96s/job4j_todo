package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

/**
 * PriorityService - интерфейс, описывающий бизнес логику по работе с приоритетами
 *
 * @author Ilya Kaltygin
 */

public interface PriorityService {

    /**
     * Найти все приоритеты из базы данных.
     * @return список приоритетов.
     */
    List<Priority> getAllPriorities();
}
