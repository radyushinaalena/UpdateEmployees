package ru.skypro.lessons.springboot.weblibrary.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class ReportDTO {

    private Integer department;
    private Integer numberOfEmployees;
    private Integer maximumSalary;
    private Integer minimumWage;
    private Integer averageSalary;

}
