package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;
import java.util.List;
import java.util.Optional;

/**
 * CategoryService - интерфейс, описывающий бизнес логику по работе с категориями.
 *
 * @author Ilya Kaltygin
 */

public interface CategoryService {

    /**
     * Найти все категории в базе данных.
     * @return список категорий.
     */
    List<Category> findAll();

    /**
     * Найти категорию по id.
     * @param id id.
     * @return Optional.of() если категория с таким id найдена, иначе Optional.empty().
     */
    Optional<Category> findById(int id);
}
