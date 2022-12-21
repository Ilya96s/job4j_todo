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
}
