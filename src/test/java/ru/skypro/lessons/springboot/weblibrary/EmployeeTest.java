package ru.skypro.lessons.springboot.weblibrary;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.lessons.springboot.weblibrary.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.weblibrary.pojo.Employee;
import ru.skypro.lessons.springboot.weblibrary.pojo.Position;
import java.util.List;

@WithMockUser(username = "postgres", password = "admin", roles = "ADMIN")
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
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

    @DisplayName("methodEmployeesById")
    @Test
    void getEmployeesByIdTest() throws Exception {

        EmployeeDTO employeeDTO = new EmployeeDTO();

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/employees/fullInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isMethodNotAllowed());
    }

    @DisplayName("CreateEmployeesMethod")
    @Test
    void CreateEmployeesMethodTest() throws Exception {

        MockMultipartFile multipartFile = new MockMultipartFile(
                "fileEmployees",
                "employees.json",
                MediaType.APPLICATION_JSON_VALUE,
                EmployeeTest.class.getResourceAsStream("/employees.json"));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/employees/upload")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("getEmployeeByIDQuery")
    @Test
    void getEmployeeByIDQueryTest() throws Exception{

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "0");
        jsonObject.put("name", "Alena");
        jsonObject.put("salary", 100000);
        jsonObject.put("department", 1);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/employees/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isMethodNotAllowed());
    }

    @DisplayName("getEmployeeByPositionName")
    @Test
    void getEmployeeByPositionNameTest() throws Exception{

        List<Employee> employeeList = List.of(
                new Employee(1, "Test1", 20_000, new Position(1, "PositionTest1"),1),
                new Employee(1, "Test1", 20_000, new Position(1, "PositionTest1"),1)
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/employees/pos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeList)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("getEmployeeFromPage")
    @Test
    void getEmployeeFromPageTest() throws Exception{

        List<Employee> employeeList = List.of(
                new Employee(1, "Test1", 20_000, new Position(1, "PositionTest1"),1),
                new Employee(1, "Test1", 20_000, new Position(1, "PositionTest1"),1)
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/employees/page")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeList)))
                .andExpect(status().isBadRequest());
    }
}
