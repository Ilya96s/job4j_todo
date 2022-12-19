package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

/**
 * Priority - модель данных
 *
 * @author Ilya Kaltygin
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "priorities")
public class Priority {

    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    /**
     * имя приоритета.
     */
    private String name;

    /**
     * позиция приоритета.
     */
    private int position;
}
