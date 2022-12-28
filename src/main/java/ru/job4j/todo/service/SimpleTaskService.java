package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.repository.PriorityRepository;
import ru.job4j.todo.repository.TaskRepository;
import java.util.ArrayList;
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
     * Добавить задачу в базу данных.
     * @param task задача.
     * @return задача.
     */
    @Override
    public Task add(Task task, Integer[] categoriesIds) {
        var categoriesForTask = new ArrayList<Category>();
        var categoriesFromDb = categoryRepository.findAll();
        for (Integer i : categoriesIds) {
            if (categoriesFromDb.get(i - 1) != null) {
                categoriesForTask.add(categoriesFromDb.get(i - 1));
            }
        }
        task.setCategoryList(categoriesForTask);
        return taskRepository.add(task);
    }

    /**
     * Обновить задачу в базе данных.
     * @param task задача.
     */
    @Override
    public boolean replace(Task task, Integer[] categoriesIds) {
        if (categoriesIds == null) {
            return false;
        }
        var priority = priorityRepository.findById(task.getPriority().getId());
        if (priority.isEmpty()) {
            return false;
        }
        var categoriesForTask = new ArrayList<Category>();
        var categoriesFromDb = categoryRepository.findAll();
        for (Integer i : categoriesIds) {
            if (categoriesFromDb.get(i - 1) != null) {
                categoriesForTask.add(categoriesFromDb.get(i - 1));
            } else {
                return false;
            }
        }
        task.setCategoryList(categoriesForTask);
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
     * @return список задач.
     */
    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
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
    public List<Task> findByStatus(boolean status) {
        return taskRepository.findByStatus(status);
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
