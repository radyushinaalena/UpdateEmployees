package ru.skypro.lessons.springboot.weblibrary.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class EmployeeFullInfo {

    private String name;
    private Integer salary;
    private String position_name;
    private Integer department;

}
