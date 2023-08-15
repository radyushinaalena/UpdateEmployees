package ru.skypro.lessons.springboot.weblibrary.repository;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeRepository  {

    List<EmployeeDTO> getHighSalary();
    void createEmployee(EmployeeDTO employee);
    List<EmployeeDTO> editEmployee(Integer id, String name, Integer salary);
    EmployeeDTO getEmployeeByID(Integer id);
    void deleteEmployeeByID(Integer id);
    List<EmployeeDTO> getEmployeesWhoseSalaryIsHigherThanTheParameter(Integer salary);

}
