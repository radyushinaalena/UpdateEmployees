package ru.skypro.lessons.springboot.weblibrary.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.exceptions.EmployeeNotFoundExceptions;
import ru.skypro.lessons.springboot.weblibrary.helperMethod.HelperMethods;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeQueryRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service

public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    private final EmployeeQueryRepository employeeQueryRepository;

    public List<EmployeeDTO> getHighSalary() {
        LOGGER.info("Был вызван метод для получения сотрудников, зарплата которых выше средней");
        return employeeRepository.getHighSalary();
    }
    public void createEmployee(EmployeeDTO employee) {
        LOGGER.info("Был вызван метод для создания сотрудника {}" , employee);
        employeeRepository.createEmployee(employee);
    }
    public List<EmployeeDTO> editEmployee(Integer id, String name, Integer salary) {
        LOGGER.info("Был вызван метод для редактирования информации о сотруднике с id = {}", id);
       return employeeRepository.editEmployee(id, name, salary);
    }
    public EmployeeDTO getEmployeeByID(Integer id) {
        LOGGER.info("Был вызван метод для получения сотрудника по id = {}", id);
        return employeeRepository.getEmployeeByID(id);
    }
    public void deleteEmployeeByID(Integer id) {
        LOGGER.info("Был вызван метод для удаления сотрудника по id = {}", id);
        employeeRepository.deleteEmployeeByID(id);
    }
    public List<EmployeeDTO> getEmployeesWhoseSalaryIsHigherThanTheParameter(Integer salary) {
        LOGGER.info("Был вызван метод для получения сотрудников, зарплата которых выше {} р", salary);
        return employeeRepository.getEmployeesWhoseSalaryIsHigherThanTheParameter(salary);
    }
    @Override
    public List<Employee> getEmployeeByPositionName(String name) {
        LOGGER.info("Вызов метода получения списка сотрудников по должности {}", name);
        LOGGER.debug("Список сотрудников получен");
        return employeeQueryRepository.findAllEmployeeFullInfo(name).stream().collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        LOGGER.info("Вызов метода для получения сотрудника по id = {}", id);
        return employeeQueryRepository.findById(id).map(EmployeeDTO::toDTO).orElseThrow(EmployeeNotFoundExceptions::new);
    }

    @Override
    public List<Employee> getEmployeeWithPaging(int pageIndex, int unitPerPage) {
        LOGGER.info("Вызов метода для получения информации о сотрудниках со страницы = {}", pageIndex);
        Pageable employeeOfConcretePage = PageRequest.of(pageIndex, unitPerPage);
        return employeeQueryRepository.findAll(employeeOfConcretePage).stream().toList();
    }

    @Override
    public void upload(MultipartFile jsonFile) throws IOException {
        LOGGER.info("Вызов метода для получения списка сотрудников и сохранения их в базе данных");
        String fileName = jsonFile.getName();
        String jsonContent = HelperMethods.readTextFromFile(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        EmployeeDTO employeeDTO = objectMapper.readValue(jsonContent, EmployeeDTO.class);
        List<Employee> employeeList = List.of(employeeDTO.toEmployee());
        employeeQueryRepository.saveAll(employeeList);
        LOGGER.debug("Список сохранен");
    }
    @Override
    public Employee addEmployee(EmployeeDTO employeeDTO) {
        return employeeQueryRepository.save(employeeDTO.toEmployee());
    }

}
