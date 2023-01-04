package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * SimpleTimeZoneService - реализация сервиса по работе с часовыми поясами.
 *
 * @author Ilya Kaltygin
 */

@Service
@AllArgsConstructor
public class SimpleTimeZoneService implements TimeZoneService {

    /**
     * Получение часовых поясов поддерживаемых Java.
     * @return список часовых поясов.
     */
    @Override
    public List<TimeZone> getAvailableTimeZoneIDs() {
        var zones = new ArrayList<TimeZone>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        return zones;
    }

    /**
     * Изменение часового пояса у списка задач в соответствии с часовым поясом пользователя.
     * @param taskList список задач у которых необходимо изменить часовой пояс.
     * @param user пользователь.
     * @return список задач с измененным часовым поясом.
     */
    @Override
    public List<Task> changeOfTimeZones(List<Task> taskList, User user) {
        if (user.getTimeZone() == null) {
            user.setTimeZone(String.valueOf(TimeZone.getDefault()));
        }
        for (Task task : taskList) {
            task.setCreated(task.getCreated().atZone(TimeZone.getDefault().toZoneId()).withZoneSameInstant(ZoneId.of(user.getTimeZone())).toLocalDateTime());
        }
        return taskList;
    }
}
