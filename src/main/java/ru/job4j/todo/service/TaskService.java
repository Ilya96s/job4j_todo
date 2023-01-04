package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

/**
 * TaskService - интерфейс, описывающий бизнес логику по работе с задачами
 *
 * @author Ilya Kaltygin
 */
public interface TaskService {

    /**
     * Добавить задачу в базу данных.
     * @param task задача.
     * @param categoriesIds список id.
     * @return Optional.of(task) если задача добавлена, иначе Optional.empty().
     */
    Optional<Task> add(Task task, List<Integer> categoriesIds);

    /**
     * Обновить задачу в базе данных.
     * @param task задача.
     * @param categoriesIds список id.
     */
    boolean replace(Task task, List<Integer> categoriesIds);

    /**
     * Удалить задачу из базы данных.
     * @param id id задачи, которую нужно удалить.
     */
    boolean delete(int id);

    /**
     * Найти все задачи в базе данных.
     * @param user пользователь.
     * @return список задач.
     */
    List<Task> findAll(User user);

    /**
     * Найти задачу по id.
     * @param id id.
     * @return Optional.of() если задача с таким id найдено, иначе Optional.empty().
     */
    Optional<Task> findById(int id);

    /**
     * Найти задачи по статусу
     * @param status статус.
     * @param user пользователь.
     * @return список задач.
     */
    List<Task> findByStatus(boolean status, User user);

    /**
     * Изменить состояние задачи на 'Выполнено'.
     * @param id id задачи.
     * @return true если состояние изменилось, иначе false
     */
    boolean setDone(int id);
}
