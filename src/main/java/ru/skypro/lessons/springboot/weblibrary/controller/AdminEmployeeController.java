package ru.skypro.lessons.springboot.weblibrary.controller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/employees")
@Getter
@AllArgsConstructor

public class AdminEmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public void createEmployee(@RequestBody EmployeeDTO employee) {
        employeeService.createEmployee(employee);
    }
    @PutMapping("/{id}")
    public List<EmployeeDTO> editEmployeeById(@PathVariable Integer id, @PathVariable String name, @PathVariable Integer salary) {
        return employeeService.editEmployee(id, name, salary);
    }

    @DeleteMapping("/delete{id}")
    public void deleteEmployeeByID(@PathVariable Integer id) {
        employeeService.deleteEmployeeByID(id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestParam("fileEmployees") MultipartFile fileEmployees) throws IOException {
        employeeService.upload(fileEmployees);
    }

    @GetMapping("/{id}/get")
    public EmployeeDTO getEmployeeByID(@PathVariable Integer id) {
        return employeeService.getEmployeeByID(id);
    }

    @GetMapping("/salaryHigherThan")
    public List<EmployeeDTO> getEmployeeHighSalariesBySalary(@RequestParam("salary") Integer salary) {
        return employeeService.getEmployeesWhoseSalaryIsHigherThanTheParameter(salary);
    }

    @GetMapping("/pos")
    public List<Employee> getEmployeeByPositionName(@RequestParam String name) {
        return employeeService.getEmployeeByPositionName(name);
    }

    @GetMapping("/{id}/fullInfo")
    public EmployeeDTO getEmployeesById(@PathVariable int id) {
        return employeeService.getEmployeeByID(id);
    }

    @GetMapping("/page")
    public List<Employee> getEmployeeWithPaging(@RequestParam int pageIndex,@RequestParam int unitPerPage) {
        return employeeService.getEmployeeWithPaging(pageIndex, unitPerPage);
    }
    @PostMapping("/add")
    public Employee addEmployee (@RequestBody EmployeeDTO employeeDTO){
        return employeeService.addEmployee(employeeDTO);
    }

}
