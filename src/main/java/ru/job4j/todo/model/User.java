package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

/**
 * User - модель данных, описывающая пользователя
 *
 * @author Ilya Kaltygin
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "todo_user")
public class User {

    /**
     * id пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * имя пользователя.
     */
    private String name;

    /**
     * логин пользователя
     */
    private String login;

    /**
     * пароль пользователя
     */
    private String password;

    /**
     * часовой пояс
     */
    @Column(name = "user_zone")
    private String timeZone;
}
