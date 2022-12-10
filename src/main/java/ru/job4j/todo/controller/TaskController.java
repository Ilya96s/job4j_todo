package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/tasks")
public class TaskController {

    /**
     * Сервис по работе с задачами
     */
    private final TaskService service;

    /**
     * Метод возвращает представление со списком всех задач из базы данных.
     * Если список пуст, то в представление будет передано сообщение "Список дел пуст....".
     * @param model модель с данными.
     * @return представление tasks.
     */
    @GetMapping("")
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
    @GetMapping("/formAdd")
    public String formAddTask() {
        return "task/addTask";
    }

    /**
     * Метод добавлет задачу в базу данных.
     * @param task задача.
     * @return переадресация по url /tasks.
     */
    @PostMapping("/create")
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
    @GetMapping("/info/{taskId}")
    public String taskInfo(Model model, @PathVariable("taskId") int taskId) {
        model.addAttribute("task", service.findById(taskId).get());
        return "task/taskInfo";
    }

    /**
     * Метод удаляет задачу из базы данных.
     * @param taskId id задачи.
     * @return переадресация по url /tasks.
     */
    @GetMapping("/delete/{taskId}")
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
        service.replace(taskId, service.findById(taskId).get());
        return "redirect:/tasks";
    }

    /**
     * Метод возвращает представление с формой для редактирования задачи.
     * @param model модель с данными.
     * @param taskId id задачи.
     * @return представление updateTask.
     */
    @GetMapping("/formUpdate/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int taskId) {
        model.addAttribute("task", service.findById(taskId).get());
        return "task/updateTask";
    }

    /**
     * Метод обновляет задачу в базе данных.
     * @param task задача.
     * @return переадресация по url /tasks.
     */
    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task) {
        service.replace(task.getId(), task);
        return "redirect:/tasks";
    }

    /**
     * Метод возвращает представление со списком всех выполненных задач.
     * Если список пуст, то в представление будет передано сообщение "Нет выполненных задач....".
     * @param model модель с данными.
     * @param status значение параметра запроса по ключу status.
     * @return список задач.
     */
    @GetMapping("/ready")
    public String getReadyTasks(Model model, @RequestParam(name = "status", required = false) Boolean status) {
        List<Task> taskList = service.findByStatus(status);
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
     * @param status значение параметра запроса по ключу status.
     * @return список задач.
     */
    @GetMapping("/new")
    public String getNewTasks(Model model, @RequestParam(name = "status", required = false) Boolean status) {
        List<Task> taskList = service.findByStatus(status);
        model.addAttribute("tasks", taskList);
        if (taskList.isEmpty()) {
            model.addAttribute("message", "Нет новых задач....");
        }
        return "task/notReadyTasks";
    }

}
