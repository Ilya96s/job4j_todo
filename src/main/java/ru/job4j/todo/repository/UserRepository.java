package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * UserRepository - хранилище пользователей
 *
 * @author Ilya Kaltygin
 */
public interface UserRepository {

    /**
     * Добавление пользователя в базу данных.
     * @param user пользователь.
     * @return Optional.of(user) если успешно, иначе Optional.empty().
     */
    Optional<User> add(User user);

    /**
     * Поиск пользователя в базе данных по логину и паролю.
     * @param login логин.
     * @param password ппароль.
     * @return Optional.of(user) если пользователь по такому логину и паролю найден, иначе Optional.empty().
     */
    Optional<User> findUserByLoginAndPassword(String login, String password);


    /**
     * Обновить пользователя в базе данных.
     * @param user пользователь.
     */
    boolean replace(User user);
}
