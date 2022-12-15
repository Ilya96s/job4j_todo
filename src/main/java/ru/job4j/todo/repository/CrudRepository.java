package ru.job4j.todo.repository;

import org.hibernate.Session;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Ilya Kaltygin
 */
public interface CrudRepository {

    /**
     * Метод принимает параметры и создает из них команду.
     * @param query запрос.
     * @param args карта,где ключ = псевдоним, значение = значение псевдонима.
     * @return true или false.
     */
    boolean queryAndGetBoolean(String query, Map<String, Object> args);

    /**
     * Метод принимает параметры и создает из них команду.
     * @param query запрос.
     * @param cl Класс, данные какого типа хотим получить.
     * @param args карта,где ключ = псевдоним, значение = значение псевдонима.
     * @return List<T>>
     * @param <T> generic.
     */
    <T> List<T> queryAndGetList(String query, Class<T> cl, Map<String, Object> args);

    /**
     * Метод принимает параметры и создает из них команду.
     * @param query запрос.
     * @param cl Класс, данные какого типа хотим получить.
     * @return List<T>>
     * @param <T> generic.
     */
    <T> List<T> queryAndGetList(String query, Class<T> cl);

    /**
     * Метод принимает параметры и создает из них команду.
     * @param query запрос.
     * @param cl Класс, данные какого типа хотим получить.
     * @param args карта,где ключ = псевдоним, значение = значение псевдонима.
     * @return Optional<T>
     * @param <T> generic.
     */
    <T> Optional<T> queryAndGetOptional(String query, Class<T> cl, Map<String, Object> args);

    /**
     * Метод принимает параметры и создает из них команду.
     * @param command команда.
     */
    void run(Consumer<Session> command);

    /**
     * Главный метод в этом классе, выполняющий абстрактную операцию.
     * @param command команда, которую необходимо выполнить.
     * @param <T> generic.
     * @return значение типа Т.
     */
    <T> T executeTransaction(Function<Session, T> command);
}
