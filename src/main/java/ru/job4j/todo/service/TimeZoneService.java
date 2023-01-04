package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import java.util.List;
import java.util.TimeZone;

/**
 * TimeZoneService - интерфейс, описывающий бизнес логику по работе с часовыми поясами
 *
 * @author Ilya Kaltygin
 */

public interface TimeZoneService {

    /**
     * Получение часовых поясов поддерживаемых Java.
     * @return список часовых поясов.
     */
    List<TimeZone> getAvailableTimeZoneIDs();

    /**
     * Изменение часового пояса у списка задач в соответствии с часовым поясом пользователя.
     * @param taskList список задач у которых необходимо изменить часовой пояс.
     * @param user пользователь.
     * @return список задач с измененным часовым поясом.
     */
    List<Task> changeOfTimeZones(List<Task> taskList, User user);
}
