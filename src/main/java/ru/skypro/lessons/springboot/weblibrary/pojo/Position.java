package ru.skypro.lessons.springboot.weblibrary.pojo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "position")

public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "position_name")
    private String position_name;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<Employee> employeeList;

    public Position(int i, String position_name) {

    }
}
