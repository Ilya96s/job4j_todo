package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    /**
     * Хранилище категорий.
     */
    private final CategoryRepository categoryRepository;

    /**
     * Найти все категории в базе данных.
     * @return список категорий.
     */
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Найти категорию по id.
     * @param id id.
     * @return Optional.of() если категория с таким id найдена, иначе Optional.empty().
     */
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }
}
