package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.repository.PriorityRepository;
import ru.job4j.todo.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * SimpleTaskService - реализация сервиса по работе с задачами.
 *
 * @author Ilya Kaltygin
 */

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    /**
     * Хранилище задач
     */
    private final TaskRepository taskRepository;

    /**
     * Хранилище приоритетов
     */
    private final PriorityRepository priorityRepository;

    /**
     * Хранилище категорий
     */
    private final CategoryRepository categoryRepository;

    /**
     * Сервис по работе с часовыми поясами.
     */
    private final TimeZoneService timeZoneService;

    /**
     * Добавить задачу в базу данных.
     * @param task задача.
     * @param categoriesIds список id.
     * @return Optional.of(task) если задача добавлена, иначе Optional.empty().
     */
    @Override
    public Optional<Task> add(Task task, List<Integer> categoriesIds) {
        var foundCategoriesById = categoryRepository.findCategoriesByIds(categoriesIds);
        if (categoriesIds.size() != foundCategoriesById.size()) {
            return Optional.empty();
        }
        task.setCategoryList(foundCategoriesById);
        task.setCreated(LocalDateTime.now());
        return taskRepository.add(task);
    }

    /**
     * Обновить задачу в базе данных.
     * @param task задача.
     * @param categoriesIds список id.
     * @return true если успешно обновили задачу, иначе false.
     */
    @Override
    public boolean replace(Task task, List<Integer> categoriesIds) {
        if (categoriesIds == null) {
            return false;
        }
        var priority = priorityRepository.findById(task.getPriority().getId());
        var foundCategoriesByIds = categoryRepository.findCategoriesByIds(categoriesIds);
        if (foundCategoriesByIds.size() != categoriesIds.size() || priority.isEmpty()) {
            return false;
        }
        task.setCategoryList(foundCategoriesByIds);
        task.setPriority(priority.get());
        return taskRepository.replace(task);
    }

    /**
     * Удалить задачу из базы данных.
     * @param id id задачи, которую нужно удалить.
     */
    @Override
    public boolean delete(int id) {
        return taskRepository.delete(id);
    }

    /**
     * Найти все задачи в базе данных.
     * @param user пользователь.
     * @return список задач.
     */
    @Override
    public List<Task> findAll(User user) {
        return timeZoneService.changeOfTimeZones(taskRepository.findAll(user), user);
    }

    /**
     * Найти задачу по id.
     * @param id id.
     * @return Optional.of() если задача с таким id найдено, иначе Optional.empty().
     */
    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    /**
     * Найти задачи по статусу
     * @param status статус.
     * @return список задач.
     */
    public List<Task> findByStatus(boolean status, User user) {
        return timeZoneService.changeOfTimeZones(taskRepository.findAll(user), user);
    }

    /**
     * Изменить состояние задачи на 'Выполнено'.
     * @param id id задачи.
     * @return true если состояние изменилось, иначе false
     */
    public boolean setDone(int id) {
        return taskRepository.setDone(id);
    }
}
