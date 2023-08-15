package ru.skypro.lessons.springboot.weblibrary;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.exceptions.EmployeeNotFoundExceptions;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeQueryRepository;
import ru.skypro.lessons.springboot.weblibrary.repository.EmployeeRepositoryImpl;
import ru.skypro.lessons.springboot.weblibrary.service.EmployeeServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {


    @Mock
    private EmployeeRepositoryImpl employeeRepository;
    @Mock
    private EmployeeQueryRepository employeeQueryRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withUsername("postgres")
            .withPassword("admin");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    @DisplayName("getHighSalaryReturned")
    @Test
    public void getHighSalaryReturnedTest () {
        List<EmployeeDTO> expected = new ArrayList<>();
        lenient().when(employeeRepository.getHighSalary())
                .thenReturn(expected);
        assertEquals(expected, employeeService.getHighSalary());
    }

    @DisplayName("createEmployeeMethod")
    @Test
    public void createEmployeeMethodTest () {
        EmployeeDTO expected = new EmployeeDTO();
        verify(employeeRepository, never())
                .createEmployee(expected);
    }

    @DisplayName("editEmployeeReturned")
    @Test
    public void editEmployeeReturnedTest () {
        List<EmployeeDTO> expected = new ArrayList<>();
        int id = 0;
        String name = String.valueOf(String.class);
        int salary = 0;
        lenient().when(employeeRepository.editEmployee(id,name, salary))
                .thenReturn(expected);
        assertEquals(expected, employeeService.editEmployee(id,name, salary));
    }

    @DisplayName("getEmployeeByIDReturned")
    @Test
    public void getEmployeeByIDReturnedTest () {
        EmployeeDTO expected = new EmployeeDTO();
        int id = 0;
        lenient().when(employeeRepository.getEmployeeByID(id))
                .thenReturn(expected);
        assertEquals(expected, employeeService.getEmployeeByID(id));
    }

    @DisplayName("deleteEmployeeByIDMethod")
    @Test
    public void deleteEmployeeByIDMethodTest () {
        int id = 0;
        verify(employeeRepository, never())
                .deleteEmployeeByID(id);
    }

    @DisplayName("getEmployeesWhoseSalaryIsHigherReturned")
    @Test
    public void getEmployeesWhoseSalaryIsHigherReturnedTest () {
        List<EmployeeDTO> expected = new ArrayList<>();
        int salary = 0;
        lenient().when(employeeRepository.getEmployeesWhoseSalaryIsHigherThanTheParameter(salary))
                .thenReturn(expected);
        assertEquals(expected, employeeService.getEmployeesWhoseSalaryIsHigherThanTheParameter(salary));
    }

    @DisplayName("getEmployeeByPositionReturned")
    @Test
    public void getEmployeeByPositionReturnedTest () {
        String name = String.valueOf(String.class);
        Optional<Employee> employeeOptional = Optional.empty();
        when(employeeQueryRepository.findAllEmployeeFullInfo(name))
                .thenReturn(employeeOptional);
        assertEquals(anyList(), employeeService.getEmployeeByPositionName(name));

    }

    @DisplayName("getEmployeeByIdReturned")
    @Test
    public void getEmployeeByIdReturnedTest () {
        Optional<Employee> employeeOptional = Optional.empty();
        lenient().when(employeeQueryRepository.findById(anyInt()))
                .thenReturn(employeeOptional);
    }

    @DisplayName("getEmployeeFromPageReturned")
    @Test
    public void getEmployeeFromPageReturnedTest () {
        int pageIndex = 1;
        int unitPerPage = 1;
        Pageable employeeOfConcretePage = PageRequest.of(pageIndex, unitPerPage);
        Page<Employee> page = Page.empty();
        lenient().when(employeeQueryRepository.findAll(employeeOfConcretePage))
                .thenReturn(page);
        assertEquals(anyList(), employeeService.getEmployeeWithPaging(pageIndex, unitPerPage));
    }

    @DisplayName("getEmployeeWithHighestSalarybySalaryReturned")
    @Test
    public void getEmployeeWithHighestSalarybySalaryReturnedTest () {
        Employee employee = new Employee();
        lenient().when(employeeQueryRepository.findAll())
                .thenReturn(Collections.singleton(employee));
    }

    @SneakyThrows
    @DisplayName("uploadFileNegative")
    @Test
    public void uploadFileNegativeTest () {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "fileEmployees",
                "employees.json",
                MediaType.APPLICATION_JSON_VALUE,
                EmployeeTest.class.getResourceAsStream("/employees.json"));
        lenient().doThrow(EmployeeNotFoundExceptions.class)
                .when(employeeQueryRepository).saveAll(anyList());
        assertThrows(IOException.class, ()-> employeeService.upload(multipartFile));
    }
}