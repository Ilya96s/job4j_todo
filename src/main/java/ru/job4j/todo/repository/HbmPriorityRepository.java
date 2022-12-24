package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * HbmPriorityRepository - реализация хранилища приоритетов.
 *
 * @author Ilya Kaltygin
 */
@Repository
@AllArgsConstructor
public class HbmPriorityRepository implements PriorityRepository {

    private static final String FIND_ALL = """
            From Priority
            """;

    private static final String FIND_BY_ID = """
            From Priority
            WHERE id = :id
            """;

    /**
     * Объекти типа CrudRepository.
     */
    private final CrudRepository crudRepository;

    /**
     * Найти все приоритеты из базы данных.
     * @return список приоритетов.
     */
    @Override
    public List<Priority> getAllPriorities() {
        return crudRepository.queryAndGetList(FIND_ALL, Priority.class);
    }

    /**
     * Найти приоритет по id.
     * @param id id.
     * @return eсли значение не null, то будет создан Optional со значением, иначе — пустой Optional.
     */
    public Optional<Priority> findById(int id) {
        return crudRepository.queryAndGetOptional(FIND_BY_ID, Priority.class, Map.of("id", id));
    }
}
