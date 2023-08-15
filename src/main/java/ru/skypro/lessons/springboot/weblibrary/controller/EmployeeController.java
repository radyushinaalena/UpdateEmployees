package ru.skypro.lessons.springboot.weblibrary.controller;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.Getter;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;
import java.util.List;

@RestController
@RequestMapping("/employees")
@Getter
@AllArgsConstructor

public class EmployeeController {

    private final EmployeeService employeeService;
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeByID(@PathVariable Integer id) {
        return employeeService.getEmployeeByID(id);
    }

    @GetMapping("/salaryHigherThan")
    public List<EmployeeDTO> getEmployeesHighSalariesBySalary(@RequestParam("salary") Integer salary) {
        return employeeService.getEmployeesWhoseSalaryIsHigherThanTheParameter(salary);
    }

    @GetMapping("/")
    public List<Employee> getEmployeeByPositionName(@RequestParam String name) {
        return employeeService.getEmployeeByPositionName(name);
    }

    @GetMapping("/{id}/fullInfo")
    public EmployeeDTO getEmployeesById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/page")
    public List<Employee> getEmployeesFromPage(@RequestParam int pageIndex,@RequestParam int unitPerPage) {
        return employeeService.getEmployeeWithPaging(pageIndex, unitPerPage);
    }

}
