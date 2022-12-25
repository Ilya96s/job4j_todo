package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ImplTaskRepository - реализация хранилища задач.
 *
 * @author Ilya Kaltygin
 */

@Repository
@AllArgsConstructor
public class HbmTaskRepository implements TaskRepository {

    private static final String DELETE = """
            DELETE From Task
            WHERE id = : id
            """;

    private static final String FIND_ALL = """
            FROM Task as task
            JOIN FETCH task.priority
            ORDER BY task.id
            """;

    private static final String FIND_BY_ID = """
            From Task
            WHERE id = :id
            """;

    private static final String FIND_BY_STATUS = """
            From Task as task
            JOIN FETCH task.priority
            WHERE done = :status
            ORDER BY task.id
            """;

    private static final String SET_DONE = """
            UPDATE Task
            SET done = true
            WHERE id = : id
            """;

    /**
     * Логгер
     */
    private static final Logger LOG = LoggerFactory.getLogger(HbmTaskRepository.class.getName());

    /**
     * Объекти типа CrudRepository.
     */
    private final CrudRepository crudRepository;

    /**
     * Добавить задачу в базу данных.
     * @param task задача.
     * @return задача.
     */
    @Override
    public Task add(Task task) {
        task.setCreated(LocalDateTime.now());
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    /**
     * Обновить задачу в базе данных.
     * @param task задача.
     * @return true если изменили задачу, иначе false.
     */
    @Override
    public boolean replace(Task task) {
        crudRepository.run(session -> session.merge(task));
        return true;
    }

    /**
     *
     * Удалить задачу из базы данных.
     * @param id id задачи, которую нужно удалить.
     * @return true если задача удалена, иначе false.
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.queryAndGetBoolean(DELETE, Map.of("id", id));
    }

    /**
     * Найти все задачи в базе данных.
     * @return список задач.
     */
    @Override
    public List<Task> findAll() {
        return crudRepository.queryAndGetList(FIND_ALL, Task.class);
    }

    /**
     * Найти задачу по id.
     * @param id id.
     * @return Optional.of() если задача с таким id найдено, иначе Optional.empty().
     */
    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.queryAndGetOptional(FIND_BY_ID, Task.class, Map.of("id", id));
    }

    /**
     * Найти задачи по статусу
     * @param status статус.
     * @return список задач.
     */
    public List<Task> findByStatus(boolean status) {
        return crudRepository.queryAndGetList(FIND_BY_STATUS, Task.class, Map.of("status", status));
    }

    /**
     * Изменить состояние задачи на 'Выполнено'.
     * @param id id задачи.
     * @return true если состояние изменилось, иначе false
     */
    public boolean setDone(int id) {
        return crudRepository.queryAndGetBoolean(SET_DONE, Map.of("id", id));
    }
}
