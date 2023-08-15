package ru.skypro.lessons.springboot.weblibrary.service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getHighSalary();
    void createEmployee(EmployeeDTO employee);
    List<EmployeeDTO> editEmployee(Integer id, String name, Integer salary);
    EmployeeDTO getEmployeeByID(Integer id);
    void deleteEmployeeByID(Integer id);
    List<EmployeeDTO> getEmployeesWhoseSalaryIsHigherThanTheParameter(Integer salary);
    List<Employee> getEmployeeByPositionName(String name);
    EmployeeDTO getEmployeeById(int id);
    List<Employee> getEmployeeWithPaging(int pageIndex, int unitPerPage);
    void upload(MultipartFile file) throws IOException;
    Employee addEmployee(EmployeeDTO employeeDTO);

}