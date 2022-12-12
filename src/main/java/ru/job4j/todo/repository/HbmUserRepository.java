package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
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
     * Объект конфигуратор.
     * Используется для получения объектов Session.
     * Отвечает за считывание параметров конфигурации Hibernate и подключение к базе данных.
     */
    private final SessionFactory sf;

    /**
     * Добавление пользователя в базу данных.
     * @param user пользователь.
     * @return Optional.of(user) если успешно, иначе Optional.empty().
     */
    @Override
    public Optional<User> add(User user) {
        Session session = sf.openSession();
        Optional<User> result = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            result = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Поиск пользователя в базе данных по логину и паролю.
     * @param login логин.
     * @param password ппароль.
     * @return Optional.of(user) если пользователь по такому логину и паролю найден, иначе Optional.empty().
     */
    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Optional<User> result = Optional.empty();
        try {
            session.beginTransaction();
            result = session.createQuery(FIND_BY_LOGIN_AND_PASSWORD)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }
}
