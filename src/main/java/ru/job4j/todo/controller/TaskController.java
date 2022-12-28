package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
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
    private final TaskService taskService;

    /**
     * Сервис по работе с приоритетами
     */
    private final PriorityService priorityService;

    /**
     * Сервис по работе с категориями
     */
    private final CategoryService categoryService;

    /**
     * Метод возвращает представление со списком всех задач из базы данных.
     * Если список пуст, то в представление будет передано сообщение "Список дел пуст....".
     * @param model модель с данными.
     * @return представление tasks.
     */
    @GetMapping("")
    public String tasks(Model model, HttpSession session) {
        List<Task> taskList = taskService.findAll();
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
        model.addAttribute("priorities", priorityService.getAllPriorities());
        model.addAttribute("categories", categoryService.findAll());
        return "task/add";
    }

    /**
     * Метод добавлет задачу в базу данных.
     * @param task задача.
     * @return переадресация по url /tasks.
     */
    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, HttpSession session,
                             @RequestParam(value = "categories", required = false) Integer[] categoriesIds) {
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        taskService.add(task, categoriesIds);
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
        Optional<Task> optionalTask = taskService.findById(taskId);
        if (optionalTask.isEmpty()) {
            return "redirect:/tasks/fail";
        }
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        model.addAttribute("task", optionalTask.get());
        model.addAttribute("categories", optionalTask.get().getCategoryList());
        return "task/info";
    }

    /**
     * Метод удаляет задачу из базы данных.
     * @param taskId id задачи.
     * @return переадресация по url /tasks если задача удалена, иначе /tasks/fail.
     */
    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId) {
        if (!taskService.delete(taskId)) {
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
        if (!taskService.setDone(taskId)) {
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
        Optional<Task> optionalTask = taskService.findById(taskId);
        if (optionalTask.isEmpty()) {
            return "redirect:/tasks/fail";
        }
        List<Priority> allPriorities = priorityService.getAllPriorities();
        List<Category> allCategories = categoryService.findAll();
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        model.addAttribute("task", optionalTask.get());
        model.addAttribute("priorities", allPriorities);
        model.addAttribute("categories", allCategories);
        return "task/update";
    }

    /**
     * Метод обновляет задачу в базе данных.
     * @param task задача.
     * @return переадресация по url /tasks если задача обновлена, иначе /tasks/fail.
     */
    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task, HttpSession session,
                             @RequestParam(value = "categories", required = false) Integer[] categories) {
        task.setUser((User) session.getAttribute("user"));
        if (!taskService.replace(task, categories)) {
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
        List<Task> taskList = taskService.findByStatus(status);
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
        List<Task> taskList = taskService.findByStatus(status);
        if (taskList.isEmpty()) {
            model.addAttribute("message", "Нет новых задач....");
        }
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        model.addAttribute("tasks", taskList);
        return "task/notReady";
    }

}
