package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * HbmCategoryRepository - реализация хранилища категорий.
 *
 * @author Ilya Kaltygin
 */

@Repository
@AllArgsConstructor
public class HbmCategoryRepository implements CategoryRepository {

    private static final String FIND_ALL = """
            From Category
            ORDER BY id
            """;

    private static final String FIND_BY_ID = """
            From Category
            WHERE id = :id
            """;

    private static final String FIND_CATEGORIES_BY_IDS = """
            From Category
            WHERE id IN :categoriesIds
            """;

    /**
     * Объект типа CrudRepository.
     */
    private final CrudRepository crudRepository;

    /**
     * Найти все категории в базе данных.
     * @return список категорий.
     */
    @Override
    public List<Category> findAll() {
        return crudRepository.queryAndGetList(FIND_ALL, Category.class);
    }

    /**
     * Найти категорию по id.
     * @param id id.
     * @return Optional.of() если категория с таким id найдена, иначе Optional.empty().
     */
    public Optional<Category> findById(int id) {
        return crudRepository.queryAndGetOptional(FIND_BY_ID, Category.class, Map.of("id", id));
    }

    /**
     * Найти категории по списку id.
     * @param categoriesIds список id.
     * @return список категорий.
     */
    public List<Category> findCategoriesByIds(List<Integer> categoriesIds) {
        return crudRepository.queryAndGetList(FIND_CATEGORIES_BY_IDS, Category.class, Map.of("categoriesIds", categoriesIds));
    }
}
