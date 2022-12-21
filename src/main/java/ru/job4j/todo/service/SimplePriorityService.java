package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.List;
import java.util.Optional;

/**
 * SimplePriorityService - реализация сервиса по работе с приоритетами.
 *
 * @author Ilya Kaltygin
 */

@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {

    private PriorityRepository priorityRepository;

    /**
     * Найти все приоритеты из базы данных.
     * @return список приоритетов.
     */
    @Override
    public List<Priority> getAllPriorities() {
        return priorityRepository.getAllPriorities();
    }

}
