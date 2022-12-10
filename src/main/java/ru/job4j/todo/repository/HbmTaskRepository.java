package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ImplTaskRepository - реализация хранилища задач.
 *
 * @author Ilya Kaltygin
 */

@Repository
@AllArgsConstructor
public class HbmTaskRepository implements TaskRepository {

    private static final String REPLACE = """
            UPDATE Task
            SET description = :desc, done = :done
            WHERE id = :id
            """;

    private static final String DELETE = """
            DELETE From Task
            WHERE id = : id
            """;

    private static final String FIND_ALL = """
            From Task as task
            ORDER BY task.id
            """;

    private static final String FIND_BY_ID = """
            From Task
            WHERE id = :id
            """;

    private static final String FIND_BY_STATUS = """
            From Task as task
            WHERE done = :status
            ORDER BY task.id
            """;

    private static final Logger LOG = LoggerFactory.getLogger(HbmTaskRepository.class.getName());

    /**
     * Объект конфигуратор.
     * Используется для получения объектов Session.
     * Отвечает за считывание параметров конфигурации Hibernate и подключение к базе данных.
     */
    private final SessionFactory sf;

    /**
     * Добавить задачу в базу данных.
     * @param task задача.
     * @return Optional.of(task) если добавили, иначе Optional.empty().
     */
    @Override
    public Task add(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            task.setCreated(LocalDateTime.now());
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    /**
     * Изменить задачу в базе данных.
     * @param id id задача, которую нужно изменить.
     * @param task задача.
     */
    @Override
    public boolean replace(int id, Task task) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            result = session.createQuery(REPLACE)
                    .setParameter("desc", task.getDescription())
                    .setParameter("done", task.isDone())
                    .setParameter("id", id)
                    .executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Удалить задачу из базы данных.
     * @param id id задачи, которую нужно удалить.
     */
    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            result = session.createQuery(DELETE)
                    .setParameter("id", id)
                    .executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Найти все задачи в базе данных.
     * @return список задач.
     */
    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        List<Task> tasks = new ArrayList<>();
        try {
            session.beginTransaction();
            tasks = session.createQuery(FIND_ALL).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    /**
     * Найти задачу по id.
     * @param id id.
     * @return Optional.of() если задача с таким id найдено, иначе Optional.empty().
     */
    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Optional<Task> task = Optional.empty();
        try {
            session.beginTransaction();
            task = Optional.of((Task) session.createQuery(FIND_BY_ID)
                    .setParameter("id", id)
                    .uniqueResult());
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    /**
     * Найти задачи по статусу
     * @param status статус.
     * @return список задач.
     */
    public List<Task> findByStatus(boolean status) {
        Session session = sf.openSession();
        List<Task> tasks = new ArrayList<>();
        try {
            session.beginTransaction();
            tasks = session.createQuery(FIND_BY_STATUS)
                    .setParameter("status", status)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }
}
