package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.GuardedBy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;
import java.util.List;

/**
 * TaskController - контроллер, обрабатывающий запросы от клиента и возвращающий результаты
 *
 * @author Ilya Kaltygin
 */

@Controller
@AllArgsConstructor
public class TaskController {

    /**
     * Сервис по работе с задачами
     */
    @GuardedBy("this")
    private final TaskService service;

    /**
     * Метод возвращает представление со списком всех задач из базы данных.
     * Если список пуст, то в представление будет передано сообщение "Список дел пуст....".
     * @param model модель с данными.
     * @return представление tasks.
     */
    @GetMapping("/tasks")
    public String tasks(Model model) {
        List<Task> taskList = service.findAll();
        if (taskList.isEmpty()) {
            model.addAttribute("message", "Список дел пуст....");
        }
        model.addAttribute("tasks", taskList);
        return "task/tasks";
    }

    /**
     * Метод возвращает представление с формой добавления задачи.
     * @return представление addTask.
     */
    @GetMapping("/formAddTask")
    public String formAddTask() {
        return "task/addTask";
    }

    /**
     * Метод добавлет задачу в базу данных.
     * @param task задача.
     * @return переадресация по url /tasks.
     */
    @PostMapping("createTask")
    public String createTask(@ModelAttribute Task task) {
        service.add(task);
        return "redirect:/tasks";
    }

    /**
     * Метод возвращает представление с информацией о задаче.
     * @param model модель с данными.
     * @param taskId id задачи.
     * @return представление taskInfo.
     */
    @GetMapping("/taskInfo/{taskId}")
    public String taskInfo(Model model, @PathVariable("taskId") int taskId) {
        model.addAttribute("task", service.findById(taskId).get());
        return "task/taskInfo";
    }

    /**
     * Метод удаляет задачу из базы данных.
     * @param taskId id задачи.
     * @return переадресация по url /tasks.
     */
    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId) {
        service.delete(taskId);
        return "redirect:/tasks";
    }

    /**
     * Метод перевод задачу в состояние выполнена.
     * @param taskId id задачи.
     * @return пернадресация по url /tasks.
     */
    @GetMapping("/setStatusDone/{taskId}")
    public String setStatusDone(@PathVariable("taskId") int taskId) {
        Task taskFromDB = service.findById(taskId).get();
        taskFromDB.setDone(true);
        service.replace(taskId, taskFromDB);
        return "redirect:/tasks";
    }

    /**
     * Метод возвращает представление с формой для редактирования задачи.
     * @param model модель с данными.
     * @param taskId id задачи.
     * @return представление updateTask.
     */
    @GetMapping("formUpdateTask/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int taskId) {
        model.addAttribute("task", service.findById(taskId).get());
        return "task/updateTask";
    }

    /**
     * Метод обновляет задачу в базе данных.
     * @param task задача.
     * @return переадресация по url /tasks.
     */
    @PostMapping("updateTask")
    public String updateTask(@ModelAttribute Task task) {
        service.replace(task.getId(), task);
        return "redirect:/tasks";
    }

    /**
     * Метод возвращает представление со списком всех выполненных задач.
     * Если список пуст, то в представление будет передано сообщение "Нет выполненных задач....".
     * @param model модель с данными.
     * @return список задач.
     */
    @GetMapping("readyTasks")
    public String getReadyTasks(Model model) {
        List<Task> taskList = service.findReadyTasks();
        if (taskList.isEmpty()) {
            model.addAttribute("message", "Нет выполненных задач...");
        }
        model.addAttribute("tasks", taskList);
        return "task/readyTasks";
    }

    /**
     * Метод возвращает представление со списком всех не выполненных задач.
     * Если список пуст, то в представление будет передано сообщение "Нет новых задач....".
     * @param model модель с данными.
     * @return список задач.
     */
    @GetMapping("newTasks")
    public String getNewTasks(Model model) {
        List<Task> taskList = service.findNotReadyTasks();
        model.addAttribute("tasks", taskList);
        if (taskList.isEmpty()) {
            model.addAttribute("message", "Нет новых задач....");
        }
        return "task/notReadyTasks";
    }

}
