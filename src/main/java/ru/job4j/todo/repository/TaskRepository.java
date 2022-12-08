package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Optional;

/**
 * TaskRepository - хранилище задач.
 *
 * @author Ilya Kaltygin
 */

public interface TaskRepository {

    /**
     * Добавить задачу в базу данных.
     * @param task задача.
     * @return Optional.of(task) если добавили, иначе Optional.empty().
     */
    Task add(Task task);

    /**
     * Изменить задачу в базе данных.
     * @param id id задача, которую нужно изменить.
     * @param task задача.
     */
    boolean replace(int id, Task task);

    /**
     * Удалить задачу из базы данных.
     * @param id id задачи, которую нужно удалить.
     */
    boolean delete(int id);

    /**
     * Найти все задачи в базе данных.
     * @return список задач.
     */
    List<Task> findAll();

    /**
     * Найти задачу по id.
     * @param id id.
     * @return Optional.of() если задача с таким id найдено, иначе Optional.empty().
     */
    Optional<Task> findById(int id);

    /**
     * Найти все задачи со статусом true.
     * @return список задач.
     */
    List<Task> findReadyTasks();

    /**
     * Найти последние добавленные задачи.
     * @return список задач.
     */
    List<Task> findNotReadyTasks();
}
