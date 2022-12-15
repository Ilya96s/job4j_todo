package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.utility.HttpSessionUtility;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

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
    public String tasks(Model model, HttpSession session) {
        List<Task> taskList = service.findAll();
        if (taskList.isEmpty()) {
            model.addAttribute("message", "Список дел пуст....");
        }
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        model.addAttribute("tasks", taskList);
        return "task/tasks";
    }

    /**
     * Метод возвращает представление с формой добавления задачи.
     * @return представление addTask.
     */
    @GetMapping("/formAdd")
    public String formAddTask(Model model, HttpSession session) {
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        return "task/add";
    }

    /**
     * Метод добавлет задачу в базу данных.
     * @param task задача.
     * @return переадресация по url /tasks.
     */
    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, HttpSession session) {
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        service.add(task);
        return "redirect:/tasks";
    }

    /**
     * Метод возвращает представление с информацией о задаче.
     * @param model модель с данными.
     * @param taskId id задачи.
     * @return представление taskInfo если задача найдена в базе данных, иначе переадресация по url tasks/fail.
     */
    @GetMapping("/info/{taskId}")
    public String taskInfo(Model model, @PathVariable("taskId") int taskId, HttpSession session) {
        Optional<Task> optionalTask = service.findById(taskId);
        if (optionalTask.isEmpty()) {
            return "redirect:/tasks/fail";
        }
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        model.addAttribute("task", optionalTask.get());
        return "task/info";
    }

    /**
     * Метод удаляет задачу из базы данных.
     * @param taskId id задачи.
     * @return переадресация по url /tasks если задача удалена, иначе /tasks/fail.
     */
    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId) {
        if (!service.delete(taskId)) {
            return "redirect:/tasks/fail";
        }
        return "redirect:/tasks";
    }

    /**
     * Метод перевод задачу в состояние выполнена.
     * @param taskId id задачи.
     * @return пернадресация по url /tasks если задача изменена, иначе переадресация по url /tasks/fail.
     */
    @GetMapping("/setStatusDone/{taskId}")
    public String setStatusDone(@PathVariable("taskId") int taskId) {
        if (!service.setDone(taskId)) {
            return "redirect:/shared/fail";
        }
        return "redirect:/tasks";
    }

    /**
     * Метод возвращает представление с формой для редактирования задачи.
     * @param model модель с данными.
     * @param taskId id задачи.
     * @return представление updateTask если задача найдена в базе данных, иначе переадресация по url /tasks/fail.
     */
    @GetMapping("/formUpdate/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int taskId, HttpSession session) {
        Optional<Task> optionalTask = service.findById(taskId);
        if (optionalTask.isEmpty()) {
            return "redirect:/tasks/fail";
        }
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        model.addAttribute("task", optionalTask.get());
        return "task/update";
    }

    /**
     * Метод обновляет задачу в базе данных.
     * @param task задача.
     * @return переадресация по url /tasks если задача обновлена, иначе /tasks/fail.
     */
    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task) {
        if (!service.replace(task.getId(), task)) {
            return "redirect:/tasks/fail";
        }
        return "redirect:/tasks";
    }

    /**
     * Метод возвращает представление с информацией об ошибке.
     * @param model модель с данными.
     * @return представление fail.
     */
    @GetMapping("/fail")
    public String updateFail(Model model, HttpSession session) {
        model.addAttribute("message", "Не удалось выполнить действие");
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        return "shared/fail";
    }

    /**
     * Метод возвращает представление со списком всех выполненных задач.
     * Если список пуст, то в представление будет передано сообщение "Нет выполненных задач....".
     * @param model модель с данными.
     * @param status значение параметра запроса по ключу status.
     * @return список задач.
     */
    @GetMapping("/ready")
    public String getReadyTasks(Model model,
                                @RequestParam(name = "status", required = false) Boolean status,
                                HttpSession session) {
        List<Task> taskList = service.findByStatus(status);
        if (taskList.isEmpty()) {
            model.addAttribute("message", "Нет выполненных задач...");
        }
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        model.addAttribute("tasks", taskList);
        return "task/ready";
    }

    /**
     * Метод возвращает представление со списком всех не выполненных задач.
     * Если список пуст, то в представление будет передано сообщение "Нет новых задач....".
     * @param model модель с данными.
     * @param status значение параметра запроса по ключу status.
     * @return список задач.
     */
    @GetMapping("/new")
    public String getNewTasks(Model model,
                              @RequestParam(name = "status", required = false) Boolean status,
                              HttpSession session) {
        List<Task> taskList = service.findByStatus(status);
        if (taskList.isEmpty()) {
            model.addAttribute("message", "Нет новых задач....");
        }
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        model.addAttribute("tasks", taskList);
        return "task/notReady";
    }

}
