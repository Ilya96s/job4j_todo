package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

/**
 * HbmUserRepository - реализация хранилища пользователей
 *
 * @author Ilya Kaltygin
 */

@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {

    private static final String FIND_BY_LOGIN_AND_PASSWORD = """
            From User
            WHERE login = :login AND password = :password
            """;

    /**
     * Объекти типа CrudRepository.
     */
    private final CrudRepository crudRepository;

    /**
     * Добавление пользователя в базу данных.
     * @param user пользователь.
     * @return Optional.of(user) если успешно, иначе Optional.empty().
     */
    @Override
    public Optional<User> add(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Поиск пользователя в базе данных по логину и паролю.
     * @param login логин.
     * @param password ппароль.
     * @return Optional.of(user) если пользователь по такому логину и паролю найден, иначе Optional.empty().
     */
    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return crudRepository.queryAndGetOptional(FIND_BY_LOGIN_AND_PASSWORD, User.class,
                Map.of("login", login, "password", password));
    }

    /**
     * Обновить пользователя в базе данных.
     * @param user пользователь.
     */
    @Override
    public boolean replace(User user) {
        return crudRepository.executeAndGetBoolean(user);
    }
}
