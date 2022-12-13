package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;
import ru.job4j.todo.utility.HttpSessionUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * UserController - контроллер, обрабатывающий запросы от клиента и возвращающий результаты
 *
 * @author Ilya Kaltygin
 */

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    /**
     * Сервис по работе с пользователями
     */
    private final UserService service;

    /**
     * Метод возвращает представление с формой добавления нового пользователя
     * @param model модель с данными.
     * @param fail параметр запроса.
     * @return представление add.
     */
    @GetMapping("/regPage")
    public String formAddUser(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "registration/add";
    }

    /**
     * Метод добавляет пользователя в базу данных
     * @param user Объект типа User
     * @return Переадресация по url /user/success если успешно, иначе /user/regPage?fail=true
     */
    @PostMapping("/registration")
    public String createUser(@ModelAttribute User user) {
        Optional<User> regUser = service.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/user/regPage?fail=true";
        }
        return "redirect:/user/success";
    }

    /**
     * Метод возвращает представление с информацией об успешной регистрации пользователя
     * @param model Модель с данными
     * @param session Объект типа HttpSession
     * @return Представление success
     */
    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        model.addAttribute("user", HttpSessionUtility.checkSession(session));
        return "registration/success";
    }

    /**
     * Метод возвращает представление для авторизации пользователя.
     * @param model модель с данными.
     * @param fail параметр запроса.
     * @return представление login.
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "authorization/login";
    }

    /**
     * Метод производит поиск пользователя в базе данных по данным, которые были получены из метода loginPage.
     * @param user пользователь созданный из параметров запроса.
     * @param request объект типа HttpServletRequest.
     * @return Если пользователь найден в базе данных произойдет переадресация по url /tasks, иначе по url /user/loginPage?fail=true
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> optionalUser = service.findUserByLoginAndPassword(user.getLogin(), user.getPassword());
        if (optionalUser.isEmpty()) {
            return "redirect:/user/loginPage?fail=true";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", optionalUser.get());
        return "redirect:/tasks";
    }

    /**
     * Метод очищает данные из сессии
     * @param session Объекти типа HttpSession
     * @return Происходит переадресация по url loginPage
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/loginPage";
    }
}
