package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import java.util.Optional;

/**
 * SimpleUserService - реализация сервиса по работе с пользователями
 *
 * @author Ilya Kaltygin
 */

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    /**
     * Хранилище пользователей
     */
    private final UserRepository userRepository;

    /**
     * Добавление пользователя в базу данных.
     * @param user пользователь.
     * @return Optional.of(user) если успешно, иначе Optional.empty().
     */
    @Override
    public Optional<User> add(User user) {
        return userRepository.add(user);
    }

    /**
     * Поиск пользователя в базе данных по логину и паролю.
     * @param login логин.
     * @param password ппароль.
     * @return Optional.of(user) если пользователь по такому логину и паролю найден, иначе Optional.empty().
     */
    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return userRepository.findUserByLoginAndPassword(login, password);
    }

    /**
     * Обновить пользователя в базе данных.
     * @param user пользователь.
     */
    @Override
    public boolean replace(User user) {
        return userRepository.replace(user);
    }
}
