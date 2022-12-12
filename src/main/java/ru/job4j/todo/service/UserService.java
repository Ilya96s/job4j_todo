package ru.job4j.todo.service;

import ru.job4j.todo.model.User;
import java.util.Optional;

/**
 * UserService - интерфейс, описывающий бизнес логику по работе с пользователями
 */
public interface UserService {

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
}
