package ru.skypro.lessons.springboot.weblibrary.repository;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.dto.PositionDTO;

import java.util.*;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

     List<EmployeeDTO> employeeList = List.of(
            new EmployeeDTO(1, "Test1", 20_000, new PositionDTO(1, "PositionTest1")),
            new EmployeeDTO(2, "Test2", 30_000, new PositionDTO(2,"PositionTest2")),
            new EmployeeDTO(3, "Test3", 40_000, new PositionDTO(3,"PositionTest3")),
            new EmployeeDTO(4, "Test4", 50_000, new PositionDTO(4,"PositionTest4")),
            new EmployeeDTO(3, "Test5", 60_000, new PositionDTO(5,"PositionTest5")),
            new EmployeeDTO(4, "Test6", 70_000, new PositionDTO(6,"PositionTest6")));



    @Override
    public List<EmployeeDTO> getHighSalary() {
        List<Integer> salary = new ArrayList<>();
        for (EmployeeDTO employee : employeeList)
            salary.add(employee.getSalary());
        List<EmployeeDTO> employees = new ArrayList<>();
        for (EmployeeDTO employeeDTO : employeeList)
            if(employeeDTO.getSalary() > salary.stream()
                .mapToInt(a -> a)
                .average().orElse(0)){
                employees.add(employeeDTO);
            }
        return employees;
    }
    public void createEmployee(EmployeeDTO employee){
                employeeList.add(employee);
    }
    public List<EmployeeDTO> editEmployee(Integer id, String name, Integer salary) {
    for (EmployeeDTO employeeDTO : employeeList) {
        if (Objects.equals(id, employeeDTO.getId())) {
            employeeDTO.setName(name);
            employeeDTO.setSalary(salary);
        }
    }
    return employeeList;
    }
    public EmployeeDTO getEmployeeByID(Integer id) {
        EmployeeDTO employee = new EmployeeDTO();
        for (EmployeeDTO employeeDTO : employeeList) {
            if (Objects.equals(id, employeeDTO.getId()))
                employee = employeeDTO;
        }
        return employee;
    }
    public void deleteEmployeeByID(Integer id) {
        employeeList.removeIf(employee -> Objects.equals(employee.getId(), id));
    }
    public List<EmployeeDTO> getEmployeesWhoseSalaryIsHigherThanTheParameter(Integer salary) {
        List<EmployeeDTO> employees = new ArrayList<>();
        for (EmployeeDTO employeeDTO : employeeList) {
            if(employeeDTO.getSalary() > salary) {
                employees.add(employeeDTO);
            }
        }
        return employees;
    }

}
