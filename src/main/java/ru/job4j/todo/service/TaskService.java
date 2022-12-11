package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
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
     * @return задача.
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
     * Найти задачи по статусу
     * @param status статус.
     * @return список задач.
     */
    List<Task> findByStatus(boolean status);

    /**
     * Изменить состояние задачи на 'Выполнено'.
     * @param id id задачи.
     * @return true если состояние изменилось, иначе false
     */
    boolean setDone(int id);
}
