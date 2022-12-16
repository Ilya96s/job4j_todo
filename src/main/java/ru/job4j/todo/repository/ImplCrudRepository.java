package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * CrudRepository - реализация паттерна Command
 *
 * @author Ilya Kaltygin
 */
@Repository
@AllArgsConstructor
public class ImplCrudRepository implements CrudRepository {

    /**
     * Объект конфигуратор.
     * Используется для получения объектов Session.
     * Отвечает за считывание параметров конфигурации Hibernate и подключение к базе данных.
     */
    private final SessionFactory sf;

    /**
     * Логгер
     */
    private static final Logger LOG = LoggerFactory.getLogger(ImplCrudRepository.class.getName());

    /**
     * Метод принимает параметры и создает из них команду.
     * В этом же методе вызывается метод executeTransaction(Function<Session, T> command), который выполняет созданную команду.
     * @param query запрос.
     * @param args карта,где ключ = псевдоним, значение = значение псевдонима.
     * @return true или false.
     */
    public boolean queryAndGetBoolean(String query, Map<String, Object> args) {
        Function<Session, Integer> command = session -> {
            var sq = session.createQuery(query);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.executeUpdate();
        };
        return executeTransaction(command) > 0;
    }

    /**
     * Метод принимает параметры и создает из них команду.
     * В этом же методе вызывается метод executeTransaction(Function<Session, T> command), который выполняет созданную команду.
     * @param query запрос.
     * @param cl Класс, данные какого типа хотим получить.
     * @param args карта,где ключ = псевдоним, значение = значение псевдонима.
     * @return List<T>>
     * @param <T> generic.
     */
    public <T> List<T> queryAndGetList(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.list();
        };
        return executeTransaction(command);
    }

    /**
     * Метод принимает параметры и создает из них команду.
     * В этом же методе вызывается метод executeTransaction(Function<Session, T> command), который выполняет созданную команду.
     * @param query запрос.
     * @param cl Класс, данные какого типа хотим получить.
     * @return List<T>>
     * @param <T> generic.
     */
    public <T> List<T> queryAndGetList(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            return sq.list();
        };
        return executeTransaction(command);
    }

    /**
     * Метод принимает параметры и создает из них команду.
     * В этом же методе вызывается метод executeTransaction(Function<Session, T> command), который выполняет созданную команду.
     * @param query запрос.
     * @param cl Класс, данные какого типа хотим получить.
     * @param args карта,где ключ = псевдоним, значение = значение псевдонима.
     * @return Optional<T>
     * @param <T> generic.
     */
    public <T> Optional<T> queryAndGetOptional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return Optional.ofNullable(sq.uniqueResult());
        };
        return executeTransaction(command);
    }

    /**
     * Метод принимает параметры и создает из них команду и передает ее в
     * метод executeTransaction(Function<Session, T> command), который выполняет полученную команду.
     * @param command команда.
     */
    public void run(Consumer<Session> command) {
        executeTransaction(session -> {
            command.accept(session);
            return null;
        });
    }

    /**
     * Главный метод в этом классе, выполняющий абстрактную операцию.
     * Метод принимает команду, открывает сессию, начинает транзакцию, выполняет команду.
     * Команда принимается в виде функционального интерфейса, благодаря чему достигается абстрактность операции.
     * @param command команда, которую необходимо выполнить.
     * @param <T> generic.
     * @return значение типа Т.
     */
    public <T> T executeTransaction(Function<Session, T> command) {
        var session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Exception in method executeTransaction(Function<Session, T> command", e);
            throw e;
        } finally {
            session.close();
        }
    }
}
